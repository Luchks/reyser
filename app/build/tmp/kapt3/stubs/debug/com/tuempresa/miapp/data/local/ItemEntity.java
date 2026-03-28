package com.tuempresa.miapp.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010 \n\u0003\b\u0080\u0001\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u009b\u00012\u00020\u0001:\u0002\u009b\u0001B\u00d7\u0003\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\r\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0006\u0012\b\b\u0002\u0010\"\u001a\u00020\u0006\u0012\b\b\u0002\u0010#\u001a\u00020\u0006\u0012\b\b\u0002\u0010$\u001a\u00020\u0006\u0012\b\b\u0002\u0010%\u001a\u00020\u0006\u0012\b\b\u0002\u0010&\u001a\u00020\u0006\u0012\b\b\u0002\u0010\'\u001a\u00020\u0006\u0012\b\b\u0002\u0010(\u001a\u00020\u0006\u0012\b\b\u0002\u0010)\u001a\u00020\u0006\u0012\b\b\u0002\u0010*\u001a\u00020\u0006\u0012\b\b\u0002\u0010+\u001a\u00020\u0006\u0012\b\b\u0002\u0010,\u001a\u00020\u0006\u0012\b\b\u0002\u0010-\u001a\u00020\u0006\u0012\b\b\u0002\u0010.\u001a\u00020\u0006\u0012\b\b\u0002\u0010/\u001a\u00020\u0006\u0012\b\b\u0002\u00100\u001a\u00020\u0006\u0012\b\b\u0002\u00101\u001a\u00020\u0006\u0012\b\b\u0002\u00102\u001a\u00020\u0006\u00a2\u0006\u0002\u00103J\t\u0010e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010g\u001a\u00020\u0006H\u00c6\u0003J\t\u0010h\u001a\u00020\u0006H\u00c6\u0003J\t\u0010i\u001a\u00020\u0006H\u00c6\u0003J\t\u0010j\u001a\u00020\u0006H\u00c6\u0003J\t\u0010k\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014H\u00c6\u0003J\t\u0010m\u001a\u00020\u0006H\u00c6\u0003J\t\u0010n\u001a\u00020\u0006H\u00c6\u0003J\t\u0010o\u001a\u00020\u0006H\u00c6\u0003J\t\u0010p\u001a\u00020\u0003H\u00c6\u0003J\t\u0010q\u001a\u00020\u0006H\u00c6\u0003J\t\u0010r\u001a\u00020\u0006H\u00c6\u0003J\t\u0010s\u001a\u00020\u0006H\u00c6\u0003J\t\u0010t\u001a\u00020\u0006H\u00c6\u0003J\t\u0010u\u001a\u00020\u0006H\u00c6\u0003J\t\u0010v\u001a\u00020\u0003H\u00c6\u0003J\t\u0010w\u001a\u00020\u0003H\u00c6\u0003J\t\u0010x\u001a\u00020\u0003H\u00c6\u0003J\t\u0010y\u001a\u00020\u0003H\u00c6\u0003J\t\u0010z\u001a\u00020\u0006H\u00c6\u0003J\t\u0010{\u001a\u00020\u0006H\u00c6\u0003J\t\u0010|\u001a\u00020\u0006H\u00c6\u0003J\t\u0010}\u001a\u00020\u0006H\u00c6\u0003J\t\u0010~\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u007f\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0084\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0090\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0006H\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0006H\u00c6\u0003J\u00dc\u0003\u0010\u0093\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00062\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\u00062\b\b\u0002\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u00062\b\b\u0002\u0010\u0019\u001a\u00020\u00062\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u00062\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u00062\b\b\u0002\u0010\"\u001a\u00020\u00062\b\b\u0002\u0010#\u001a\u00020\u00062\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\b\b\u0002\u0010&\u001a\u00020\u00062\b\b\u0002\u0010\'\u001a\u00020\u00062\b\b\u0002\u0010(\u001a\u00020\u00062\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\b\b\u0002\u0010+\u001a\u00020\u00062\b\b\u0002\u0010,\u001a\u00020\u00062\b\b\u0002\u0010-\u001a\u00020\u00062\b\b\u0002\u0010.\u001a\u00020\u00062\b\b\u0002\u0010/\u001a\u00020\u00062\b\b\u0002\u00100\u001a\u00020\u00062\b\b\u0002\u00101\u001a\u00020\u00062\b\b\u0002\u00102\u001a\u00020\u0006H\u00c6\u0001J\u0016\u0010\u0094\u0001\u001a\u00030\u0095\u00012\t\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0003H\u00d6\u0001J\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001J\n\u0010\u009a\u0001\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010!\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0011\u0010-\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00105R\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00105R\u0011\u00102\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00105R\u0011\u0010\u0018\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u00105R\u0011\u0010\"\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u00105R\u0011\u0010&\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u00105R\u0011\u0010)\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u00105R\u0011\u0010\u0016\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u00105R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u00105R\u0011\u0010%\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u00105R\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00105R\u0011\u00100\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u00105R\u0011\u00101\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u00105R\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u00105R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0011\u0010(\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u00105R\u0011\u0010\u0019\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u00105R\u0011\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u00105R\u0011\u0010\u0010\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u00105R\u0011\u0010+\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u00105R\u0011\u0010,\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u00105R\u0011\u0010\u001a\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00105R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u00105R\u0011\u0010\u0012\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u00105R\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u00105R\u0011\u0010$\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u00105R\u0011\u0010/\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u00105R\u0011\u0010\u001b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u00105R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0011\u0010\u0015\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u00105R\u0011\u0010\u001f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010ER\u0011\u0010\u001d\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010ER\u0011\u0010\u001e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010ER\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010ER\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010ER\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u00105R\u0011\u0010\f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u00105R\u0011\u0010\u001c\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u00105R\u0011\u0010.\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u00105R\u0011\u0010 \u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u0010ER\u0011\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u00105R\u0011\u0010#\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u00105R\u0011\u0010\'\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bb\u00105R\u0011\u0010*\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u00105R\u0011\u0010\u0017\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u00105\u00a8\u0006\u009c\u0001"}, d2 = {"Lcom/tuempresa/miapp/data/local/ItemEntity;", "", "roomId", "", "serverId", "syncStatus", "", "flagActivo", "name", "description", "codigoReserva", "nombreTour", "tipoCliente", "fecha", "horaInicio", "turno", "hotelDireccion", "duracion", "nombrePrincipal", "pasajerosAdicionales", "", "pasaporteID", "countryCodewhatsapp", "whatsapp", "correo", "habitacion", "idioma", "pais", "tipoPago", "precioPorPersona", "precioTotal", "precioComisionable", "totalComision", "agente", "countryCodewaAgente", "waAgente", "observacion", "driver", "countryCodewaDriver", "waDriver", "guia", "countryCodewaGuia", "waGuia", "id_calendar", "id_map", "cantidadPasajero", "tipoServicio", "observacionGeneral", "estadoPago", "estadoReserva", "comprobantePago", "(IILjava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAgente", "()Ljava/lang/String;", "getCantidadPasajero", "getCodigoReserva", "getComprobantePago", "getCorreo", "getCountryCodewaAgente", "getCountryCodewaDriver", "getCountryCodewaGuia", "getCountryCodewhatsapp", "getDescription", "getDriver", "getDuracion", "getEstadoPago", "getEstadoReserva", "getFecha", "getFlagActivo", "()I", "getGuia", "getHabitacion", "getHoraInicio", "getHotelDireccion", "getId_calendar", "getId_map", "getIdioma", "getName", "getNombrePrincipal", "getNombreTour", "getObservacion", "getObservacionGeneral", "getPais", "getPasajerosAdicionales", "()Ljava/util/List;", "getPasaporteID", "getPrecioComisionable", "getPrecioPorPersona", "getPrecioTotal", "getRoomId", "getServerId", "getSyncStatus", "getTipoCliente", "getTipoPago", "getTipoServicio", "getTotalComision", "getTurno", "getWaAgente", "getWaDriver", "getWaGuia", "getWhatsapp", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toItem", "Lcom/tuempresa/miapp/data/Item;", "toString", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "items", indices = {@androidx.room.Index(value = {"serverId"}, unique = true)})
public final class ItemEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int roomId = 0;
    private final int serverId = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String syncStatus = null;
    private final int flagActivo = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String codigoReserva = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nombreTour = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tipoCliente = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fecha = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String horaInicio = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String turno = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String hotelDireccion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String duracion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nombrePrincipal = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> pasajerosAdicionales = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pasaporteID = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String countryCodewhatsapp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String whatsapp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String correo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String habitacion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String idioma = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pais = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tipoPago = null;
    private final int precioPorPersona = 0;
    private final int precioTotal = 0;
    private final int precioComisionable = 0;
    private final int totalComision = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String agente = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String countryCodewaAgente = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String waAgente = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String observacion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String driver = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String countryCodewaDriver = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String waDriver = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String guia = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String countryCodewaGuia = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String waGuia = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id_calendar = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id_map = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cantidadPasajero = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tipoServicio = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String observacionGeneral = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String estadoPago = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String estadoReserva = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String comprobantePago = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.tuempresa.miapp.data.local.ItemEntity.Companion Companion = null;
    
    public ItemEntity(int roomId, int serverId, @org.jetbrains.annotations.NotNull()
    java.lang.String syncStatus, int flagActivo, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String codigoReserva, @org.jetbrains.annotations.NotNull()
    java.lang.String nombreTour, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoCliente, @org.jetbrains.annotations.NotNull()
    java.lang.String fecha, @org.jetbrains.annotations.NotNull()
    java.lang.String horaInicio, @org.jetbrains.annotations.NotNull()
    java.lang.String turno, @org.jetbrains.annotations.NotNull()
    java.lang.String hotelDireccion, @org.jetbrains.annotations.NotNull()
    java.lang.String duracion, @org.jetbrains.annotations.NotNull()
    java.lang.String nombrePrincipal, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> pasajerosAdicionales, @org.jetbrains.annotations.NotNull()
    java.lang.String pasaporteID, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewhatsapp, @org.jetbrains.annotations.NotNull()
    java.lang.String whatsapp, @org.jetbrains.annotations.NotNull()
    java.lang.String correo, @org.jetbrains.annotations.NotNull()
    java.lang.String habitacion, @org.jetbrains.annotations.NotNull()
    java.lang.String idioma, @org.jetbrains.annotations.NotNull()
    java.lang.String pais, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoPago, int precioPorPersona, int precioTotal, int precioComisionable, int totalComision, @org.jetbrains.annotations.NotNull()
    java.lang.String agente, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaAgente, @org.jetbrains.annotations.NotNull()
    java.lang.String waAgente, @org.jetbrains.annotations.NotNull()
    java.lang.String observacion, @org.jetbrains.annotations.NotNull()
    java.lang.String driver, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaDriver, @org.jetbrains.annotations.NotNull()
    java.lang.String waDriver, @org.jetbrains.annotations.NotNull()
    java.lang.String guia, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaGuia, @org.jetbrains.annotations.NotNull()
    java.lang.String waGuia, @org.jetbrains.annotations.NotNull()
    java.lang.String id_calendar, @org.jetbrains.annotations.NotNull()
    java.lang.String id_map, @org.jetbrains.annotations.NotNull()
    java.lang.String cantidadPasajero, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoServicio, @org.jetbrains.annotations.NotNull()
    java.lang.String observacionGeneral, @org.jetbrains.annotations.NotNull()
    java.lang.String estadoPago, @org.jetbrains.annotations.NotNull()
    java.lang.String estadoReserva, @org.jetbrains.annotations.NotNull()
    java.lang.String comprobantePago) {
        super();
    }
    
    public final int getRoomId() {
        return 0;
    }
    
    public final int getServerId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSyncStatus() {
        return null;
    }
    
    public final int getFlagActivo() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCodigoReserva() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNombreTour() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTipoCliente() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFecha() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHoraInicio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTurno() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHotelDireccion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDuracion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNombrePrincipal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPasajerosAdicionales() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPasaporteID() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCountryCodewhatsapp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWhatsapp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCorreo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHabitacion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIdioma() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPais() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTipoPago() {
        return null;
    }
    
    public final int getPrecioPorPersona() {
        return 0;
    }
    
    public final int getPrecioTotal() {
        return 0;
    }
    
    public final int getPrecioComisionable() {
        return 0;
    }
    
    public final int getTotalComision() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAgente() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCountryCodewaAgente() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWaAgente() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getObservacion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDriver() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCountryCodewaDriver() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWaDriver() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGuia() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCountryCodewaGuia() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getWaGuia() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId_calendar() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId_map() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCantidadPasajero() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTipoServicio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getObservacionGeneral() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEstadoPago() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEstadoReserva() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getComprobantePago() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tuempresa.miapp.data.Item toItem() {
        return null;
    }
    
    public ItemEntity() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component24() {
        return null;
    }
    
    public final int component25() {
        return 0;
    }
    
    public final int component26() {
        return 0;
    }
    
    public final int component27() {
        return 0;
    }
    
    public final int component28() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component30() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component31() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component32() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component33() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component34() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component35() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component36() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component37() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component39() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component40() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component41() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component42() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component43() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component44() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component45() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component46() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.tuempresa.miapp.data.local.ItemEntity copy(int roomId, int serverId, @org.jetbrains.annotations.NotNull()
    java.lang.String syncStatus, int flagActivo, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String codigoReserva, @org.jetbrains.annotations.NotNull()
    java.lang.String nombreTour, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoCliente, @org.jetbrains.annotations.NotNull()
    java.lang.String fecha, @org.jetbrains.annotations.NotNull()
    java.lang.String horaInicio, @org.jetbrains.annotations.NotNull()
    java.lang.String turno, @org.jetbrains.annotations.NotNull()
    java.lang.String hotelDireccion, @org.jetbrains.annotations.NotNull()
    java.lang.String duracion, @org.jetbrains.annotations.NotNull()
    java.lang.String nombrePrincipal, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> pasajerosAdicionales, @org.jetbrains.annotations.NotNull()
    java.lang.String pasaporteID, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewhatsapp, @org.jetbrains.annotations.NotNull()
    java.lang.String whatsapp, @org.jetbrains.annotations.NotNull()
    java.lang.String correo, @org.jetbrains.annotations.NotNull()
    java.lang.String habitacion, @org.jetbrains.annotations.NotNull()
    java.lang.String idioma, @org.jetbrains.annotations.NotNull()
    java.lang.String pais, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoPago, int precioPorPersona, int precioTotal, int precioComisionable, int totalComision, @org.jetbrains.annotations.NotNull()
    java.lang.String agente, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaAgente, @org.jetbrains.annotations.NotNull()
    java.lang.String waAgente, @org.jetbrains.annotations.NotNull()
    java.lang.String observacion, @org.jetbrains.annotations.NotNull()
    java.lang.String driver, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaDriver, @org.jetbrains.annotations.NotNull()
    java.lang.String waDriver, @org.jetbrains.annotations.NotNull()
    java.lang.String guia, @org.jetbrains.annotations.NotNull()
    java.lang.String countryCodewaGuia, @org.jetbrains.annotations.NotNull()
    java.lang.String waGuia, @org.jetbrains.annotations.NotNull()
    java.lang.String id_calendar, @org.jetbrains.annotations.NotNull()
    java.lang.String id_map, @org.jetbrains.annotations.NotNull()
    java.lang.String cantidadPasajero, @org.jetbrains.annotations.NotNull()
    java.lang.String tipoServicio, @org.jetbrains.annotations.NotNull()
    java.lang.String observacionGeneral, @org.jetbrains.annotations.NotNull()
    java.lang.String estadoPago, @org.jetbrains.annotations.NotNull()
    java.lang.String estadoReserva, @org.jetbrains.annotations.NotNull()
    java.lang.String comprobantePago) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n\u00a8\u0006\u000b"}, d2 = {"Lcom/tuempresa/miapp/data/local/ItemEntity$Companion;", "", "()V", "fromItem", "Lcom/tuempresa/miapp/data/local/ItemEntity;", "item", "Lcom/tuempresa/miapp/data/Item;", "syncStatus", "Lcom/tuempresa/miapp/data/local/SyncStatus;", "roomId", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Construye un [ItemEntity] desde un [Item] del servidor.
         */
        @org.jetbrains.annotations.NotNull()
        public final com.tuempresa.miapp.data.local.ItemEntity fromItem(@org.jetbrains.annotations.NotNull()
        com.tuempresa.miapp.data.Item item, @org.jetbrains.annotations.NotNull()
        com.tuempresa.miapp.data.local.SyncStatus syncStatus, int roomId) {
            return null;
        }
    }
}