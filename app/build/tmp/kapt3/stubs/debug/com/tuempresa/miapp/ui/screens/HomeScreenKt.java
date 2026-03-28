package com.tuempresa.miapp.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000d\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u001aT\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\tH\u0003\u001a\b\u0010\f\u001a\u00020\u0001H\u0003\u001a\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0003\u001a0\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0018\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007\u001a\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u0014H\u0003\u001a$\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\"2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00010$H\u0003\u001a\u0010\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020\u000bH\u0003\u001a\u0016\u0010\'\u001a\u0010\u0012\f\u0012\n **\u0004\u0018\u00010)0)0(H\u0002\u001a\f\u0010+\u001a\u00020\u000b*\u00020\u0003H\u0002\u001a\u0014\u0010,\u001a\u00020\u0014*\u00020\u00032\u0006\u0010-\u001a\u00020\u000bH\u0002\u001a\f\u0010.\u001a\u00020\n*\u00020\u0003H\u0002\u001a\u000e\u0010/\u001a\u0004\u0018\u00010\u000f*\u00020\u000bH\u0002\u001a\f\u00100\u001a\u00020\u000b*\u00020)H\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00061"}, d2 = {"AgendaItemRow", "", "item", "Lcom/tuempresa/miapp/data/Item;", "onOpen", "Lkotlin/Function0;", "onSoftDelete", "onCopy", "onUpdateEstadoPago", "Lkotlin/Function2;", "", "", "BadgeBorrador", "DayHeader", "date", "Ljava/time/LocalDate;", "FilterCircle", "color", "Landroidx/compose/ui/graphics/Color;", "isSelected", "", "onClick", "FilterCircle-ek8zF_U", "(JZLkotlin/jvm/functions/Function0;)V", "HomeScreen", "navController", "Landroidx/navigation/NavController;", "vm", "Lcom/tuempresa/miapp/viewmodel/MainViewModel;", "ObservationStatusBadge", "label", "hasContent", "ServiceFilterControls", "currentFilter", "Lcom/tuempresa/miapp/ui/screens/ServiceFilter;", "onFilterChange", "Lkotlin/Function1;", "TimelineLeft", "timeText", "defaultMonthsAroundNow", "", "Ljava/time/YearMonth;", "kotlin.jvm.PlatformType", "displayTime", "matchesQuery", "q", "timeMinutesOrMax", "toLocalDateOrNull", "toMonthTitle", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.tuempresa.miapp.viewmodel.MainViewModel vm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DayHeader(java.time.LocalDate date) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void AgendaItemRow(com.tuempresa.miapp.data.Item item, kotlin.jvm.functions.Function0<kotlin.Unit> onOpen, kotlin.jvm.functions.Function0<kotlin.Unit> onSoftDelete, kotlin.jvm.functions.Function0<kotlin.Unit> onCopy, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onUpdateEstadoPago) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BadgeBorrador() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ObservationStatusBadge(java.lang.String label, boolean hasContent) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TimelineLeft(java.lang.String timeText) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ServiceFilterControls(com.tuempresa.miapp.ui.screens.ServiceFilter currentFilter, kotlin.jvm.functions.Function1<? super com.tuempresa.miapp.ui.screens.ServiceFilter, kotlin.Unit> onFilterChange) {
    }
    
    private static final java.lang.String displayTime(com.tuempresa.miapp.data.Item $this$displayTime) {
        return null;
    }
    
    private static final int timeMinutesOrMax(com.tuempresa.miapp.data.Item $this$timeMinutesOrMax) {
        return 0;
    }
    
    private static final boolean matchesQuery(com.tuempresa.miapp.data.Item $this$matchesQuery, java.lang.String q) {
        return false;
    }
    
    private static final java.time.LocalDate toLocalDateOrNull(java.lang.String $this$toLocalDateOrNull) {
        return null;
    }
    
    private static final java.lang.String toMonthTitle(java.time.YearMonth $this$toMonthTitle) {
        return null;
    }
    
    private static final java.util.List<java.time.YearMonth> defaultMonthsAroundNow() {
        return null;
    }
}