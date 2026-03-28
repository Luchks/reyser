<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Content-Type');

include 'db.php';

$data = json_decode(file_get_contents('php://input'), true);

// Log para debug (puedes comentar después)
error_log("Datos recibidos: " . print_r($data, true));

if (!$data || !isset($data['id']) || !isset($data['estadoPago'])) {
    echo json_encode([
        "success" => false, 
        "message" => "Datos incompletos"
    ]);
    exit;
}

$id = (int)$data['id'];
$estadoPago = $data['estadoPago'];

// Validar que el estado sea válido
$estadosValidos = ['PAGADO', 'PENDIENTE PAGO', 'CREDITO AGENCIA', 'SERVICIO GRATUITO'];
if (!in_array($estadoPago, $estadosValidos)) {
    echo json_encode([
        "success" => false,
        "message" => "Estado de pago inválido: $estadoPago"
    ]);
    exit;
}

$stmt = $conn->prepare("UPDATE items SET estadoPago = ? WHERE id = ?");
$stmt->bind_param("si", $estadoPago, $id);

if ($stmt->execute()) {
    echo json_encode([
        "success" => true,
        "message" => "Estado actualizado correctamente"
    ]);
} else {
    echo json_encode([
        "success" => false,
        "message" => "Error al actualizar",
        "error" => $stmt->error
    ]);
}

$stmt->close();
$conn->close();
?>
