<html>
	<head>
		<title>
			CleanCity - Towards Cleaner City!!
		</title>
		<script type="text/javascript" src="./2.13.1/OpenLayers.js">
		</script>
		<style>
			html,head, body{
				width: 100%;
				height: 100%;
				margin: 0;
				padding: 0;
				background-color:#666666;
			}
		</style>
		<style>
			#map{
				position:relative;
				float:right;
			}
		</style>
		<?php
			include_once './connection.php';
			$SQL_string="select * from reports";// where rep_type='$post_type'";		
			$result = pg_query($connection,$SQL_string);
			echo "<script>";
			echo "var lat_array = new Array();
				var long_array = new Array();
				var type_array = new Array();
				var comment_array = new Array();";
			while($row=pg_fetch_array($result)){
				//$data[]=array('type'=>$row['rep_type'],'comment'=>$row['rep_comment'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude']);
				$tp=trim($row['rep_type']);
				$cm=trim($row['rep_comment']);
				$lat=trim($row['latitude']);
				$lon=trim($row['longitude']);
				echo "type_array.push(\"$tp\");";
				echo "comment_array.push(\"$cm\");";
				echo "lat_array.push(\"$lat\");";
				echo "long_array.push(\"$lon\");";
			}
			echo "</script>";
		?>	
		<script type ="text/javascript">
			
			function init() {
				map = new OpenLayers.Map("map",{projection:"EPSG:3857"});
				var osm = new OpenLayers.Layer.OSM();
				var toMercator = OpenLayers.Projection.transforms['EPSG:4326']['EPSG:3857'];
				var center = toMercator({x:85.3219,y:27.7019});
				//adding features
				var bio_features = [];    
				var nonbio_features = [];    
				var mixed_features = [];    
				for(var i=0;i<type_array.length;i++){
					var lt = parseFloat(lat_array[i]);
					var ln = parseFloat(long_array[i]);
					if(type_array[i]=="Bio Degradable"){
						bio_features.push(new OpenLayers.Feature.Vector(
							toMercator(new OpenLayers.Geometry.Point(ln,lt)), 
							{
								type : type_array[i],
								comment : comment_array[i]
							}));
					}
					else if (type_array[i]=="Non-Bio Degradable"){
						nonbio_features.push( new OpenLayers.Feature.Vector(
							toMercator(new OpenLayers.Geometry.Point(ln,lt)), 
							{	
								type : type_array[i],	
								comment : comment_array[i]
							}));
					}
					else{
						mixed_features.push( new OpenLayers.Feature.Vector(
							toMercator(new OpenLayers.Geometry.Point(ln,lt)), 
							{	
								type : type_array[i],	
								comment : comment_array[i]
							}));
					}
				}
				 var style = new OpenLayers.Style({
                    pointRadius: "${radius}",
                    fillColor: "${color}",
                    fillOpacity: 0.8,
                    strokeColor: "#cc6633",
                    strokeWidth: "${width}",
                    strokeOpacity: 0.8
                }, {
                    context: {
                        width: function(feature) {
                            return (feature.cluster) ? 2 : 1;
                        },
                        radius: function(feature) {
                            var pix = 8;
                            if(feature.cluster) {
                                pix = Math.min(feature.attributes.count, 7) + 8;
                            }
                            return pix;
                        },
						color: function(feature){
							if(feature.attributes.type == "Bio Degradable"){
								return '#00dd00';
							}
							else if(feature.attributes.type == "Non-Bio Degradable"){
								return '#dd0000';
							}
							else
							{
								return '#0000dd';
							}
						}
                    }
                });
				// create the layer with listeners to create and destroy popups
				var vector = new OpenLayers.Layer.Vector("Points",{
					strategies: [new OpenLayers.Strategy.Cluster({distance: 15, threshold: 1})],
					styleMap: new OpenLayers.StyleMap({
                        "default": style,
                        "select": {
                            fillColor: "#888888",
                            strokeColor: "#32a8a9"
                        }
                    }),
					eventListeners:{
						'featureselected':function(evt){
							var feature = evt.feature;
							var popup = new OpenLayers.Popup.FramedCloud("popup",
								OpenLayers.LonLat.fromString(feature.geometry.toShortString()),
								null,
								"<div style='font-size:.8em font-style:bold'>Type: " + feature.attributes.type+"<br>Comments: " + feature.attributes.comment +"</div>",
								null,
								true
							);
							feature.popup = popup;
							map.addPopup(popup);
						},
						'featureunselected':function(evt){
							var feature = evt.feature;
							map.removePopup(feature.popup);
							feature.popup.destroy();
							feature.popup = null;
						}
					}
				});
				vector.addFeatures(bio_features);
				vector.addFeatures(nonbio_features);
				vector.addFeatures(mixed_features);

				// create the select feature control
				var selector = new OpenLayers.Control.SelectFeature(vector,{
					hover:true,
					autoActivate:true
				}); 
				
				map.addLayers([osm, vector]);
				map.addControl(selector);
				map.setCenter(new OpenLayers.LonLat(center.x,center.y), 13);
			}
		</script>
	</head>
	<body onload="init()">
		
		<div id="map" style="width: 100%; height=100%;">
		</div>
		
	</body>
</html>