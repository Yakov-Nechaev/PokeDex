package com.kforeach.pokedex.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatLineBox(
    modifier: Modifier = Modifier,
    lab: String,
    value: Int,
    max: Int = 255,
    cod: Int
) {
    val progress = (value / max.toFloat()).coerceIn(0f, 1f)
    val fillColor = Color(cod)

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(lab, modifier = Modifier.width(48.dp))
        VerticalDivider(
            modifier = Modifier
                .height(16.dp)
                .padding(horizontal = 6.dp),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.4f)
        )
        Text(value.toString().padStart(3, '0'), modifier = Modifier.width(40.dp))


        Box(
            modifier = Modifier
                .height(6.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(999.dp))
                .background(fillColor.copy(alpha = 0.25f)) // трек
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)               // заполнение
                    .background(fillColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemStatLinePreview() {
    StatLineBox(
        lab = "HP",
        value = 50 ,
       cod = 0xFFF2C94C.toInt())
}