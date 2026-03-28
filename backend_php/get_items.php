<?php
    header('Content-Type: application/json');
    include 'db.php';

    /**
     * Helpers de saneamiento
     */
    function nstr($v) {
        return $v === null ? "" : (string)$v;
    }

    function nint($v) {
        return $v === null ? 0 : (int)$v;
    }

    $result = $conn->query("SELECT * FROM items WHERE FlagActivo = 1 ORDER BY id DESC");

    $items = [];

    while ($row = $result->fetch_assoc()) {

        // ─────────────────────────────────────────────
        // PASAJEROS (siempre array)
        // ─────────────────────────────────────────────
        $rawPasajeros = $row['pasajerosAdicionales'] ?? '[]';
        $decoded = json_decode($rawPasajeros, true);
        $row['pasajerosAdicionales'] = is_array($decoded) ? $decoded : [];

        // ─────────────────────────────────────────────
        // ENTEROS (Android espera Int)
        // ─────────────────────────────────────────────
        $row['id']                 = nint($row['id']);
        $row['precioPorPersona']   = nint($row['precioPorPersona']);
        $row['precioTotal']        = nint($row['precioTotal']);
        $row['precioComisionable'] = nint($row['precioComisionable']);
        $row['totalComision']      = nint($row['totalComision']);
        $row['FlagActivo']         = nint($row['FlagActivo']);

        // ─────────────────────────────────────────────
        // STRINGS (CRÍTICO para evitar crash de Gson)
        // ─────────────────────────────────────────────
        $stringFields = [
            'name','description','codigoReserva','nombreTour','tipoCliente',
            'fecha','horaInicio','turno','hotelDireccion','duracion',
            'nombrePrincipal','pasaporteID','countryCodewhatsapp','whatsapp',
            'correo','habitacion','idioma','pais','tipoPago','agente',
            'countryCodewaAgente','waAgente','observacion','driver',
            'countryCodewaDriver','waDriver','guia','countryCodewaGuia',
            'waGuia','id_calendar','id_map','cantidadPasajero','tipoServicio',
            'observacionGeneral','status','estadoPago','estadoReserva',
            'ComprobantePago'
        ];

        foreach ($stringFields as $field) {
            if (array_key_exists($field, $row)) {
                $row[$field] = nstr($row[$field]);
            }
        }

        // 🔥 IMPORTANTE: normalizar nombre para Android (camelCase)
        if (isset($row['ComprobantePago'])) {
            $row['comprobantePago'] = $row['ComprobantePago'];
            unset($row['ComprobantePago']);
        }

        $items[] = $row;
    }

    echo json_encode($items);
    $conn->close();
?>
