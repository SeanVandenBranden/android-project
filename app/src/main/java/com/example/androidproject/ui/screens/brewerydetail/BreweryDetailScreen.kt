package com.example.androidproject.ui.screens.brewerydetail

import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.common.LoadingScreen
import com.example.androidproject.ui.common.UIState
import com.example.androidproject.ui.screens.brewerydetail.components.BreweryDetail
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreweryDetailScreen(onBack: () -> Unit, viewModel: BreweryDetailViewModel = hiltViewModel()) {
    val breweryDetailState by viewModel.uiState.collectAsState()
    val breweryApiState = viewModel.breweryApiState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("quotationDetailScaffold"),
        topBar = {
            TopAppBar(
                title = {
                    if (breweryApiState is UIState.Success) {
                        breweryDetailState.brewery?.let { Text(text = it.name) }
                    } else {
                        breweryDetailState.name?.let { Text(it) }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.contentDescription_menu_back))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .testTag("breweriesOverview"),) {
            when (breweryApiState) {
                is UIState.Loading -> LoadingScreen(
                    text = stringResource(
                        R.string.loading_screen,
                        stringResource(R.string.title_brewery),
                    ),
                )
                is UIState.Error -> Text(breweryApiState.error!!)
                is UIState.Success -> Brewery(brewery = breweryDetailState.brewery!!)
            }
        }
    }
}


@Composable
fun Brewery(
    brewery: Brewery
) {
    BreweryDetail(brewery)
    /*if(!brewery.latitude.isNullOrEmpty() && !brewery.longitude.isNullOrEmpty()){
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(Color.Cyan).padding(top = 16.dp)){
            Map(
                modifier = Modifier.width(200.dp).height(200.dp).padding(top = 16.dp).zIndex(1f),
                latitude = brewery.latitude,
                longitude = brewery.longitude)
        }
    }*/
}

@Composable
fun Map(modifier: Modifier = Modifier, latitude: String, longitude: String){
    AndroidView(
        modifier = modifier,
        factory = { context ->
            // Creates the view
            MapView(context).apply {
                // Do anything that needs to happen on the view init here
                // For example set the tile source or add a click listener
                setTileSource(TileSourceFactory.USGS_TOPO)
                setOnClickListener {
                    TODO("Handle click here")
                }
            }
        },
        update = { view ->
            // Code to update or recompose the view goes here
            // Since geoPoint is read here, the view will recompose whenever it is updated
            var geoPoint by mutableStateOf(GeoPoint(latitude.toDouble(),longitude.toDouble()))
            view.controller.setCenter(geoPoint)
            view.controller.animateTo(geoPoint, 17.0, 1)
        }
    )
}
