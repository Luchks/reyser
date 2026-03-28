package com.tuempresa.miapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00062\b\b\u0001\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\u0016\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u001f"}, d2 = {"Lcom/tuempresa/miapp/data/ApiService;", "", "checkUpdates", "Lcom/tuempresa/miapp/data/UpdateCheckResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createItem", "Lretrofit2/Response;", "Lcom/tuempresa/miapp/data/GenericResponse;", "item", "Lcom/tuempresa/miapp/data/Item;", "(Lcom/tuempresa/miapp/data/Item;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getItems", "", "getTrash", "hardDelete", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/tuempresa/miapp/data/LoginResponse;", "username", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "restore", "softDelete", "updateEstadoPago", "request", "Lcom/tuempresa/miapp/data/UpdateEstadoPagoRequest;", "(Lcom/tuempresa/miapp/data/UpdateEstadoPagoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateItem", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.FormUrlEncoded()
    @retrofit2.http.POST(value = "login.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Field(value = "username")
    @org.jetbrains.annotations.NotNull()
    java.lang.String username, @retrofit2.http.Field(value = "password")
    @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.LoginResponse>> $completion);
    
    @retrofit2.http.GET(value = "get_items.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tuempresa.miapp.data.Item>> $completion);
    
    @retrofit2.http.GET(value = "get_trash.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTrash(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.tuempresa.miapp.data.Item>> $completion);
    
    @retrofit2.http.POST(value = "create_item.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createItem(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.POST(value = "update_item.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateItem(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.Item item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.FormUrlEncoded()
    @retrofit2.http.POST(value = "delete_item.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@retrofit2.http.Field(value = "id")
    int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.FormUrlEncoded()
    @retrofit2.http.POST(value = "restore_item.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object restore(@retrofit2.http.Field(value = "id")
    int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.FormUrlEncoded()
    @retrofit2.http.POST(value = "hard_delete_item.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@retrofit2.http.Field(value = "id")
    int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.POST(value = "update_estado_pago.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateEstadoPago(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.data.UpdateEstadoPagoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.tuempresa.miapp.data.GenericResponse>> $completion);
    
    @retrofit2.http.GET(value = "check_updates.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object checkUpdates(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.tuempresa.miapp.data.UpdateCheckResponse> $completion);
}