<?php
    $host = "localhost";
    $db = "miapp";
    $user = "root";
    $pass = "123";

    $conn = new mysqli($host, $user, $pass, $db);
    if($conn->connect_error){
        die(json_encode(["error" => "Conexión fallida: " . $conn->connect_error]));
    }
    $conn->set_charset("utf8mb4");
?>
