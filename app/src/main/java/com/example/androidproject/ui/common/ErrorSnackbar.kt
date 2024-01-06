package com.example.androidproject.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.androidproject.R
import com.example.androidproject.ui.LocalSnackbarHostState

@Composable
fun ErrorSnackbar(isError: Boolean, onErrorConsumed: () -> Unit, errorMessage: String) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val okText = context.getString(R.string.ok)

    if (isError) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = okText,
            )
            onErrorConsumed()
        }
    }
}
