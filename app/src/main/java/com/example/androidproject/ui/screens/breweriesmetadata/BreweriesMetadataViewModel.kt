package com.example.androidproject.ui.screens.breweriesmetadata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.local.breweries.BreweryRepository
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreweriesMetadataViewModel @Inject constructor(
    private val breweryRepository: BreweryRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BreweriesMetadataState(false))
    val uiState: StateFlow<BreweriesMetadataState> = _uiState.asStateFlow()

    var breweryApiState: UIState<List<Brewery>> by mutableStateOf(UIState.Loading())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        exception.printStackTrace()
        showError()
    }

    init {
        loadBreweries()
    }

    private fun loadBreweries() {
        viewModelScope.launch {
            var metadata = breweryRepository.getMetadata()
            _uiState.update {
                it.copy(metadata = metadata)
            }
        }
    }

    fun onErrorConsumed() {
        _uiState.update {
            it.copy(isError = false)
        }
    }

    private fun showError() {
        _uiState.update {
            it.copy(isError = true)
        }
    }


}
