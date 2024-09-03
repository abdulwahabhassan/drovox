package com.drovox.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drovox.core.design.icon.DrovoxIcons
import com.drovox.core.design.theme.DrovoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrovoxSearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    searchPlaceholder: String,
    trailingIcon: (@Composable () -> Unit)? = null,
    isEnabled: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    placeholderTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    outlined: Boolean = false,
) {
    Row(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium,
            )
            .border(
                width = if (outlined) 1.dp else 0.dp,
                color = contentColor,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .padding(start = 16.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp,
                    ),
                ),

            ) {
            Icon(
                modifier = modifier
                    .size(20.dp)
                    .align(Alignment.Center)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp,
                        ),
                    ),
                imageVector = DrovoxIcons.Search,
                contentDescription = "Search icon",
                tint = contentColor,
            )
        }
        TextField(
            modifier = modifier.weight(1f),
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = searchPlaceholder,
                    style = MaterialTheme.typography.bodyMedium.copy(color = placeholderTextColor),
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
                cursorColor = contentColor
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            enabled = isEnabled
        )
        trailingIcon?.invoke()
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun SearchBarPreview() {
    DrovoxTheme {
        DrovoxSearchBar(
            modifier = Modifier,
            query = "",
            onQueryChange = {},
            searchPlaceholder = "Type a keyword ...",
            trailingIcon = {
                DrovoxClickableIcon(
                    modifier = Modifier.padding(end = 16.dp),
                    icon = DrovoxIcons.Filter,
                    onClick = { /*TODO*/ })
            },
            isEnabled = true
        )
    }
}
