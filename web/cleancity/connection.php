<?php
	$db_host = 'localhost';
	$db_port = 5432;
	$db_name = 'cleancity';
	$db_user = 'banmedo';
	$db_pass = '12345';
	
	$connection = pg_connect("host=$db_host port=$db_port dbname=$db_name user=$db_user password=$db_pass");
	
	if (!$connection){
		die ('connection terminated!!');
	}
	
?>