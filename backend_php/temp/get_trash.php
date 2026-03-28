<?php
    header('Content-Type: application/json');
    include 'db.php';

    $result = $conn->query("SELECT * FROM items WHERE FlagActivo = 0 ORDER BY id DESC");
    $items = [];

    while($row = $result->fetch_assoc()){
        $row['pasajerosAdicionales'] = json_decode($row['pasajerosAdicionales'], true) ?? [];
        $row['id']                 = intval($row['id']);
        $row['precioPorPersona']   = intval($row['precioPorPersona']);
        $row['precioTotal']        = intval($row['precioTotal']);
        $row['precioComisionable'] = intval($row['precioComisionable']);
        $row['totalComision']      = intval($row['totalComision']);
        $row['FlagActivo']         = intval($row['FlagActivo']);

        $items[] = $row;
    }

    echo json_encode($items);
    $conn->close();
?>
