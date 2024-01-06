package com.example.androidproject.ui.screens.breweriesoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class BreweriesOverviewViewModel @Inject constructor() : ViewModel() {
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
        val breweries = listOf( // TODO this is just to quicklly test the listview, this should be deleted once api calls are implemented  (api should be used instead)
            Brewery("154b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
            Brewery("254b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
            Brewery("354b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
        )
        viewModelScope.launch {
            // TODO get breweries here
            kotlinx.coroutines.delay(1000) // TODO delay to simulate loading time, should be removed when not in development
            breweryApiState = UIState.Success(breweries)
            _uiState.update {
                it.copy(breweries = breweries) // TODO
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
        val breweries = listOf( // TODO this is just to quicklly test the listview, this should be deleted once api calls are implemented  (api should be used instead)
            Brewery("154b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
            Brewery("254b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
            Brewery("354b16e1-ac3b-4bff-a11f-f7ae9ddc27e0", "MadTree Brewing 2.0", "regional", "5164 Kennedy Ave", null, null, "Cincinnati", "Ohio", "45213", "United States", "-84.4137736", "39.1885752", "5138368733", "http://www.madtreebrewing.com", "Ohio", "5164 Kennedy Ave"),
        )
        viewModelScope.launch(exceptionHandler) {
            // TODO get breweries here
            kotlinx.coroutines.delay(1000) // TODO delay to simulate loading time, should be removed when not in development
            breweryApiState = UIState.Success(breweries)
            _uiState.update {
                it.copy(breweries = breweries) // TODO
            }
            _uiState.update {
                it.copy(isRefreshing = false)
            }
        }
    }
}
