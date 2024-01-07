package com.example.androidproject.ui.screens.randombrewery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.local.breweries.BreweryRepository
import com.example.androidproject.model.breweries.Brewery
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
class RandomBreweryDetailViewModel @Inject constructor(
    private val breweryRepository: BreweryRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        RandomBreweryDetailState(),
    )
    val uiState: StateFlow<RandomBreweryDetailState> = _uiState.asStateFlow()

    var breweryApiState: UIState<Brewery> by mutableStateOf(UIState.Loading())
        private set

    init {
        loadRandomBrewery()
    }
    fun loadRandomBrewery() {
        viewModelScope.launch {
            try{
                val brewery = breweryRepository.getRandom();
                breweryApiState = UIState.Success(
                    brewery,
                )
                _uiState.update {
                    it.copy(
                        brewery = brewery,
                        name = brewery?.name
                    )
                }
            }
            catch(ex: Exception){
                breweryApiState = UIState.Error(ex.message)
            }
        }
    }
}