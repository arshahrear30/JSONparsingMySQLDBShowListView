<?php

 
header('Content-Type: application/json; charset=utf-8');
  
$con = mysqli_connect("localhost","zftsszne_shahrear","arshahrear30@gmail.com","zftsszne_my_database");
$sql = "SELECT * FROM user_table ORDER BY id DESC "; // এটা এমন একটা command  যেটা ইউজার টেবিল এর মধ্যে থাকা সকল ডাটা কে কালেক্ট করবে
//ORDER BY id DESC লেখার কারণে সর্বশেষ Input টা প্রথমে show করবে
$result = mysqli_query($con,$sql); //Query Done
$rowcount = mysqli_num_rows($result); //result মধ্যে কয়টা row আছে সেটা বের করার code

$data = array();
foreach($result as $item){
    $id = $item['id'];
    $name = $item['name'];
    $mobile = $item['mobile'];
    $email = $item['email'];

    $userInfo['id'] = $id;
    $userInfo['name'] = $name;
    $userInfo['mobile'] = $mobile;
    $userInfo['email'] = $email;
    array_push($data, $userInfo);
}
echo json_encode($data);

?>
