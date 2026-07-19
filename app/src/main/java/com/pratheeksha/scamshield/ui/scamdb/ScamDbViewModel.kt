package com.pratheeksha.scamshield.ui.scamdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratheeksha.scamshield.data.local.ScamNumberEntity
import com.pratheeksha.scamshield.data.repository.ScamNumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScamDbViewModel @Inject constructor(
    private val repository: ScamNumberRepository
) : ViewModel() {

    val scamNumbers: StateFlow<List<ScamNumberEntity>> = repository.getAllScamNumbers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun reportNumber(phoneNumber: String, category: String) {
        viewModelScope.launch {
            repository.reportNumber(phoneNumber, category)
        }
    }
}