package com.tuempresa.miapp.data;

/**
 * ARQUITECTURA OFFLINE-FIRST SIMPLE
 * ══════════════════════════════════
 *
 * CON INTERNET:
 *  - INSERT / UPDATE / DELETE → van directo a MySQL vía PHP
 *  - Los datos que se muestran vienen del servidor (sincronizados en Room)
 *
 * SIN INTERNET:
 *  - Las operaciones se guardan en Room con syncStatus = PENDING_*
 *  - La UI muestra lo que hay en Room (última copia conocida + pendientes)
 *
 * AL RECONECTAR:
 *  - syncPendingOperations() recorre Room, envía cada pendiente al servidor
 *  - Si el servidor responde OK → marca como SYNCED (o borra si era DELETE)
 *  - Luego baja el estado fresco del servidor
 *
 * Room NO es caché permanente. Es una cola temporal.
 * MySQL es la fuente de verdad.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010#J\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120&0%J\u0012\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120&0%J\u0016\u0010(\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010)\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010-\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010.\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010/\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ&\u00100\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u00101\u001a\u00020!H\u0086@\u00a2\u0006\u0002\u00102J\u001e\u00103\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/tuempresa/miapp/data/Repository;", "", "api", "Lcom/tuempresa/miapp/data/ApiService;", "db", "Lcom/tuempresa/miapp/data/local/AppDatabase;", "syncPrefs", "Lcom/tuempresa/miapp/data/SyncPreferences;", "(Lcom/tuempresa/miapp/data/ApiService;Lcom/tuempresa/miapp/data/local/AppDatabase;Lcom/tuempresa/miapp/data/SyncPreferences;)V", "dao", "Lcom/tuempresa/miapp/data/local/ItemDao;", "checkForUpdatesAndSync", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createItem", "Lretrofit2/Response;", "Lcom/tuempresa/miapp/data/GenericResponse;", "item", "Lcom/tuempresa/miapp/data/Item;", "(Lcom/tuempresa/miapp/data/Item;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handlePendingCreate", "entity", "Lcom/tuempresa/miapp/data/local/ItemEntity;", "(Lcom/tuempresa/miapp/data/local/ItemEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handlePendingDelete", "handlePendingUpdate", "hardDelete", "serverId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/tuempresa/miapp/data/LoginResponse;", "username", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeItems", "Lkotlinx/coroutines/flow/Flow;", "", "observeTrash", "restore", "saveDraftToRoom", "currentItemId", "(Lcom/tuempresa/miapp/data/Item;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "softDelete", "syncItemsFromServer", "syncPendingOperations", "syncTrashFromServer", "updateEstadoPago", "estadoPago", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateItem", "app_debug"})
public final class Repository {
    @org.jetbrains.annotations.NotNull()
    private final com.tuempresa.miapp.data.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tuempresa.miapp.data.local.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tuempresa.miapp.data.SyncPreferences syncPrefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tuempresa.miapp.data.local.ItemDao dao = null;
    
    public Repository(@org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.ApiService api, @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.local.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.SyncPreferences syncPrefs) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.tuempresa.miapp.data.Item>> observeItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.tuempresa.miapp.data.Item>> observeTrash() {
        return null;
    }
    
    /**
     * Baja los items activos del servidor y los guarda en Room (SYNCED).
     * No toca los registros que están PENDING_* para no pisarlos.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncItemsFromServer(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncTrashFromServer(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Polling inteligente por firma: solo descarga si el servidor cambió.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkForUpdatesAndSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Recorre todos los registros PENDING_* en Room y los envía al servidor.
     * Llamado al reconectar y en el SyncWorker de background.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncPendingOperations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePendingCreate(com.tuempresa.miapp.data.local.ItemEntity entity, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePendingUpdate(com.tuempresa.miapp.data.local.ItemEntity entity, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePendingDelete(com.tuempresa.miapp.data.local.ItemEntity entity, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createItem(@org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateItem(@org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object softDelete(int serverId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object restore(int serverId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * hardDelete requiere internet para eliminar en MySQL.
     * Sin internet: lanza excepcion controlada (NO crashea la app).
     * La UI debe mostrar un mensaje al usuario.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hardDelete(int serverId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateEstadoPago(int serverId, @org.jetbrains.annotations.NotNull()
    java.lang.String estadoPago, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion) {
        return null;
    }
    
    /**
     * Guarda un borrador en Room cuando la app se va a background.
     * No intenta ir al servidor.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveDraftToRoom(@org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.Item item, int currentItemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.LoginResponse>> $completion) {
        return null;
    }
}