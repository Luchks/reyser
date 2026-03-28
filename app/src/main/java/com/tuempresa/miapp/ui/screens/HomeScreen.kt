package com.tuempresa.miapp.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuempresa.miapp.data.Item
import com.tuempresa.miapp.ui.components.PaymentStatusDropdown
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

// 1. Enum para controlar el estado del filtro de servicio
enum class ServiceFilter { ALL, GRUPAL, PRIVADO }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    vm: com.tuempresa.miapp.viewmodel.MainViewModel
) {
    // Carga inicial de datos
    LaunchedEffect(Unit) { vm.fetchItems() }

    val items by vm.items.collectAsState()
    val listState = rememberLazyListState()
    val clipboard = LocalClipboardManager.current

    // ESTADOS DE UI
    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }
    var monthMenuExpanded by remember { mutableStateOf(false) }
    var searchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var serviceFilter by remember { mutableStateOf(ServiceFilter.ALL) }
    var isLoading by remember { mutableStateOf(false) }

    // Resetear isLoading cuando volvemos a la pantalla
    DisposableEffect(Unit) {
        onDispose { isLoading = false }
    }

    // LÓGICA DE FILTRADO POR MES
    val availableMonths = remember(items) {
        val months = items.mapNotNull { it.fecha.toLocalDateOrNull()?.let { d -> YearMonth.from(d) } }
            .distinct()
            .sortedDescending()
        if (months.isNotEmpty()) months else defaultMonthsAroundNow()
    }

    LaunchedEffect(availableMonths) {
        if (availableMonths.isNotEmpty() && selectedMonth !in availableMonths) {
            selectedMonth = availableMonths.first()
        }
    }

    val monthFiltered = remember(items, selectedMonth) {
        items.filter { it.fecha.toLocalDateOrNull()?.let { d -> YearMonth.from(d) == selectedMonth } == true }
    }

    // FILTRO FINAL (Búsqueda + Tipo de Servicio)
    val finalFiltered = remember(monthFiltered, searchQuery, serviceFilter) {
        val q = searchQuery.trim().lowercase()
        monthFiltered.filter { item ->
            val matchesQuery = if (q.isBlank()) true else item.matchesQuery(q)
            val matchesService = when (serviceFilter) {
                ServiceFilter.ALL -> true
                ServiceFilter.GRUPAL -> item.tipoServicio.contains("Grupal", ignoreCase = true)
                ServiceFilter.PRIVADO -> item.tipoServicio.contains("Privado", ignoreCase = true)
            }
            matchesQuery && matchesService
        }
    }

    // AGRUPACIÓN POR DÍA
    val groupedByDay = remember(finalFiltered) {
        finalFiltered
            .mapNotNull { it.fecha.toLocalDateOrNull()?.let { d -> d to it } }
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, list) -> list.sortedBy { it.timeMinutesOrMax() } }
            .toSortedMap(compareByDescending { it })
    }

    val headerIndexForDay = remember(groupedByDay) {
        val map = mutableMapOf<LocalDate, Int>()
        var idx = 0
        groupedByDay.forEach { (date, dayItems) ->
            map[date] = idx
            idx += 1 + dayItems.size
        }
        map.toMap()
    }

    // Auto-scroll al día de hoy si estamos en el mes actual
    LaunchedEffect(selectedMonth, groupedByDay) {
        val today = LocalDate.now()
        if (YearMonth.now() == selectedMonth) {
            headerIndexForDay[today]?.let { target ->
                if (target >= 0) listState.scrollToItem(target)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (searchMode) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                                singleLine = true,
                                placeholder = { Text("Buscar reserva o pasajero...") },
                                trailingIcon = {
                                    IconButton(onClick = { searchQuery = ""; searchMode = false }) {
                                        Icon(Icons.Default.Close, null)
                                    }
                                }
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = selectedMonth.toMonthTitle())
                                IconButton(onClick = { monthMenuExpanded = true }) {
                                    Icon(Icons.Default.KeyboardArrowDown, null)
                                }
                                DropdownMenu(
                                    expanded = monthMenuExpanded,
                                    onDismissRequest = { monthMenuExpanded = false }
                                ) {
                                    availableMonths.forEach { ym ->
                                        DropdownMenuItem(
                                            text = { Text(ym.toMonthTitle()) },
                                            onClick = {
                                                selectedMonth = ym
                                                monthMenuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    actions = {
                        if (!searchMode) {
                            ServiceFilterControls(
                                currentFilter = serviceFilter,
                                onFilterChange = { serviceFilter = it }
                            )
                            IconButton(onClick = { searchMode = true }) {
                                Icon(Icons.Default.Search, contentDescription = "Buscar")
                            }
                        }
                        IconButton(onClick = { navController.navigate("trash") }) {
                            Icon(Icons.Default.Delete, contentDescription = "Papelera")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { 
                        isLoading = true
                        // ✅ RUTA UNIFICADA PARA NUEVO
                        navController.navigate("reservation/-1") 
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Reserva")
                }
            }
        ) { padding ->
            if (groupedByDay.isEmpty()) {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text(if (searchQuery.isNotBlank()) "Sin coincidencias" else "No hay reservas")
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(bottom = 90.dp)
                ) {
                    groupedByDay.forEach { (date, dayItems) ->
                        stickyHeader { DayHeader(date = date) }

                        dayItems.forEach { item ->
                            item(key = item.id) {
                                AgendaItemRow(
                                    item = item,
                                    // ✅ RUTA UNIFICADA PARA EDITAR
                                    onOpen = { navController.navigate("reservation/${item.id}") },
                                    onSoftDelete = { vm.deleteItem(item.id) },
                                    onCopy = {
                                        val text = "Reserva: ${item.codigoReserva}\nCliente: ${item.nombrePrincipal}\nTour: ${item.nombreTour}"
                                        clipboard.setText(AnnotatedString(text))
                                    },
                                    onUpdateEstadoPago = { id, nuevoEstado -> 
                                        vm.updateEstadoPago(id, nuevoEstado)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Overlay de carga
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .pointerInput(Unit) {}, 
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

// --- COMPONENTES DE APOYO ---

@Composable
private fun DayHeader(date: LocalDate) {
    val dayNumber = date.dayOfMonth.toString()
    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES"))
    val monthName = date.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))

    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(44.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(dayNumber, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(dayName.uppercase(), style = MaterialTheme.typography.labelSmall)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = monthName.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgendaItemRow(
    item: Item, 
    onOpen: () -> Unit, 
    onSoftDelete: () -> Unit, 
    onCopy: () -> Unit,
    onUpdateEstadoPago: (Int, String) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it != SwipeToDismissBoxValue.Settled) {
                //onSoftDelete()
                //true
                false
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer).padding(20.dp), Alignment.CenterEnd) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.onErrorContainer)
            }
        },
        content = {
            Row(Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background).padding(horizontal = 12.dp, vertical = 6.dp)) {
                TimelineLeft(item.displayTime())
                
                Card(
                    modifier = Modifier.weight(1f).pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { onOpen() }, 
                            onLongPress = { showMenu = true }
                        )
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = if (item.estadoReserva == "BORRADOR") Color(0xFFE3F2FD) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = if (item.estadoReserva == "BORRADOR") BorderStroke(2.dp, Color(0xFF2196F3)) else null
                ) {
                    Column(Modifier.padding(12.dp)) {
                        if (item.estadoReserva == "BORRADOR") {
                            BadgeBorrador()
                        }
                        
                        Text(
                            "${item.nombreTour} x${item.cantidadPasajero} ${item.tipoServicio}", 
                            color = MaterialTheme.colorScheme.primary, 
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        Text("Hotel: ${item.hotelDireccion}", style = MaterialTheme.typography.bodySmall)
                        Text("Pasajero: ${item.nombrePrincipal}", style = MaterialTheme.typography.bodySmall)
                        Text("Personal: ${item.guia} / ${item.driver}", style = MaterialTheme.typography.bodySmall)
                        
                        Spacer(Modifier.height(8.dp))
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ObservationStatusBadge("Gral", item.observacionGeneral.isNotBlank())
                            ObservationStatusBadge("Int", item.observacion.isNotBlank())
                            PaymentStatusDropdown(
                                currentStatus = item.estadoPago,
                                onStatusChange = { onUpdateEstadoPago(item.id, it) }
                            )
                        }
                    }
                }

                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(text = { Text("Editar") }, onClick = { showMenu = false; onOpen() }, leadingIcon = { Icon(Icons.Default.Edit, null) })
                    DropdownMenuItem(text = { Text("Copiar Info") }, onClick = { showMenu = false; onCopy() }, leadingIcon = { Icon(Icons.Default.ContentCopy, null) })
                    DropdownMenuItem(text = { Text("Eliminar") }, onClick = { showMenu = false; onSoftDelete() }, leadingIcon = { Icon(Icons.Default.Delete, null) })
                }
            }
        }
    )
}

@Composable
private fun BadgeBorrador() {
    Surface(
        color = Color(0xFF2196F3),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        Text(
            "BORRADOR", 
            color = Color.White, 
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun ObservationStatusBadge(label: String, hasContent: Boolean) {
    Surface(
        color = if (hasContent) Color(0xFF4CAF50) else Color.Gray.copy(alpha = 0.5f), 
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun TimelineLeft(timeText: String) {
    Column(modifier = Modifier.width(65.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(timeText, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Box(Modifier.size(8.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
        Box(Modifier.width(2.dp).weight(1f).background(MaterialTheme.colorScheme.outlineVariant))
    }
}

@Composable
private fun ServiceFilterControls(currentFilter: ServiceFilter, onFilterChange: (ServiceFilter) -> Unit) {
    Row(Modifier.padding(end = 8.dp)) {
        FilterCircle(Color.Red, currentFilter == ServiceFilter.PRIVADO) { onFilterChange(ServiceFilter.PRIVADO) }
        Spacer(Modifier.width(6.dp))
        FilterCircle(Color(0xFF4CAF50), currentFilter == ServiceFilter.GRUPAL) { onFilterChange(ServiceFilter.GRUPAL) }
        Spacer(Modifier.width(6.dp))
        FilterCircle(Color.Blue, currentFilter == ServiceFilter.ALL) { onFilterChange(ServiceFilter.ALL) }
    }
}

@Composable
private fun FilterCircle(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier.size(20.dp)
            .background(if (isSelected) color else color.copy(alpha = 0.2f), CircleShape)
            .border(1.5.dp, color, CircleShape)
            .clickable { onClick() }
    )
}

// HELPERS
private fun Item.displayTime(): String = horaInicio.ifBlank { "S/H" }
private fun Item.timeMinutesOrMax(): Int = try {
    val t = LocalTime.parse(horaInicio.uppercase().trim(), DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH))
    t.hour * 60 + t.minute
} catch (_: Exception) { Int.MAX_VALUE }

private fun Item.matchesQuery(q: String): Boolean = 
    nombreTour.lowercase().contains(q) || nombrePrincipal.lowercase().contains(q) || codigoReserva.lowercase().contains(q)

private fun String.toLocalDateOrNull(): LocalDate? = try { LocalDate.parse(this) } catch (_: Exception) { null }
private fun YearMonth.toMonthTitle(): String = month.getDisplayName(TextStyle.FULL, Locale("es")).uppercase() + " " + year
private fun defaultMonthsAroundNow() = (-2..6).map { YearMonth.now().plusMonths(it.toLong()) }.sortedDescending()
