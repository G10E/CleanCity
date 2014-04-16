<html>
<head>
	<title>
		CleanCity - Towards Cleaner City!!
	</title>
	<!--script type="text/javascript" src="http://openlayers.org/api/2.13.1/OpenLayers.js">
	</script-->
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
			var comment_array = new Array();
			var img_array = new Array();";
		while($row=pg_fetch_array($result)){
			//$data[]=array('type'=>$row['rep_type'],'comment'=>$row['rep_comment'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude']);
			$tp=trim($row['rep_type']);
			$cm=trim($row['rep_comment']);
			$lat=trim($row['latitude']);
			$lon=trim($row['longitude']);
			$img=trim($row['imgpath']);
			echo "type_array.push(\"$tp\");";
			echo "comment_array.push(\"$cm\");";
			echo "lat_array.push(\"$lat\");";
			echo "long_array.push(\"$lon\");";
			echo "img_array.push(\"$img\");";
		}
		echo "</script>";
	?>	
	<script type ="text/javascript">
		
		function init() {
			map = new OpenLayers.Map("map",{projection:"EPSG:3857"});
			//mapquest tiles
			arrayOSM = ["http://otile1.mqcdn.com/tiles/1.0.0/map/${z}/${x}/${y}.jpg",
                    "http://otile2.mqcdn.com/tiles/1.0.0/map/${z}/${x}/${y}.jpg",
                    "http://otile3.mqcdn.com/tiles/1.0.0/map/${z}/${x}/${y}.jpg",
                    "http://otile4.mqcdn.com/tiles/1.0.0/map/${z}/${x}/${y}.jpg"];
			var osm = new OpenLayers.Layer.OSM("MapQuest-OSM Tiles", arrayOSM);
			var toMercator = OpenLayers.Projection.transforms['EPSG:4326']['EPSG:3857'];
			var center = toMercator({x:85.3219,y:27.7019});
			//adding features
			var features = [];
			for(var i=0;i<type_array.length;i++){
				var lt = parseFloat(lat_array[i]);
				var ln = parseFloat(long_array[i]);
				features.push(new OpenLayers.Feature.Vector(
					toMercator(new OpenLayers.Geometry.Point(ln,lt)), 
					{
						type : type_array[i],
						comment : comment_array[i],
						imgpath : img_array[i]
					}));
			}
			var style = new OpenLayers.Style({
				label:"${name}",
				fontColor:"white",
				fontSize:"12",
				pointRadius: "${radius}",
				fillColor: "${color}",
				fillOpacity: 0.8,
				strokeColor: "#000000",
				strokeWidth: "${width}",
				strokeOpacity: 0.8
			}, {
				context: {
					name: function(feature){
						return(feature.cluster) ? (feature.attributes.count) : "";
					},
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
						if (feature.cluster){
							var mixed_garbage=false;
							var one_report = feature.cluster[0].attributes.type.trim();
							if (one_report!="Mixed"){
								for(var i=0;i<feature.attributes.count;i++){
									if (one_report!=feature.cluster[i].attributes.type.trim()){
										mixed_garbage=true;
										break;
									}
								}
							}
							else{
								mixed_garbage=true;
							}
							if(mixed_garbage){
								return '#0000dd';
							}
							else{
								if(feature.cluster[0].attributes.type == "Bio Degradable"){
									return '#00dd00';
								}
								else {
									return '#dd0000';
								}
							}
						}
						else{
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
					},
					decodeimg : function(base64string){
						
					}
				}
			});
			// create the layer with listeners to create and destroy popups
			var vector = new OpenLayers.Layer.Vector("Points",{
				strategies: [new OpenLayers.Strategy.Cluster({distance: 20, threshold: 2})],
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
						//var popup_text = "<div style='font-size:.8em font-style:bold'><b>Type: ";
						var popup_text = "<html><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><body><b>Type: ";
						if(feature.cluster){
							var num = feature.attributes.count;
							var mixed_garbage=false;
							var one_report = feature.cluster[0].attributes.type.trim();
							if (one_report!="Mixed"){
								for(var i=0;i<feature.attributes.count;i++){
									if (one_report!=feature.cluster[i].attributes.type.trim()){
										mixed_garbage=true;
										break;
									}
								}
							}
							else mixed_garbage=true;
							var last =feature.attributes.count-1;
							if(mixed_garbage){
									popup_text+="Mixed </b><br>Comments: " + feature.cluster[last].attributes.comment +"<br>";
									popup_text+= "<img src='"+feature.cluster[last].attributes.imgpath+"'/><br>and "+last+" more...";
							}
							else{
									popup_text+=feature.cluster[0].attributes.type+"</b><br>Comments: " + feature.cluster[last].attributes.comment +"<br>";
									popup_text+= "<img src='"+feature.cluster[last].attributes.imgpath+"'/> <br>and "+last+" more...";
							}
						}
						else{
							popup_text+=feature.attributes.type+"</b><br>Comments: " + feature.attributes.comment+"<br>";
							popup_text+= "<img src='"+feature.attributes.imgpath+"'/>";
						}
						//popup_text+="</div>";
						popup_text+="</body></html>";
						var popup = new OpenLayers.Popup.FramedCloud("popup",
							OpenLayers.LonLat.fromString(feature.geometry.toShortString()),
							null,
							popup_text,
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
			
			// create the select feature control
			var selector = new OpenLayers.Control.SelectFeature(vector,{
				hover:true,
				autoActivate:true
			}); 
			
			map.addLayers([osm, vector]);
			
			vector.addFeatures(features);
			//vector.addFeatures(nonbio_features);
			//vector.addFeatures(mixed_features);
			
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
<!--
$imageDataEncoded = base64_encode(file_get_contents('sample.png'));
$imageData = base64_decode($imageDataEncoded);
$source = imagecreatefromstring($imageData);
$angle = 90;
$rotate = imagerotate($source, $angle, 0); // if want to rotate the image
$imageName = "hello1.png";
$imageSave = imagejpeg($rotate,$imageName,100);
imagedestroy($source);
-->