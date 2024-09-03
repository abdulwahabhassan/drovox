package com.drovox.core.design.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drovox.core.design.theme.DrovoxTheme

@Composable
fun DrovoxOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.primary,
    outlineColor: Color = MaterialTheme.colorScheme.primary,
    startIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
            .border(
                width = 1.dp,
                color = if (isEnabled) outlineColor else MaterialTheme.colorScheme.inversePrimary,
                shape = MaterialTheme.shapes.small,
            ),
        enabled = isEnabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = MaterialTheme.colorScheme.inversePrimary,
        ),

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (startIcon != null) {
                startIcon()
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DrovoxOutlinedButtonPreview1() {
    DrovoxTheme {
        DrovoxOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            text = "No",
            onClick = {},
            isEnabled = true,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DrovoxOutlinedButtonPreview2() {
    DrovoxTheme {
        DrovoxOutlinedButton(
            modifier = Modifier.padding(32.dp),
            text = "Yes",
            onClick = {},
            isEnabled = true,
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            textColor = MaterialTheme.colorScheme.error,
            outlineColor = Color.Unspecified,
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun DrovoxOutlinedButtonPreview3() {
    DrovoxTheme {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DrovoxOutlinedButton(
                modifier = Modifier.weight(1f).padding(16.dp),
                text = "Disabled",
                onClick = {},
                isEnabled = false,
            )
            DrovoxOutlinedButton(
                modifier = Modifier.weight(1f).padding(16.dp),
                text = "No",
                onClick = {},
                isEnabled = true,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DrovoxOutlinedButtonPreview4() {
    DrovoxTheme {
        DrovoxOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            text = "Disabled",
            onClick = {},
            isEnabled = false,
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            textColor = MaterialTheme.colorScheme.error,
            outlineColor = Color.Unspecified,
        )
    }
}
