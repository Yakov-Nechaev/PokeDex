package com.kforeach.pokedex.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kforeach.pokedex.R
import com.kforeach.pokedex.models.Pokemon

@Composable
fun ItemImage(
    pokemon: Pokemon,
    topPadding: Dp,
    sizeImage: Dp,
    isInPreview: Boolean = false,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = topPadding)
            .height(sizeImage)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {

        if (isInPreview) {
            Image(
                painter = painterResource(R.drawable.pichu),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(sizeImage)
                    .align(Alignment.Center)
            )
        } else {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
            )

            Image(
                painter = painter,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(sizeImage)
                    .align(Alignment.Center)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ItemImagePreview() {
    Box(modifier = Modifier.background(color = Color.LightGray)) {
        ItemImage(
            pokemon = Pokemon.getPreviewPokemon(),
            topPadding = 0.dp,
            sizeImage = 120.dp,
            isInPreview = true,
        )
    }
}