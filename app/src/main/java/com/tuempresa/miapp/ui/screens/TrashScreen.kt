package com.tuempresa.miapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuempresa.miapp.data.Item
import com.tuempresa.miapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(navController: NavController, vm: MainViewModel) {

    LaunchedEffect(Unit) { vm.fetchTrash() }
    val trash = vm.trash.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Papelera") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        if (trash.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { Text("No hay reservas eliminadas") }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(trash.distinctBy { it.id }, key = { it.id }) { item ->
                TrashItemCard(
                    item = item,
                    onRestore = { vm.restoreItem(item.id) },
                    onHardDelete = { vm.hardDeleteItem(item.id) }
                )
            }
        }
    }
}

@Composable
private fun TrashItemCard(
    item: Item,
    onRestore: () -> Unit,
    onHardDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = item.nombreTour,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "${item.fecha}  ${item.horaInicio} ${item.turno}")
            Spacer(Modifier.height(6.dp))
            Text(text = "Cliente: ${item.nombrePrincipal}")
            Text(text = "Hotel: ${item.hotelDireccion}")

            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onRestore) {
                    Icon(Icons.Default.Restore, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Restaurar")
                }

                OutlinedButton(onClick = onHardDelete) {
                    Icon(Icons.Default.DeleteForever, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Eliminar definitivo")
                }
            }
        }
    }
}
