package com.drovox.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drovox.core.design.R
import com.drovox.core.design.icon.DrovoxIcons
import com.drovox.core.design.theme.DrovoxTheme

@Composable
fun DrovoxClickableIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color = Color.Unspecified,
    rippleColor: Color = MaterialTheme.colorScheme.primaryContainer,
    isEnabled: Boolean = true,
    backgroundColor: Color = Color.Unspecified,
    backgroundShape: Shape = CircleShape,
    iconPaddingValues: PaddingValues = PaddingValues(4.dp)
) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(R.string.desc_clickable_icon),
        tint = color,
        modifier = modifier
            .background(color = backgroundColor, shape = backgroundShape)
            .clip(MaterialTheme.shapes.small)
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = rippleColor,
                ),
            )
            .padding(iconPaddingValues),
    )
}

@Composable
@Preview(showBackground = true)
private fun DrovoxClickableIconPreview1() {
    DrovoxTheme {
        DrovoxClickableIcon(
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            icon = DrovoxIcons.NotificationBell_01,
            onClick = {},
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DrovoxClickableIconPreview2() {
    DrovoxTheme {
        DrovoxClickableIcon(
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            icon = DrovoxIcons.ChevronLeft,
            onClick = {},
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
