<?php
    // check_updates.php
    header('Content-Type: application/json');
    include 'db.php';

    // Obtenemos la fecha de modificación más reciente de cualquier item activo
    // Usamos UNIX_TIMESTAMP para evitar problemas de formato de fecha entre PHP y Kotlin
    $sql = "SELECT UNIX_TIMESTAMP(MAX(updated_at)) as ultima_mod FROM items WHERE FlagActivo = 1";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();

    // La firma será el timestamp (ej: 1715432001)
    $signature = $row['ultima_mod'] ?? "0";

    echo json_encode([
        "success" => true,
        "signature" => $signature
    ]);

    $conn->close();
?>
