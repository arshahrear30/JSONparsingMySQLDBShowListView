<?php
  
	$id = $_GET['id'];
  
$con = mysqli_connect("localhost","zftsszne_shahrear","arshahrear30@gmail.com","zftsszne_my_database");

$sql = "DELETE FROM user_table WHERE id='$id' "; 
$result = mysqli_query($con,$sql); 

if($result) echo " $name Successfully deleted from database";
else echo 'Something wrong';

?>
