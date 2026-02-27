package com.kforeach.pokedex.ui.items

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kforeach.pokedex.R
import com.kforeach.pokedex.models.PokemonShort
import com.kforeach.pokedex.models.getImageUrl
import com.kforeach.pokedex.models.getPokemonId

@Composable
fun ItemPoc(
    pokemonShort: PokemonShort,
    modifier: Modifier = Modifier,
    onSelect: (codColor: Int) -> Unit
) {

    val isInPreview = LocalInspectionMode.current
    var codColor = Color.White.toArgb()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(size = 10.dp))
            .clip(shape = RoundedCornerShape(size = 14.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(colors = listOf(Color.White, Color.LightGray, Color.Gray))
            )
            .clickable { onSelect(codColor) }
    ) {


        if (isInPreview) {
            Image(
                painter = painterResource(R.drawable.pichu),
                contentDescription = pokemonShort.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
            )
        } else {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonShort.getImageUrl())
                    .crossfade(true)
                    .build(),
            )
            val state = painter.state
            if (state is AsyncImagePainter.State.Success) {


                val bmp =(state.result.drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

                Palette.from(bmp).generate { palette ->
                    palette?.dominantSwatch?.rgb?.let {
                        codColor = it
                    }
                }
            }

            Image(
                painter = painter,
                contentDescription = pokemonShort.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
            )
        }


        Text(
            text = pokemonShort.getPokemonId(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
        )

        Text(
            text = pokemonShort.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    val list = List(10) { PokemonShort.previewPokemonShort() }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list.size) { index ->
            ItemPoc(pokemonShort = list[index], onSelect = {})
        }
    }
}

