package com.example.androidproject.ui.screens.brewerydetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.local.breweries.BreweryRepository
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.ProjectDestinationsArgs
import com.example.androidproject.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreweryDetailViewModel @Inject constructor(
    private val breweryRepository: BreweryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val breweryId: String = savedStateHandle[ProjectDestinationsArgs.BREWERY_ID_ARG]!!

    private val _uiState = MutableStateFlow(
        BreweryDetailState(),
    )
    val uiState: StateFlow<BreweryDetailState> = _uiState.asStateFlow()

    var breweryApiState: UIState<Brewery> by mutableStateOf(UIState.Loading())
        private set

    init {
        loadBrewery(breweryId)
    }
    private fun loadBrewery(id: String) {
        viewModelScope.launch {
            breweryRepository.getById(id)
                .catch { exception ->
                    breweryApiState = UIState.Error(exception.message)
                }
                .collect { brewery ->
                    breweryApiState = UIState.Success(
                        brewery,
                    )
                    _uiState.update {
                        it.copy(
                            brewery = brewery,
                            name = brewery.name
                        )
                    }
                }
        }
    }
}