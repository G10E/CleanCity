<?php
	//enum values for type = ('Bio Degradable','Non-Bio Degradable', 'Other')
	
	if(isset($_POST['type'])){
		include_once './connection.php';
		$post_type = $_POST['type'];
		$post_comment = $_POST['comment'];
		$post_lat = $_POST['lat'];
		$post_long = $_POST['long'];
		
		//pg_prepare($connection,"SQL_string", "insert into");
		//pg_execute($connection,"SQL_string", ($ASDASD,$asdasd));
		$SQL_string = "INSERT INTO reports(
            rep_id, rep_type, rep_comment, latitude, longitude)
			VALUES (nextval('reports_rep_id_seq'), '$post_type', '$post_comment', '$post_lat', '$post_long');";
		
		pg_query($connection,$SQL_string);
		
	}
?>