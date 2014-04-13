<html>
	<title>
		mockup
	</title>
	<body>
		<form method='post' action='./report.php'>
			<table>
			<tr>
				<th>type</th><td><INPUT TYPE='text' NAME='type'/></td>
			</tr>
			<tr>
				<th>comment</th><td><INPUT TYPE='text' NAME='comment'/></td>
			</tr>	
			<tr>
				<th>lat</th><td><INPUT TYPE='text' NAME='lat'/></td>
			</tr>
			<tr>			
				<th>long</th><td><INPUT TYPE='text' NAME='long'/></td>
			</tr>
			<tr>
				<td><INPUT TYPE='SUBMIT' TEXT='SUBMIT'/></td>
			</tr>
			</table>
		</form>
		<form method='post' action='./retrieve.php'>
			type <INPUT type='text' name='type'/>
			<input type='submit' text='request'/>
		</form>
		<?php 
			include_once './retrieve.php';
		?>
	</body>

</html>