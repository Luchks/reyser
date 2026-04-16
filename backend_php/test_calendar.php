<?php
    // test_calendar.php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);

    require 'google_calendar_service.php';

    echo "<h2>Probando conexión con Google Calendar...</h2>";

    // 1. Datos de prueba
    $summary = "Reserva de Prueba Directa";
    $description = "Si ves esto, la cuenta de servicio y los permisos están perfectos.";
    $location = "Lima, Perú";

    // Formato RFC3339 requerido por Google (ajusta la fecha a hoy o mañana)
    $startDate = "2026-04-10T10:00:00-05:00"; 
    $endDate   = "2026-04-10T12:00:00-05:00";

    echo "Intentando crear evento para: $startDate <br>";

    // 2. Llamada a la función de tu servicio
    $id_creado = crearEventoCalendar($summary, $description, $location, $startDate, $endDate);

    if ($id_creado) {
        echo "<p style='color: green; font-weight: bold;'>";
        echo "✅ ¡ÉXITO! El evento se creó correctamente.<br>";
        echo "ID del Evento: " . $id_creado;
        echo "</p>";
        echo "<p>Revisa tu Google Calendar en el navegador ahora mismo.</p>";
    } else {
        echo "<p style='color: red; font-weight: bold;'>";
        echo "❌ FALLÓ la creación del evento.";
        echo "</p>";
        echo "Revisa el archivo <b>php_error.log</b> para ver el motivo exacto del error.";
    }
?>
