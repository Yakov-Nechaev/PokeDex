package com.kforeach.pokedex.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    placeholder: String = "",
    readOnly: Boolean = false,
    iconStart: (@Composable () -> Unit)? = null,
    iconEnd: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    textRowAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    val isFocused = remember { mutableStateOf(false) }


    Column {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            readOnly = readOnly,
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            modifier = modifier
                .height(40.dp)
                .background(Color.White, RoundedCornerShape(30))
                .border(1.dp, Color.Gray, RoundedCornerShape(30))
                .padding(horizontal = 3.dp, vertical = 3.dp)
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = textRowAlignment,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp)
                ) {
                    if (iconStart != null) {
                        iconStart()
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = if (iconStart == null) 8.dp else 4.dp, end = 4.dp)
                            .then(if (singleLine) Modifier.horizontalScroll(
                                rememberScrollState()
                            ) else Modifier)
                        ,
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        innerTextField()
                    }
                    if (iconEnd != null) {
                        iconEnd()
                    }
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    val text = remember { mutableStateOf("") }

    CustomTextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = "Search...",
        iconEnd = { Icon(imageVector = Icons.Default.Clear, contentDescription = "") },
        iconStart = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
    )
}
