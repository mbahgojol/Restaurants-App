package com.blank.composerestaurant.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier, title: String, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = title)
        })
    }, content = content, modifier = modifier)
}

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    title: String,
    navigateBack: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = title)
        }, navigationIcon = {
            BackButton(navigateBack = navigateBack)
        })
    }, content = content, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun CustomScaffoldBackPreview() {
    CustomScaffold(title = "Custom Scaffold", navigateBack = {}) {

    }
}

@Preview(showBackground = true)
@Composable
fun CustomScaffoldPreview() {
    CustomScaffold(title = "Custom Scaffold") {

    }
}