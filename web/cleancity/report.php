<?php
	//enum values for type = ('Bio Degradable','Non-Bio Degradable', 'Other')
	
	if(isset($_POST['type'])){
		include_once './connection.php';
		$post_type = $_POST['type'];
		$post_comment = $_POST['comment'];
		$post_lat = $_POST['lat'];
		$post_long = $_POST['long'];
		$post_imgstr = $_POST['base64image'];
		
		//echo $post_imgstr;
		//pg_prepare($connection,"SQL_string", "insert into");
		//pg_execute($connection,"SQL_string", ($ASDASD,$asdasd));
		
		$i=1;
		$imageName="";
		while(true){
			if(!file_exists ("./uploadedimages/$i.png")){
				$imageName = "./uploadedimages/$i.png";
				$handle = fopen($imageName, 'w');
				fclose($handle);
				break;
			}
			$i++;
		}
		//$imageDataEncoded = base64_encode($post_imgstr);
		$imageData = base64_decode($post_imgstr);
		$source = imagecreatefromstring($imageData);
		$angle = 0;
		$rotate = imagerotate($source, $angle, 0); // if want to rotate the image
		$imageSave = imagejpeg($rotate,$imageName,100);
		//imagedestroy($source);
		
		
		$SQL_string = "INSERT INTO reports(
            rep_id, rep_type, rep_comment, latitude, longitude, imgPath)
			VALUES (nextval('reports_rep_id_seq'), '$post_type', '$post_comment', '$post_lat', '$post_long','$imageName');";
		echo $SQL_string;
		pg_query($connection,$SQL_string);
	}
?>