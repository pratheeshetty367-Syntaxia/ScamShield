package com.pratheeksha.scamshield.ui.scamdb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScamDatabaseScreen(
    viewModel: ScamDbViewModel = hiltViewModel()
) {
    val scamNumbers by viewModel.scamNumbers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showReportDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Scam Number Database", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search phone number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showReportDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Report a Scam Number")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val filtered = scamNumbers.filter { it.phoneNumber.contains(searchQuery) }

        if (filtered.isEmpty()) {
            Text(
                text = "No reported numbers yet.",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filtered) { entry ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = entry.phoneNumber, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = "${entry.category} • Reported ${entry.reportCount}x",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }

    if (showReportDialog) {
        ReportNumberDialog(
            onDismiss = { showReportDialog = false },
            onSubmit = { number, category ->
                viewModel.reportNumber(number, category)
                showReportDialog = false
            }
        )
    }
}

@Composable
private fun ReportNumberDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    var number by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Report Scam Number") },
        text = {
            Column {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Phone Number") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g. Banking, Lottery)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSubmit(number, category) }) { Text("Submit") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}