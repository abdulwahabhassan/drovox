package com.drovox.core.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.drovox.core.design.R
import com.drovox.core.design.icon.DrovoxIcons
import com.drovox.core.design.theme.DecorativeColor
import com.drovox.core.design.theme.DrovoxTheme

@Composable
fun DrovoxCenterDialog(
    title: String,
    subtitle: String? = null,
    icon: @Composable (() -> Unit)? = null,
    positiveActionText: String? = null,
    positiveAction: () -> Unit = {},
    negativeActionText: String? = null,
    negativeAction: () -> Unit = {},
    showDialog: Boolean,
    onTriggerPositiveOrNegativeAction: () -> Unit = {},
    onDismissIconClick: (() -> Unit)? = null,
    extraContent: @Composable (() -> Unit)? = null,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (icon != null && onDismissIconClick != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    icon()
                                }
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    DrovoxClickableIcon(
                                        Modifier.align(Alignment.TopEnd),
                                        icon = DrovoxIcons.Close,
                                        onClick = onDismissIconClick,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = DecorativeColor.dark
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                subtitle?.let {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 10,
                                    )
                                }
                            }
                        } else if (icon != null) {
                            icon()
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = DecorativeColor.dark
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                subtitle?.let {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 10,
                                    )
                                }
                            }
                        } else if (onDismissIconClick != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(modifier = Modifier.weight(1f))
                                Column(
                                    modifier = Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                        .padding(horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = DecorativeColor.dark
                                        ),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    subtitle?.let {
                                        Text(
                                            text = subtitle,
                                            style = MaterialTheme.typography.bodyMedium,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 10,
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    DrovoxClickableIcon(
                                        Modifier.align(Alignment.TopEnd),
                                        icon = DrovoxIcons.Close,
                                        onClick = onDismissIconClick,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier.padding(top = 8.dp).padding(horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = DecorativeColor.dark
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                subtitle?.let {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 10,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            if (positiveActionText != null) {
                                DrovoxOutlinedButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp)
                                        .fillMaxWidth(),
                                    text = positiveActionText,
                                    onClick = {
                                        positiveAction()
                                        onTriggerPositiveOrNegativeAction()
                                    },
                                )
                            }
                            if (negativeActionText != null) {
                                DrovoxFilledButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp)
                                        .fillMaxWidth(),
                                    text = negativeActionText,
                                    onClick = {
                                        negativeAction()
                                        onTriggerPositiveOrNegativeAction()
                                    },
                                    backgroundColor = MaterialTheme.colorScheme.errorContainer,
                                    textColor = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                        if (extraContent != null) {
                            extraContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DrovoxCenterDialogPreview1() {
    DrovoxTheme {
        val context = LocalContext.current
        DrovoxCenterDialog(
            title = stringResource(R.string.title_cancel_transaction),
            subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_cancel),
            positiveActionText = stringResource(R.string.action_no),
            positiveAction = {
            },
            negativeActionText = stringResource(R.string.action_yes_cancel),
            showDialog = true,
            onTriggerPositiveOrNegativeAction = {},
            onDismissIconClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DrovoxCenterDialogPreview2() {
    DrovoxTheme {
        DrovoxCenterDialog(
            title = "Check your internet connection",
            subtitle = "Check your internet connection and try again",
            positiveActionText = "Okay",
            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = DrovoxIcons.Warning,
                    contentDescription = "Error alert icon",
                    tint = DecorativeColor.yellow,
                )
            },
            positiveAction = {
            },
            showDialog = true,
            onTriggerPositiveOrNegativeAction = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DrovoxCenterDialogPreview3() {
    DrovoxTheme {
        DrovoxCenterDialog(
            title = "Check your internet connection",
            subtitle = "Check your internet connection and try again",
            positiveActionText = "Okay",
            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = DrovoxIcons.Warning,
                    contentDescription = "Error alert icon",
                    tint = DecorativeColor.yellow,
                )
            },
            positiveAction = {
            },
            showDialog = true,
            onTriggerPositiveOrNegativeAction = {},
            onDismissIconClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DrovoxCenterDialogPreview4() {
    DrovoxTheme {
        DrovoxCenterDialog(
            title = "Check your internet connection kjb.kdbn.j",
            subtitle = "Check your internet connection and try again",
            positiveActionText = "Okay",
            positiveAction = {
            },
            showDialog = true,
            onTriggerPositiveOrNegativeAction = {},
        )
    }
}