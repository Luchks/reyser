<?php
    // create_item.php
    ini_set('log_errors', 1);
    ini_set('error_log', __DIR__ . '/php_error.log');
    error_reporting(E_ALL);
    ini_set('display_errors', 0);

    header('Content-Type: application/json');

    include 'db.php';
    require 'google_calendar_service.php';       // ✅ Activado
    include 'utilitarios/fechaCalendar.php';

    $input_json = file_get_contents('php://input');
    $data = json_decode($input_json, true);

    if (!$data) {
        echo json_encode(["success" => false, "message" => "Cuerpo vacío"]);
        exit;
    }

    // ─────────────────────────────────────────────────────────────────────
    // 1. MAPEADO
    // ─────────────────────────────────────────────────────────────────────
    $nombreTour          = $data['nombreTour']          ?? '';
    $name                = !empty($nombreTour) ? $nombreTour : ($data['name'] ?? 'Tour');
    $description         = $data['description']         ?? '';
    $codigoReserva       = $data['codigoReserva']       ?? '';
    $tipoCliente         = $data['tipoCliente']         ?? '';
    $fecha               = $data['fecha']               ?? date('Y-m-d');
    $horaInicio          = $data['horaInicio']          ?? '00:00 AM';
    $turno               = $data['turno']               ?? '';
    $hotelDireccion      = $data['hotelDireccion']      ?? '';
    $duracion            = $data['duracion']            ?? '';
    $nombrePrincipal     = $data['nombrePrincipal']     ?? '';
    $pasajerosAdicionales = is_array($data['pasajerosAdicionales'] ?? null)
        ? json_encode($data['pasajerosAdicionales'])
        : ($data['pasajerosAdicionales'] ?? '[]');
    $pasaporteID         = $data['pasaporteID']         ?? '';
    $countryCodewhatsapp = $data['countryCodewhatsapp'] ?? '';
    $whatsapp            = $data['whatsapp']            ?? '';
    $correo              = $data['correo']              ?? '';
    $habitacion          = $data['habitacion']          ?? '';
    $idioma              = $data['idioma']              ?? '';
    $pais                = $data['pais']                ?? '';
    $tipoPago            = $data['tipoPago']            ?? '';
    $precioPorPersona    = (float)($data['precioPorPersona']  ?? 0);
    $precioTotal         = (float)($data['precioTotal']        ?? 0);
    $precioComisionable  = (float)($data['precioComisionable'] ?? 0);
    $totalComision       = (float)($data['totalComision']      ?? 0);
    $agente              = $data['agente']              ?? '';
    $countryCodewaAgente = $data['countryCodewaAgente'] ?? '';
    $waAgente            = $data['waAgente']            ?? '';
    $observacion         = $data['observacion']         ?? '';
    $driver              = $data['driver']              ?? '';
    $countryCodewaDriver = $data['countryCodewaDriver'] ?? '';
    $waDriver            = $data['waDriver']            ?? '';
    $guia                = $data['guia']                ?? '';
    $countryCodewaGuia   = $data['countryCodewaGuia']   ?? '';
    $waGuia              = $data['waGuia']              ?? '';
    $id_map              = $data['id_map']              ?? '';
    $cantidadPasajero    = $data['cantidadPasajero']    ?? '1';
    $tipoServicio        = $data['tipoServicio']        ?? '';
    $observacionGeneral  = $data['observacionGeneral']  ?? '';
    $status              = "BORRADOR";
    $is_deleted          = 0;
    $FlagActivo          = 1;
    $estadoPago          = $data['estadoPago']          ?? 'PENDIENTE PAGO';
    $estadoReserva       = $data['estadoReserva']       ?? 'COMPLETADA';
    $comprobantePago     = $data['comprobantePago']     ?? '';

    // ─────────────────────────────────────────────────────────────────────
    // 2. GOOGLE CALENDAR
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Extrae las horas de un string de duración.
     * Ejemplos: "4 Horas" → 4 | "18 Horas" → 18 | "3 horas" → 3
     * Si no puede parsear, devuelve 4 como valor por defecto.
     */
    function parsearHorasDuracion(string $duracion): int {
        preg_match('/(\d+)\s*[Hh]ora/i', $duracion, $matches);
        return isset($matches[1]) ? (int)$matches[1] : 4;
    }

    $id_calendar = "";   // Se llenará si Calendar responde correctamente

    // Solo creamos evento si el estadoReserva es COMPLETADA
    // (los BORRADOR no merecen bloquear un slot en el calendario)
    if ($estadoReserva === 'COMPLETADO') {

        $horasDuracion = parsearHorasDuracion($duracion);

        // Fecha/hora de inicio en formato RFC3339 (ej: "2026-02-10T08:00:00-05:00")
        $startDate = convertirAGoogleCalendar($fecha, $horaInicio);

        // Fecha/hora de fin = inicio + horas de duración
        $endDate   = convertirAGoogleCalendar($fecha, $horaInicio, $horasDuracion);

        if ($startDate && $endDate) {

            // Título del evento
            $summary = "$nombreTour X$cantidadPasajero  $tipoServicio($agente)";

            // Descripción enriquecida

            $calDescription= "<b>Código:</b> " . ($codigoReserva ?? '') . "<br>" .
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




            // Ubicación = hotel/dirección del cliente
            $location = $hotelDireccion;

            // Llamada al servicio de Calendar
            $calendarId = crearEventoCalendar(
                $summary,
                $calDescription,
                $location,
                $startDate,
                $endDate,
                $colorId
            );

            if ($calendarId !== false) {
                $id_calendar = $calendarId;
                error_log("Google Calendar: evento creado con ID = $id_calendar");
            } else {
                // Calendar falló, pero NO bloqueamos el guardado en MySQL
                error_log("Google Calendar: falló la creación del evento para $codigoReserva");
            }

        } else {
            error_log("Google Calendar: fecha/hora inválida — fecha=$fecha hora=$horaInicio");
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // 3. INSERT EN MYSQL (con id_calendar real)
    // ─────────────────────────────────────────────────────────────────────
    $sql = "INSERT INTO items (
        name, description, codigoReserva, nombreTour, tipoCliente,
        fecha, horaInicio, turno, hotelDireccion, duracion,
        nombrePrincipal, pasajerosAdicionales, pasaporteID, countryCodewhatsapp, whatsapp,
        correo, habitacion, idioma, pais, tipoPago,
        precioPorPersona, precioTotal, precioComisionable, totalComision, agente,
        countryCodewaAgente, waAgente, observacion, driver, countryCodewaDriver,
        waDriver, guia, countryCodewaGuia, waGuia, id_calendar,
        id_map, cantidadPasajero, tipoServicio, observacionGeneral, status,
        is_deleted, FlagActivo, estadoPago, estadoReserva, comprobantePago
    ) VALUES (
        ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,
        ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,
        ?,?,?,?,?
    )";

    $success  = false;
    $error_db = "";

    if ($stmt = $conn->prepare($sql)) {
        $types = "ssssssssssssssssssssddddsssssssssssssssssiiss";

        $stmt->bind_param($types,
            $name, $description, $codigoReserva, $nombreTour, $tipoCliente,          // 5
            $fecha, $horaInicio, $turno, $hotelDireccion, $duracion,                  // 10
            $nombrePrincipal, $pasajerosAdicionales, $pasaporteID,
                $countryCodewhatsapp, $whatsapp,                                      // 15
            $correo, $habitacion, $idioma, $pais, $tipoPago,                          // 20
            $precioPorPersona, $precioTotal, $precioComisionable, $totalComision,
                $agente,                                                              // 25
            $countryCodewaAgente, $waAgente, $observacion, $driver,
                $countryCodewaDriver,                                                 // 30
            $waDriver, $guia, $countryCodewaGuia, $waGuia, $id_calendar,             // 35
            $id_map, $cantidadPasajero, $tipoServicio, $observacionGeneral, $status, // 40
            $is_deleted, $FlagActivo, $estadoPago, $estadoReserva, $comprobantePago  // 45
        );

        $success = $stmt->execute();
        if (!$success) $error_db = $stmt->error;
        $stmt->close();
    } else {
        $error_db = $conn->error;
    }

    echo json_encode([
        "success"     => $success,
        "id"          => $conn->insert_id,
        "id_calendar" => $id_calendar,        // ✅ Devuelto al cliente Android por si lo necesita
        "message"     => $success ? "OK" : $error_db
    ]);
