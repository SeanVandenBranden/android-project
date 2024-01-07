package com.example.androidproject.ui.screens.breweriesmetadata

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidproject.R
import com.example.androidproject.ui.common.ErrorSnackbar

@Composable
fun BreweriesMetadataScreen(
    breweriesMetadataViewModel: BreweriesMetadataViewModel = hiltViewModel()
) {
    val breweriesMetadataState by breweriesMetadataViewModel.uiState.collectAsState()
    val breweryApiState = breweriesMetadataViewModel.breweryApiState


    ErrorSnackbar(isError = breweriesMetadataState.isError, onErrorConsumed = breweriesMetadataViewModel::onErrorConsumed, stringResource(R.string.metadata_error))
    Box( modifier = Modifier
        .fillMaxWidth()
        .testTag("breweriesOverview") ) {
        Box{
            Column {
                Text("Totaal aantal brouwerijen:")
                breweriesMetadataState.metadata?.let { Text(text = it.total) }
            }
        }
    }
}


