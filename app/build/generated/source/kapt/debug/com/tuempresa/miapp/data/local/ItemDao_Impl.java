package com.tuempresa.miapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ItemDao_Impl implements ItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ItemEntity> __insertionAdapterOfItemEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkSyncedByRoomId;

  private final SharedSQLiteStatement __preparedStmtOfSoftDeleteByServerId;

  private final SharedSQLiteStatement __preparedStmtOfRestoreByServerId;

  private final SharedSQLiteStatement __preparedStmtOfUpdateEstadoPago;

  private final SharedSQLiteStatement __preparedStmtOfHardDeleteByServerId;

  private final SharedSQLiteStatement __preparedStmtOfHardDeleteByRoomId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTrash;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSynced;

  public ItemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItemEntity = new EntityInsertionAdapter<ItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `items` (`roomId`,`serverId`,`syncStatus`,`flagActivo`,`name`,`description`,`codigoReserva`,`nombreTour`,`tipoCliente`,`fecha`,`horaInicio`,`turno`,`hotelDireccion`,`duracion`,`nombrePrincipal`,`pasajerosAdicionales`,`pasaporteID`,`countryCodewhatsapp`,`whatsapp`,`correo`,`habitacion`,`idioma`,`pais`,`tipoPago`,`precioPorPersona`,`precioTotal`,`precioComisionable`,`totalComision`,`agente`,`countryCodewaAgente`,`waAgente`,`observacion`,`driver`,`countryCodewaDriver`,`waDriver`,`guia`,`countryCodewaGuia`,`waGuia`,`id_calendar`,`id_map`,`cantidadPasajero`,`tipoServicio`,`observacionGeneral`,`estadoPago`,`estadoReserva`,`comprobantePago`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemEntity entity) {
        statement.bindLong(1, entity.getRoomId());
        statement.bindLong(2, entity.getServerId());
        if (entity.getSyncStatus() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSyncStatus());
        }
        statement.bindLong(4, entity.getFlagActivo());
        if (entity.getName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDescription());
        }
        if (entity.getCodigoReserva() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCodigoReserva());
        }
        if (entity.getNombreTour() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNombreTour());
        }
        if (entity.getTipoCliente() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTipoCliente());
        }
        if (entity.getFecha() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getFecha());
        }
        if (entity.getHoraInicio() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getHoraInicio());
        }
        if (entity.getTurno() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTurno());
        }
        if (entity.getHotelDireccion() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getHotelDireccion());
        }
        if (entity.getDuracion() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getDuracion());
        }
        if (entity.getNombrePrincipal() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getNombrePrincipal());
        }
        final String _tmp = __converters.fromStringList(entity.getPasajerosAdicionales());
        if (_tmp == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp);
        }
        if (entity.getPasaporteID() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getPasaporteID());
        }
        if (entity.getCountryCodewhatsapp() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getCountryCodewhatsapp());
        }
        if (entity.getWhatsapp() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getWhatsapp());
        }
        if (entity.getCorreo() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getCorreo());
        }
        if (entity.getHabitacion() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getHabitacion());
        }
        if (entity.getIdioma() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getIdioma());
        }
        if (entity.getPais() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getPais());
        }
        if (entity.getTipoPago() == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, entity.getTipoPago());
        }
        statement.bindLong(25, entity.getPrecioPorPersona());
        statement.bindLong(26, entity.getPrecioTotal());
        statement.bindLong(27, entity.getPrecioComisionable());
        statement.bindLong(28, entity.getTotalComision());
        if (entity.getAgente() == null) {
          statement.bindNull(29);
        } else {
          statement.bindString(29, entity.getAgente());
        }
        if (entity.getCountryCodewaAgente() == null) {
          statement.bindNull(30);
        } else {
          statement.bindString(30, entity.getCountryCodewaAgente());
        }
        if (entity.getWaAgente() == null) {
          statement.bindNull(31);
        } else {
          statement.bindString(31, entity.getWaAgente());
        }
        if (entity.getObservacion() == null) {
          statement.bindNull(32);
        } else {
          statement.bindString(32, entity.getObservacion());
        }
        if (entity.getDriver() == null) {
          statement.bindNull(33);
        } else {
          statement.bindString(33, entity.getDriver());
        }
        if (entity.getCountryCodewaDriver() == null) {
          statement.bindNull(34);
        } else {
          statement.bindString(34, entity.getCountryCodewaDriver());
        }
        if (entity.getWaDriver() == null) {
          statement.bindNull(35);
        } else {
          statement.bindString(35, entity.getWaDriver());
        }
        if (entity.getGuia() == null) {
          statement.bindNull(36);
        } else {
          statement.bindString(36, entity.getGuia());
        }
        if (entity.getCountryCodewaGuia() == null) {
          statement.bindNull(37);
        } else {
          statement.bindString(37, entity.getCountryCodewaGuia());
        }
        if (entity.getWaGuia() == null) {
          statement.bindNull(38);
        } else {
          statement.bindString(38, entity.getWaGuia());
        }
        if (entity.getId_calendar() == null) {
          statement.bindNull(39);
        } else {
          statement.bindString(39, entity.getId_calendar());
        }
        if (entity.getId_map() == null) {
          statement.bindNull(40);
        } else {
          statement.bindString(40, entity.getId_map());
        }
        if (entity.getCantidadPasajero() == null) {
          statement.bindNull(41);
        } else {
          statement.bindString(41, entity.getCantidadPasajero());
        }
        if (entity.getTipoServicio() == null) {
          statement.bindNull(42);
        } else {
          statement.bindString(42, entity.getTipoServicio());
        }
        if (entity.getObservacionGeneral() == null) {
          statement.bindNull(43);
        } else {
          statement.bindString(43, entity.getObservacionGeneral());
        }
        if (entity.getEstadoPago() == null) {
          statement.bindNull(44);
        } else {
          statement.bindString(44, entity.getEstadoPago());
        }
        if (entity.getEstadoReserva() == null) {
          statement.bindNull(45);
        } else {
          statement.bindString(45, entity.getEstadoReserva());
        }
        if (entity.getComprobantePago() == null) {
          statement.bindNull(46);
        } else {
          statement.bindString(46, entity.getComprobantePago());
        }
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE items \n"
                + "        SET serverId = ?, syncStatus = 'SYNCED'\n"
                + "        WHERE roomId = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSyncedByRoomId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE items SET syncStatus = 'SYNCED' WHERE roomId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSoftDeleteByServerId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE items \n"
                + "        SET flagActivo = 0, syncStatus = 'PENDING_DELETE' \n"
                + "        WHERE serverId = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfRestoreByServerId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE items \n"
                + "        SET flagActivo = 1, syncStatus = 'PENDING_UPDATE'\n"
                + "        WHERE serverId = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateEstadoPago = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE items \n"
                + "        SET estadoPago = ?, syncStatus = 'PENDING_UPDATE'\n"
                + "        WHERE serverId = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfHardDeleteByServerId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items WHERE serverId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfHardDeleteByRoomId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items WHERE roomId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllTrash = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items WHERE flagActivo = 0 AND syncStatus = 'SYNCED'";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items WHERE syncStatus = 'SYNCED' AND flagActivo = 1";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final ItemEntity entity, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfItemEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<ItemEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfItemEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replaceActiveCache(final List<ItemEntity> serverItems,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> ItemDao.DefaultImpls.replaceActiveCache(ItemDao_Impl.this, serverItems, __cont), $completion);
  }

  @Override
  public Object markSynced(final int roomId, final int serverId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, serverId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, roomId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSyncedByRoomId(final int roomId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSyncedByRoomId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, roomId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSyncedByRoomId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object softDeleteByServerId(final int serverId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDeleteByServerId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, serverId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDeleteByServerId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object restoreByServerId(final int serverId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRestoreByServerId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, serverId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfRestoreByServerId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEstadoPago(final int serverId, final String estadoPago,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateEstadoPago.acquire();
        int _argIndex = 1;
        if (estadoPago == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, estadoPago);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, serverId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateEstadoPago.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hardDeleteByServerId(final int serverId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDeleteByServerId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, serverId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfHardDeleteByServerId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object hardDeleteByRoomId(final int roomId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDeleteByRoomId.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, roomId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfHardDeleteByRoomId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllTrash(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTrash.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllTrash.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllSynced(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSynced.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllSyncedTrash(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTrash.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllTrash.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ItemEntity>> observeActiveItems() {
    final String _sql = "SELECT * FROM items WHERE flagActivo = 1 ORDER BY fecha DESC, horaInicio DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _item = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ItemEntity>> observeTrashItems() {
    final String _sql = "SELECT * FROM items WHERE flagActivo = 0 ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"items"}, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _item = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getByRoomId(final int roomId, final Continuation<? super ItemEntity> $completion) {
    final String _sql = "SELECT * FROM items WHERE roomId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, roomId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _result = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByServerId(final int serverId,
      final Continuation<? super ItemEntity> $completion) {
    final String _sql = "SELECT * FROM items WHERE serverId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serverId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _result = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByCodigoReserva(final String codigo,
      final Continuation<? super ItemEntity> $completion) {
    final String _sql = "SELECT * FROM items WHERE codigoReserva = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (codigo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, codigo);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ItemEntity>() {
      @Override
      @Nullable
      public ItemEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final ItemEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _result = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingSync(final Continuation<? super List<ItemEntity>> $completion) {
    final String _sql = "SELECT * FROM items WHERE syncStatus != 'SYNCED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ItemEntity>>() {
      @Override
      @NonNull
      public List<ItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfServerId = CursorUtil.getColumnIndexOrThrow(_cursor, "serverId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfFlagActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "flagActivo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCodigoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "codigoReserva");
          final int _cursorIndexOfNombreTour = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreTour");
          final int _cursorIndexOfTipoCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoCliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final int _cursorIndexOfHoraInicio = CursorUtil.getColumnIndexOrThrow(_cursor, "horaInicio");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfHotelDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "hotelDireccion");
          final int _cursorIndexOfDuracion = CursorUtil.getColumnIndexOrThrow(_cursor, "duracion");
          final int _cursorIndexOfNombrePrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "nombrePrincipal");
          final int _cursorIndexOfPasajerosAdicionales = CursorUtil.getColumnIndexOrThrow(_cursor, "pasajerosAdicionales");
          final int _cursorIndexOfPasaporteID = CursorUtil.getColumnIndexOrThrow(_cursor, "pasaporteID");
          final int _cursorIndexOfCountryCodewhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewhatsapp");
          final int _cursorIndexOfWhatsapp = CursorUtil.getColumnIndexOrThrow(_cursor, "whatsapp");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfHabitacion = CursorUtil.getColumnIndexOrThrow(_cursor, "habitacion");
          final int _cursorIndexOfIdioma = CursorUtil.getColumnIndexOrThrow(_cursor, "idioma");
          final int _cursorIndexOfPais = CursorUtil.getColumnIndexOrThrow(_cursor, "pais");
          final int _cursorIndexOfTipoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoPago");
          final int _cursorIndexOfPrecioPorPersona = CursorUtil.getColumnIndexOrThrow(_cursor, "precioPorPersona");
          final int _cursorIndexOfPrecioTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "precioTotal");
          final int _cursorIndexOfPrecioComisionable = CursorUtil.getColumnIndexOrThrow(_cursor, "precioComisionable");
          final int _cursorIndexOfTotalComision = CursorUtil.getColumnIndexOrThrow(_cursor, "totalComision");
          final int _cursorIndexOfAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "agente");
          final int _cursorIndexOfCountryCodewaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaAgente");
          final int _cursorIndexOfWaAgente = CursorUtil.getColumnIndexOrThrow(_cursor, "waAgente");
          final int _cursorIndexOfObservacion = CursorUtil.getColumnIndexOrThrow(_cursor, "observacion");
          final int _cursorIndexOfDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "driver");
          final int _cursorIndexOfCountryCodewaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaDriver");
          final int _cursorIndexOfWaDriver = CursorUtil.getColumnIndexOrThrow(_cursor, "waDriver");
          final int _cursorIndexOfGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "guia");
          final int _cursorIndexOfCountryCodewaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCodewaGuia");
          final int _cursorIndexOfWaGuia = CursorUtil.getColumnIndexOrThrow(_cursor, "waGuia");
          final int _cursorIndexOfIdCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "id_calendar");
          final int _cursorIndexOfIdMap = CursorUtil.getColumnIndexOrThrow(_cursor, "id_map");
          final int _cursorIndexOfCantidadPasajero = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadPasajero");
          final int _cursorIndexOfTipoServicio = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoServicio");
          final int _cursorIndexOfObservacionGeneral = CursorUtil.getColumnIndexOrThrow(_cursor, "observacionGeneral");
          final int _cursorIndexOfEstadoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoPago");
          final int _cursorIndexOfEstadoReserva = CursorUtil.getColumnIndexOrThrow(_cursor, "estadoReserva");
          final int _cursorIndexOfComprobantePago = CursorUtil.getColumnIndexOrThrow(_cursor, "comprobantePago");
          final List<ItemEntity> _result = new ArrayList<ItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemEntity _item;
            final int _tmpRoomId;
            _tmpRoomId = _cursor.getInt(_cursorIndexOfRoomId);
            final int _tmpServerId;
            _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
            final String _tmpSyncStatus;
            if (_cursor.isNull(_cursorIndexOfSyncStatus)) {
              _tmpSyncStatus = null;
            } else {
              _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            }
            final int _tmpFlagActivo;
            _tmpFlagActivo = _cursor.getInt(_cursorIndexOfFlagActivo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCodigoReserva;
            if (_cursor.isNull(_cursorIndexOfCodigoReserva)) {
              _tmpCodigoReserva = null;
            } else {
              _tmpCodigoReserva = _cursor.getString(_cursorIndexOfCodigoReserva);
            }
            final String _tmpNombreTour;
            if (_cursor.isNull(_cursorIndexOfNombreTour)) {
              _tmpNombreTour = null;
            } else {
              _tmpNombreTour = _cursor.getString(_cursorIndexOfNombreTour);
            }
            final String _tmpTipoCliente;
            if (_cursor.isNull(_cursorIndexOfTipoCliente)) {
              _tmpTipoCliente = null;
            } else {
              _tmpTipoCliente = _cursor.getString(_cursorIndexOfTipoCliente);
            }
            final String _tmpFecha;
            if (_cursor.isNull(_cursorIndexOfFecha)) {
              _tmpFecha = null;
            } else {
              _tmpFecha = _cursor.getString(_cursorIndexOfFecha);
            }
            final String _tmpHoraInicio;
            if (_cursor.isNull(_cursorIndexOfHoraInicio)) {
              _tmpHoraInicio = null;
            } else {
              _tmpHoraInicio = _cursor.getString(_cursorIndexOfHoraInicio);
            }
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final String _tmpHotelDireccion;
            if (_cursor.isNull(_cursorIndexOfHotelDireccion)) {
              _tmpHotelDireccion = null;
            } else {
              _tmpHotelDireccion = _cursor.getString(_cursorIndexOfHotelDireccion);
            }
            final String _tmpDuracion;
            if (_cursor.isNull(_cursorIndexOfDuracion)) {
              _tmpDuracion = null;
            } else {
              _tmpDuracion = _cursor.getString(_cursorIndexOfDuracion);
            }
            final String _tmpNombrePrincipal;
            if (_cursor.isNull(_cursorIndexOfNombrePrincipal)) {
              _tmpNombrePrincipal = null;
            } else {
              _tmpNombrePrincipal = _cursor.getString(_cursorIndexOfNombrePrincipal);
            }
            final List<String> _tmpPasajerosAdicionales;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPasajerosAdicionales)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPasajerosAdicionales);
            }
            _tmpPasajerosAdicionales = __converters.toStringList(_tmp);
            final String _tmpPasaporteID;
            if (_cursor.isNull(_cursorIndexOfPasaporteID)) {
              _tmpPasaporteID = null;
            } else {
              _tmpPasaporteID = _cursor.getString(_cursorIndexOfPasaporteID);
            }
            final String _tmpCountryCodewhatsapp;
            if (_cursor.isNull(_cursorIndexOfCountryCodewhatsapp)) {
              _tmpCountryCodewhatsapp = null;
            } else {
              _tmpCountryCodewhatsapp = _cursor.getString(_cursorIndexOfCountryCodewhatsapp);
            }
            final String _tmpWhatsapp;
            if (_cursor.isNull(_cursorIndexOfWhatsapp)) {
              _tmpWhatsapp = null;
            } else {
              _tmpWhatsapp = _cursor.getString(_cursorIndexOfWhatsapp);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpHabitacion;
            if (_cursor.isNull(_cursorIndexOfHabitacion)) {
              _tmpHabitacion = null;
            } else {
              _tmpHabitacion = _cursor.getString(_cursorIndexOfHabitacion);
            }
            final String _tmpIdioma;
            if (_cursor.isNull(_cursorIndexOfIdioma)) {
              _tmpIdioma = null;
            } else {
              _tmpIdioma = _cursor.getString(_cursorIndexOfIdioma);
            }
            final String _tmpPais;
            if (_cursor.isNull(_cursorIndexOfPais)) {
              _tmpPais = null;
            } else {
              _tmpPais = _cursor.getString(_cursorIndexOfPais);
            }
            final String _tmpTipoPago;
            if (_cursor.isNull(_cursorIndexOfTipoPago)) {
              _tmpTipoPago = null;
            } else {
              _tmpTipoPago = _cursor.getString(_cursorIndexOfTipoPago);
            }
            final int _tmpPrecioPorPersona;
            _tmpPrecioPorPersona = _cursor.getInt(_cursorIndexOfPrecioPorPersona);
            final int _tmpPrecioTotal;
            _tmpPrecioTotal = _cursor.getInt(_cursorIndexOfPrecioTotal);
            final int _tmpPrecioComisionable;
            _tmpPrecioComisionable = _cursor.getInt(_cursorIndexOfPrecioComisionable);
            final int _tmpTotalComision;
            _tmpTotalComision = _cursor.getInt(_cursorIndexOfTotalComision);
            final String _tmpAgente;
            if (_cursor.isNull(_cursorIndexOfAgente)) {
              _tmpAgente = null;
            } else {
              _tmpAgente = _cursor.getString(_cursorIndexOfAgente);
            }
            final String _tmpCountryCodewaAgente;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaAgente)) {
              _tmpCountryCodewaAgente = null;
            } else {
              _tmpCountryCodewaAgente = _cursor.getString(_cursorIndexOfCountryCodewaAgente);
            }
            final String _tmpWaAgente;
            if (_cursor.isNull(_cursorIndexOfWaAgente)) {
              _tmpWaAgente = null;
            } else {
              _tmpWaAgente = _cursor.getString(_cursorIndexOfWaAgente);
            }
            final String _tmpObservacion;
            if (_cursor.isNull(_cursorIndexOfObservacion)) {
              _tmpObservacion = null;
            } else {
              _tmpObservacion = _cursor.getString(_cursorIndexOfObservacion);
            }
            final String _tmpDriver;
            if (_cursor.isNull(_cursorIndexOfDriver)) {
              _tmpDriver = null;
            } else {
              _tmpDriver = _cursor.getString(_cursorIndexOfDriver);
            }
            final String _tmpCountryCodewaDriver;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaDriver)) {
              _tmpCountryCodewaDriver = null;
            } else {
              _tmpCountryCodewaDriver = _cursor.getString(_cursorIndexOfCountryCodewaDriver);
            }
            final String _tmpWaDriver;
            if (_cursor.isNull(_cursorIndexOfWaDriver)) {
              _tmpWaDriver = null;
            } else {
              _tmpWaDriver = _cursor.getString(_cursorIndexOfWaDriver);
            }
            final String _tmpGuia;
            if (_cursor.isNull(_cursorIndexOfGuia)) {
              _tmpGuia = null;
            } else {
              _tmpGuia = _cursor.getString(_cursorIndexOfGuia);
            }
            final String _tmpCountryCodewaGuia;
            if (_cursor.isNull(_cursorIndexOfCountryCodewaGuia)) {
              _tmpCountryCodewaGuia = null;
            } else {
              _tmpCountryCodewaGuia = _cursor.getString(_cursorIndexOfCountryCodewaGuia);
            }
            final String _tmpWaGuia;
            if (_cursor.isNull(_cursorIndexOfWaGuia)) {
              _tmpWaGuia = null;
            } else {
              _tmpWaGuia = _cursor.getString(_cursorIndexOfWaGuia);
            }
            final String _tmpId_calendar;
            if (_cursor.isNull(_cursorIndexOfIdCalendar)) {
              _tmpId_calendar = null;
            } else {
              _tmpId_calendar = _cursor.getString(_cursorIndexOfIdCalendar);
            }
            final String _tmpId_map;
            if (_cursor.isNull(_cursorIndexOfIdMap)) {
              _tmpId_map = null;
            } else {
              _tmpId_map = _cursor.getString(_cursorIndexOfIdMap);
            }
            final String _tmpCantidadPasajero;
            if (_cursor.isNull(_cursorIndexOfCantidadPasajero)) {
              _tmpCantidadPasajero = null;
            } else {
              _tmpCantidadPasajero = _cursor.getString(_cursorIndexOfCantidadPasajero);
            }
            final String _tmpTipoServicio;
            if (_cursor.isNull(_cursorIndexOfTipoServicio)) {
              _tmpTipoServicio = null;
            } else {
              _tmpTipoServicio = _cursor.getString(_cursorIndexOfTipoServicio);
            }
            final String _tmpObservacionGeneral;
            if (_cursor.isNull(_cursorIndexOfObservacionGeneral)) {
              _tmpObservacionGeneral = null;
            } else {
              _tmpObservacionGeneral = _cursor.getString(_cursorIndexOfObservacionGeneral);
            }
            final String _tmpEstadoPago;
            if (_cursor.isNull(_cursorIndexOfEstadoPago)) {
              _tmpEstadoPago = null;
            } else {
              _tmpEstadoPago = _cursor.getString(_cursorIndexOfEstadoPago);
            }
            final String _tmpEstadoReserva;
            if (_cursor.isNull(_cursorIndexOfEstadoReserva)) {
              _tmpEstadoReserva = null;
            } else {
              _tmpEstadoReserva = _cursor.getString(_cursorIndexOfEstadoReserva);
            }
            final String _tmpComprobantePago;
            if (_cursor.isNull(_cursorIndexOfComprobantePago)) {
              _tmpComprobantePago = null;
            } else {
              _tmpComprobantePago = _cursor.getString(_cursorIndexOfComprobantePago);
            }
            _item = new ItemEntity(_tmpRoomId,_tmpServerId,_tmpSyncStatus,_tmpFlagActivo,_tmpName,_tmpDescription,_tmpCodigoReserva,_tmpNombreTour,_tmpTipoCliente,_tmpFecha,_tmpHoraInicio,_tmpTurno,_tmpHotelDireccion,_tmpDuracion,_tmpNombrePrincipal,_tmpPasajerosAdicionales,_tmpPasaporteID,_tmpCountryCodewhatsapp,_tmpWhatsapp,_tmpCorreo,_tmpHabitacion,_tmpIdioma,_tmpPais,_tmpTipoPago,_tmpPrecioPorPersona,_tmpPrecioTotal,_tmpPrecioComisionable,_tmpTotalComision,_tmpAgente,_tmpCountryCodewaAgente,_tmpWaAgente,_tmpObservacion,_tmpDriver,_tmpCountryCodewaDriver,_tmpWaDriver,_tmpGuia,_tmpCountryCodewaGuia,_tmpWaGuia,_tmpId_calendar,_tmpId_map,_tmpCantidadPasajero,_tmpTipoServicio,_tmpObservacionGeneral,_tmpEstadoPago,_tmpEstadoReserva,_tmpComprobantePago);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldItemsNotInServer(final List<Integer> serverIds,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("\n");
        _stringBuilder.append("        DELETE FROM items ");
        _stringBuilder.append("\n");
        _stringBuilder.append("        WHERE serverId NOT IN (");
        final int _inputSize = serverIds.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(") ");
        _stringBuilder.append("\n");
        _stringBuilder.append("          AND syncStatus = 'SYNCED' ");
        _stringBuilder.append("\n");
        _stringBuilder.append("          AND flagActivo = 1");
        _stringBuilder.append("\n");
        _stringBuilder.append("    ");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (Integer _item : serverIds) {
          if (_item == null) {
            _stmt.bindNull(_argIndex);
          } else {
            _stmt.bindLong(_argIndex, _item);
          }
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldTrashNotInServer(final List<Integer> serverIds,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("\n");
        _stringBuilder.append("        DELETE FROM items ");
        _stringBuilder.append("\n");
        _stringBuilder.append("        WHERE serverId NOT IN (");
        final int _inputSize = serverIds.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(") ");
        _stringBuilder.append("\n");
        _stringBuilder.append("          AND syncStatus = 'SYNCED' ");
        _stringBuilder.append("\n");
        _stringBuilder.append("          AND flagActivo = 0");
        _stringBuilder.append("\n");
        _stringBuilder.append("    ");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (Integer _item : serverIds) {
          if (_item == null) {
            _stmt.bindNull(_argIndex);
          } else {
            _stmt.bindLong(_argIndex, _item);
          }
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
