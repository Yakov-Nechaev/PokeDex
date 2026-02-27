package com.kforeach.pokedex.ui.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.kforeach.pokedex.ui.theme.PrimaryColor

@Composable
fun DialogInfo(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        title = {
            Text(
                text = "Información",
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Esta aplicación muestra una lista de Pokémon con la posibilidad de buscar y obtener información detallada. Realizada como parte de una prueba técnica para desarrollador Android para verificar habilidades técnicas en una entrevista.",
                fontSize = 16.sp
            )
        },
        confirmButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                Text("Continuar", color = Color.White)
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}

@Preview
@Composable
fun DialogInfoPreview() {
    DialogInfo(onDismiss = {})
}
