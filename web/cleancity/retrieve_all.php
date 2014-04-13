<?php
	//if(isset($_POST['type'])){
		include_once './connection.php';
		//$post_type=$_GET['type'];
		/*if(isset($_POST['lat'])){
			$post_lat=$_GET['lat'];
		}
		else{
			$post_lat="*";
		}
		if(isset($_POST['long']){
			$post_long=$_GET['long'];
		}
		else{
			$post_long="*";
		}*/
		//pg_prepare($connection, "SQL_string","select * from reports where rep_type='$1' and latitude=* and longitude=*");
		//$result = pg_execute($connection, "SQL_string",($post_type));
		$SQL_string="select * from reports";// where rep_type='$post_type'";
		
		$result = pg_query($connection,$SQL_string);
		$data=array();
		while($row=pg_fetch_array($result)){
			$data[]=array('type'=>$row['rep_type'],'comment'=>$row['rep_comment'],'latitude'=>$row['latitude'],'longitude'=>$row['longitude']);
		}
		echo json_encode($data);
	//}
?>