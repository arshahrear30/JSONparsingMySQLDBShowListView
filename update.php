<?php
  
    
    $name = $_GET['name'];
    $mobile = $_GET['mobile'];
    $email = $_GET['email'];
	$id = $_GET['id'];
  
$con = mysqli_connect("localhost","zftsszne_shahrear","arshahrear30@gmail.com","zftsszne_my_database");

$sql = "UPDATE user_table SET name='$name',mobile='$mobile',email='$email' WHERE id='$id' "; 
$result = mysqli_query($con,$sql); 

if($result) echo " $name Successfully inserted to database";
else echo 'Something wrong';

?>
