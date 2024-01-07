package com.example.androidproject.ui.screens.breweriesoverview

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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreweriesOverviewViewModel @Inject constructor(
    private val breweryRepository: BreweryRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BreweriesOverviewState(false, null))
    val uiState: StateFlow<BreweriesOverviewState> = _uiState.asStateFlow()

    var breweryApiState: UIState<List<Brewery>> by mutableStateOf(UIState.Loading())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        exception.printStackTrace()
        showError()
    }

    init {
        refreshBreweries(isVisibleRefresh = false)
        loadBreweries()
    }

    private fun loadBreweries() {
        viewModelScope.launch() {
            // TODO get breweries here
            breweryRepository
                .getAll()
                .catch { exception ->
                    breweryApiState = UIState.Error(exception.message)
                    exception.printStackTrace()
                }
                .collect { breweries ->
                    breweryApiState = UIState.Success(breweries)
                    _uiState.update {
                        it.copy(breweries = breweries)
                    }
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

    fun refreshBreweries(isVisibleRefresh: Boolean = true) {
        if (isVisibleRefresh) {
            _uiState.update {
                it.copy(isRefreshing = true)
            }
        }
        viewModelScope.launch() {
            val refreshBreweriesDeferred = async { breweryRepository.refreshBreweries() }
            try {
                awaitAll(refreshBreweriesDeferred)
            }
            finally {
                _uiState.update {
                    it.copy(isRefreshing = false)
                }
            }
        }
    }
}
