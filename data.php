<?php

//প্রথমে server এর Hosting  এর C pannel এ Database এ যাবো ।সেখানে Manage Database এ যাবো 
//তারপর  Add User To Database এখানে New User/New Database/DBUSERID & DBPassword set করবো । তারপর Database 
//create করবো ।  এরপর phpmyadmin এ যাবো ।

$name = $_GET['n'];
$mobile =$_GET['m'];
$email =$_GET['e'];

/*
একটা Database এ Data কিভাবে link এর মাধ্যমে Hit করে ? সেটা দেখবো । 
প্রথমে Data নামে(যেকোনো নাম দিতে পারো) c panel এ একটা PHP file create করলাম । 
তারপর সেখানে Database এর সাথে php connect করার code input দিলাম । 
তারপর ধরো link টা এমন হলো  https://nubsoft.xyz/data.php ।
এবার খেয়াল করো :  https://nubsoft.xyz/data.php?n=shahrear&m=01872000&e=arshahrear30  
লক্ষ্য করো n এবং m এবং e এগুলো key । আর shahrear , 01872000 , arshahrear30 এগুলো হলো Values । 
তাহলে .php এর পর key=values এভাবে data input দিতে পারি । 
এখন এই link এ value গুলো update করলে আরেকটা database এ row create হয়ে data গুলো input হবে ।

*/


//mysqli_connect(); এর কাজ database(phpmyadmin) এর সাথে php file এর connection ঘটানো
// mysqli_connect(); এর ভিতর 4 টা part থাকবে । 1st part DB(database) localhost 
//mysqli_connect("localhost","DBUSERID","DBPassword","DBNameinphpmyadmin");
//dollar sign দিয়ে variable ধরতে হয় । এখানে con একটা variable 

$con = mysqli_connect("localhost","zftsszne_shahrear","arshahrear30@gmail.com","zftsszne_my_database");

if(mysqli_connect_errno()){
	//mysqli_connect_errno এটার মানে , যদি mysql এর সাথে connect না হয় তাহলে show করবে Couldn't connect to database এবং<br> মানে newline
	
	echo "Couldn't connect to database!<br>".mysqli_connect_error();
	//.mysqli_connect_error() এটার মানে , কোডে কোথায় error আছে সেটা দেখাও ।
	
}else{
	echo"Connected with database";
}

//variable ধরলাম sql তারপর Database(phpmyadmin) এ data insert  করে Go তে দিলে Query গুলো Show করবে । 

$sql = "INSERT INTO user_table (name,mobile,email)VALUES ('$name','$mobile','$email')";
$result = mysqli_query($con,$sql);

//mysqli_query মানে server এর সাথে connection ঘটানো । আর 1st bracket  মাঝে বুঝাইলাম কার কার সাথে connection করলাম

if($result) echo"\n Data insert Successful" ;// যদি সঠিক ভাব result variable এর কাজ হয় তখন Successful echo হবে ।
else echo " Query error!!";


?>
