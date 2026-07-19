package com.pratheeksha.scamshield.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pratheeksha.scamshield.domain.model.RiskLevel
import com.pratheeksha.scamshield.domain.model.ScanRecord

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProtectionStatusCard(
                isActive = uiState.protectionStatus.isActive,
                scansToday = uiState.protectionStatus.totalScansToday,
                scamsBlocked = uiState.protectionStatus.scamsBlockedToday,
                onToggle = { viewModel.toggleProtection() }
            )
        }

        item {
            Button(
                onClick = { viewModel.testScamAlert() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Test Scam Alert Notification")
            }
        }

        item {
            Text(
                text = "Recent Calls",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(uiState.recentScans) { scan ->
            ScanRecordCard(scan = scan)
        }
    }
}

@Composable
private fun ProtectionStatusCard(
    isActive: Boolean,
    scansToday: Int,
    scamsBlocked: Int,
    onToggle: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) Color(0xFF2E7D32) else Color(0xFF9E9E9E)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isActive) "Protection Active" else "Protection Paused",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatColumn(label = "Scans Today", value = scansToday.toString())
                StatColumn(label = "Scams Blocked", value = scamsBlocked.toString())
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = onToggle) {
                Text(if (isActive) "Pause Protection" else "Resume Protection")
            }
        }
    }
}

@Composable
private fun StatColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.headlineSmall)
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ScanRecordCard(scan: ScanRecord) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RiskBadge(riskLevel = scan.riskLevel)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = scan.callerName, style = MaterialTheme.typography.bodyLarge)
                Text(text = scan.phoneNumber, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = scan.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun RiskBadge(riskLevel: RiskLevel) {
    val color = when (riskLevel) {
        RiskLevel.LOW -> Color(0xFF2E7D32)
        RiskLevel.MEDIUM -> Color(0xFFF9A825)
        RiskLevel.HIGH -> Color(0xFFE64A19)
        RiskLevel.CRITICAL -> Color(0xFFC62828)
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
    )
}