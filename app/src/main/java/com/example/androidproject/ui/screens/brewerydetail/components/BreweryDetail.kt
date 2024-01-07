package com.example.androidproject.ui.screens.brewerydetail.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat.startActivity
import com.example.androidproject.R
import com.example.androidproject.model.breweries.Brewery
import com.example.androidproject.ui.screens.brewerydetail.Map
import kotlin.math.abs

@Composable
fun BreweryDetail(
    brewery: Brewery
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_medium))){
        Column {
            Text(
                text = stringResource(R.string.algemene_info),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_extra_small)),
            )
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounding_small)),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth(),
                ) {
                    if(!brewery.breweryType.isNullOrEmpty()){
                        Row{
                            Text("Type brouwerij: ", fontWeight = FontWeight.Bold)
                            Text(brewery.breweryType)
                        }
                    }
                    if(!brewery.websiteUrl.isNullOrEmpty()){
                        Row (verticalAlignment = Alignment.Bottom ){
                            val context = LocalContext.current
                            val annotatedString = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(brewery.websiteUrl)
                                }
                            }
                            Text("Website: ", fontWeight = FontWeight.Bold)
                            ClickableText(text = annotatedString, onClick = {

                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(brewery.websiteUrl))
                                startActivity(context, intent, null)
                            })
                        }
                    }
                }
            }
            Text(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.locatie)
            )
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounding_small)),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .zIndex(2f)
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )){
                        if(!brewery.address1.isNullOrEmpty()){
                            Text(brewery.address1)
                        }
                        if (!brewery.address2.isNullOrEmpty()) {
                            Text(brewery.address2)
                        }
                        if (!brewery.address3.isNullOrEmpty()) {
                            Text(brewery.address3)
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .zIndex(2f)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))){
                        Text("${brewery.city ?: ""}, ${brewery.stateProvince?: brewery.state?: ""} ${brewery.postalCode?: ""}")
                    }
                    if(!brewery.country.isNullOrEmpty()){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .zIndex(2f)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))){
                            Text(brewery.country)
                        }
                    }
                    if(!brewery.latitude.isNullOrEmpty() && !brewery.longitude.isNullOrEmpty()){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .zIndex(2f)
                                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                            text = latitudeToDMS(brewery.latitude))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .zIndex(2f)
                                .padding(
                                    start = dimensionResource(id = R.dimen.padding_medium),
                                    end = dimensionResource(id = R.dimen.padding_medium),
                                    bottom = dimensionResource(id = R.dimen.padding_medium)),
                            text = longitudeToDMS(brewery.longitude)
                        )
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.White)
                            .padding(top = 16.dp)){
                            Map(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .zIndex(1f),
                                latitude = brewery.latitude,
                                longitude = brewery.longitude)
                        }
                    }
                }
            }
        }

    }
}

fun latitudeToDMS(value: String): String {
    val decimalLat = value.toDouble()
    val degreesLat = decimalLat.toInt()
    val minutesLat = ((decimalLat - degreesLat) * 60).toInt()
    val secondsLat = ((decimalLat - degreesLat - minutesLat / 60.0) * 3600)
    return "Breedtegraad: ${abs(degreesLat)}°${abs(minutesLat)}'${"%.2f".format(abs(secondsLat))}\""
}
fun longitudeToDMS(value: String): String {
    val decimalLong = value.toDouble()
    val degreesLong = decimalLong.toInt()
    val minutesLong = ((decimalLong - degreesLong) * 60).toInt()
    val secondsLong = ((decimalLong - degreesLong - minutesLong / 60.0) * 3600)
    return "Lengtegraad: ${abs(degreesLong)}°${abs(minutesLong)}'${"%.2f".format(abs(secondsLong))}\""
}