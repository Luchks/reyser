<?php
    header('Content-Type: application/json');
    include 'db.php';

    // Para usar convertirAGoogleCalendar (igual que en create_item.php)
    include 'utilitarios/fechaCalendar.php';

    // Para usar crear/actualizar eventos
    require 'google_calendar_service.php';

    $data = json_decode(file_get_contents('php://input'), true);

    if (!$data || !isset($data['id'])) {
        echo json_encode(["success" => false, "message" => "ID de reserva faltante"]);
        exit;
    }

    $id = (int)$data['id'];

    // ================================
    // 1) LEER id_calendar ACTUAL
    // ================================
    $id_calendar_actual = null;

    $stmtCal = $conn->prepare("SELECT id_calendar FROM items WHERE id = ?");
    $stmtCal->bind_param("i", $id);
    $stmtCal->execute();
    $stmtCal->bind_result($id_calendar_db);
    if ($stmtCal->fetch()) {
        $id_calendar_actual = $id_calendar_db;   
    }
    $stmtCal->close();

    // ================================
    // 2) LEER/ASIGNAR DATOS DEL JSON
    // ================================

    $name = $data['name'] ?? '';
    $description = $data['description'] ?? '';
    $codigoReserva = $data['codigoReserva'] ?? '';
    $nombreTour = $data['nombreTour'] ?? '';
    $tipoCliente = $data['tipoCliente'] ?? '';
    $fecha = $data['fecha'] ?? '';
    $horaInicio = $data['horaInicio'] ?? '';
    $turno = $data['turno'] ?? '';
    $hotelDireccion = $data['hotelDireccion'] ?? '';
    $duracion = $data['duracion'] ?? '';
    $nombrePrincipal = $data['nombrePrincipal'] ?? '';
    // Procesar pasajeros adicionales como JSON string
    $pasajeros = isset($data['pasajerosAdicionales']) ? json_encode($data['pasajerosAdicionales']) : '[]';
    $pasaporteID = $data['pasaporteID'] ?? '';
    $countryCodewhatsapp = $data['countryCodewhatsapp'] ?? '';
    $whatsapp = $data['whatsapp'] ?? '';
    $correo = $data['correo'] ?? '';
    $habitacion = $data['habitacion'] ?? '';
    $idioma = $data['idioma'] ?? '';
    $pais = $data['pais'] ?? '';
    $tipoPago = $data['tipoPago'] ?? '';
    $precioPorPersona = (float)($data['precioPorPersona'] ?? 0);
    $precioTotal = (float)($data['precioTotal'] ?? 0);
    $precioComisionable = (float)($data['precioComisionable'] ?? 0);
    $totalComision = (float)($data['totalComision'] ?? 0);
    $agente = $data['agente'] ?? '';
    $countryCodewaAgente = $data['countryCodewaAgente'] ?? '';
    $waAgente = $data['waAgente'] ?? '';
    $observacion = $data['observacion'] ?? '';
    $driver = $data['driver'] ?? '';
    $countryCodewaDriver = $data['countryCodewaDriver'] ?? '';
    $waDriver = $data['waDriver'] ?? '';
    $guia = $data['guia'] ?? '';
    $countryCodewaGuia = $data['countryCodewaGuia'] ?? '';
    $waGuia = $data['waGuia'] ?? '';
    $cantidadPasajero = $data['cantidadPasajero'] ?? '';
    $tipoServicio = $data['tipoServicio'] ?? '';
    $observacionGeneral = $data['observacionGeneral'] ?? '';
    // Normalizar estadoPago: nunca guardar '0' o vacío en MySQL
    $rawEstadoPago = strtoupper(trim($data['estadoPago'] ?? ''));
    $validEstados  = ['PAGADO', 'PENDIENTE PAGO', 'CREDITO AGENCIA', 'SERVICIO GRATUITO'];
    $estadoPago    = in_array($rawEstadoPago, $validEstados, true) ? $rawEstadoPago : 'PENDIENTE PAGO';
    // CAMBIO CLAVE: Capturar el estado enviado por la App (BORRADOR o COMPLETADO)
    $estadoReserva = $data['estadoReserva'] ?? 'BORRADOR'; 
    $comprobantePago = $data['comprobantePago'] ?? '';

    // ================================
    // 3) ACTUALIZAR CALENDAR
    // ================================
    $fechaStart = convertirAGoogleCalendar($fecha, $horaInicio);
    $fechaEnd   = convertirAGoogleCalendar($fecha, $horaInicio, 4);

    $summary = "$nombreTour X$cantidadPasajero  $tipoServicio($agente)";
    $location = $hotelDireccion;

    $description_cal = "<b>Código:</b> " . ($codigoReserva ?? '') . "<br>" .
            "<b>=======================</b><br>" .
            "<b>DATOS DE RESERVA</b><br>" .
            "<b>Servicio:</b> " . ($nombreTour ?? '') . "<br>" .
            "<b>Tipo de servicio:</b> " . ($tipoServicio ?? '') . "<br>" .
            "<b>Tipo de cliente:</b> " . ($tipoCliente ?? '') . "<br>" .
            "<b>Contacto o Agente:</b> " . ($agente ?? '') . "<br>" .
            "<b>Teléfono contacto:</b> " . ($waAgente ?? '') . "<br>" .
            "<b>Fecha:</b> " . ($fecha ?? '') . "<br>" .

            "<b>=======================</b><br>" .
            "<b>DATOS DEL PASAJERO</b><br>" .
            "<b>Cantidad pasajeros:</b> " . ($cantidadPasajero ?? '') . "<br>" .
            "<b>Pasajero principal:</b> " . ($nombrePrincipal ?? '') . "<br>" .
            "<b>WhatsApp:</b> <a href='https://wa.me/" . ($whatsapp ?? '') . "'>" . ($whatsapp ?? '') . "</a><br>" .
            "<b>Correo:</b> " . ($correo ?? '') . "<br>" .
            "<b>País:</b> " . ($pais ?? '') . "<br>" .
            "<b>Idioma:</b> " . ($idioma ?? '') . "<br>" .
            "<b>Pasajeros adicionales:</b> " . ($pasajerosAdicionales ?? '') . "<br>" .

            "<b>=======================</b><br>" .
            "<b>PAGOS</b><br>" .
            "<b>Tipo de pago:</b> " . ($tipoPago ?? '') . "<br>" .
            "<b>Precio por persona:</b> " . ($precioPorPersona ?? '') . "<br>" .
            "<b>Precio total:</b> " . ($precioTotal ?? '') . "<br>" .
            "<b>Estado de pago:</b> " . ($estadoPago ?? '') . "<br>" .
            "<b>Comprobante:</b> " . ($comprobantePago ?? '') . "<br>" .

            "<b>=======================</b><br>" .
            "<b>OBSERVACIONES</b><br>" .
            "<b>Interna:</b> " . ($observacion ?? '') . "<br>" .
            "<b>General:</b> " . ($observacionGeneral ?? '') . "<br>" .

            "<b>=======================</b><br>" .
            "<b>PERSONAL ASIGNADO</b><br>" .
            "<b>Driver:</b> " . ($driver ?? '') . "<br>" .
            "<b>Teléfono driver:</b> " . ($waDriver ?? '') . "<br>" .
            "<b>Guía:</b> " . ($guia ?? '') . "<br>" .
            "<b>Teléfono guía:</b> " . ($waGuia ?? '') . "<br>";


            $colorId="5";
            if($tipoServicio=="Privado") $colorId="5";
            if($tipoServicio=="Grupal") $colorId="10";
            if($tipoServicio=="Anulado") $colorId="11";



    $id_calendar_nuevo = $id_calendar_actual;


    if($estadoReserva != 'BORRADOR'){
        if (!empty($id_calendar_actual)) {
            actualizarEventoCalendar($id_calendar_actual, $summary, $description_cal, $location, $fechaStart, $fechaEnd,$colorId);
        } else {
            $nuevo_id = crearEventoCalendar($summary, $description_cal, $location, $fechaStart, $fechaEnd,$colorId);
            if ($nuevo_id) $id_calendar_nuevo = $nuevo_id;
        }
    }

    // ================================
    // 4) UPDATE EN BASE DE DATOS
    // ================================

    // Se agrega estadoReserva=? antes de comprobantePago
    $stmt = $conn->prepare("
        UPDATE items SET 
            name=?, description=?, codigoReserva=?, nombreTour=?, tipoCliente=?, fecha=?, horaInicio=?, turno=?,
            hotelDireccion=?, duracion=?, nombrePrincipal=?, pasajerosAdicionales=?, pasaporteID=?, countryCodewhatsapp=?, whatsapp=?, correo=?,
            habitacion=?, idioma=?, pais=?, tipoPago=?, precioPorPersona=?, precioTotal=?, precioComisionable=?, totalComision=?,
            agente=?, countryCodewaAgente=?, waAgente=?, observacion=?, driver=?, countryCodewaDriver=?, waDriver=?, guia=?, countryCodewaGuia=?,
            waGuia=?, id_calendar=?, cantidadPasajero=?, tipoServicio=?, observacionGeneral=?, estadoPago=?, 
            estadoReserva=?, comprobantePago=? 
        WHERE id=?
    ");

    // Se actualiza el bind_param: 41 campos tipo string/double y 1 ID tipo integer
    $stmt->bind_param(
        "ssssssssssssssssssssddddsssssssssssssssssi",
        $name, $description, $codigoReserva, $nombreTour, $tipoCliente, $fecha, $horaInicio, $turno,
        $hotelDireccion, $duracion, $nombrePrincipal, $pasajeros, $pasaporteID, $countryCodewhatsapp, $whatsapp, $correo,
        $habitacion, $idioma, $pais, $tipoPago, $precioPorPersona, $precioTotal, $precioComisionable,
        $totalComision, $agente, $countryCodewaAgente, $waAgente, $observacion, $driver, $countryCodewaDriver, $waDriver, $guia, $countryCodewaGuia,
        $waGuia, $id_calendar_nuevo, $cantidadPasajero, $tipoServicio, $observacionGeneral, $estadoPago, 
        $estadoReserva, $comprobantePago, $id
    );

    $ok = $stmt->execute();

    if ($ok) {
        echo json_encode([
            "success" => true,
            "id_calendar" => $id_calendar_nuevo,
            "message" => "Reserva actualizada con éxito"
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "message" => "Error al actualizar reserva",
            "error" => $stmt->error
        ]);
    }

    $stmt->close();
    $conn->close();
?>
