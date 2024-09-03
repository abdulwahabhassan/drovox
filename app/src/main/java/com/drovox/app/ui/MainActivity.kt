package com.drovox.app.ui

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drovox.R
import com.drovox.app.broadcastreceiver.NotificationReceiver
import com.drovox.core.design.component.DrovoxCenterDialog
import com.drovox.core.design.icon.DrovoxIcons
import com.drovox.core.design.theme.DecorativeColor
import com.drovox.core.design.theme.DrovoxTheme
import com.drovox.feature.map.utiil.FusedLocationManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Calendar


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionsToRequest = mutableListOf<String>()
    private lateinit var fusedLocationManager: FusedLocationManager
    private val viewModel: MainViewModel by viewModels()

    private val requestMultiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationsGranted = permissions[Manifest.permission.POST_NOTIFICATIONS] ?: false
            if (notificationsGranted) {
                scheduleDailyNotification()
            } else {
                viewModel.sendEvent(MainScreenEvent.OnShowAlertDialog("Permission to deliver notifications denied!"))
            }
        } else {
            scheduleDailyNotification()
        }
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        if (fineLocationGranted) {
            initiateLocationUpdates()
        } else {
            viewModel.sendEvent(MainScreenEvent.OnShowAlertDialog("Permission to access location denied!"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            DrovoxTheme {
                val screenState by viewModel.uiState.collectAsStateWithLifecycle()
                DrovoxApp(
                    appState = rememberDrovoxAppState(),
                    onExitApp = { finish() }
                )
                DrovoxCenterDialog(
                    title = "Alert",
                    icon = {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = DrovoxIcons.Warning,
                            contentDescription = "Alert icon",
                            tint = DecorativeColor.yellow,
                        )
                    },
                    subtitle = screenState.alertMessage,
                    showDialog = screenState.showAlertDialog,
                    positiveActionText = "Okay",
                    onTriggerPositiveOrNegativeAction = {
                        viewModel.sendEvent(MainScreenEvent.OnDismissAlertDialog)
                    }
                )
                DrovoxCenterDialog(
                    title = "Grant Permission",
                    icon = {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = DrovoxIcons.Warning,
                            contentDescription = "Alert icon",
                            tint = DecorativeColor.yellow,
                        )
                    },
                    subtitle = screenState.permissionRationale,
                    showDialog = screenState.showPermissionRationale,
                    positiveActionText = "Grant",
                    positiveAction = {
                        requestMultiplePermissionsLauncher.launch(permissionsToRequest.toTypedArray())
                    },
                    onTriggerPositiveOrNegativeAction = {
                        viewModel.sendEvent(MainScreenEvent.OnDismissPermissionRationaleDialog)
                    }
                )
            }
        }
        initFusedLocationManager()
        createNotificationChannel()
        checkAndRequestPermissions()
    }

    private fun createNotificationChannel() {
        val channelId = getString(R.string.daily_notification_channel_id)
        val channelName = getString(R.string.daily_notifications)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = getString(R.string._9am_daily_notification_channel_description)
        }

        val notificationManager: NotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initFusedLocationManager() {
        fusedLocationManager = FusedLocationManager(this)
    }

    private fun scheduleDailyNotification() {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun initiateLocationUpdates() {
        fusedLocationManager.startLocationUpdates(
            onLocationUpdates = { longitude: Double, latitude: Double ->
                viewModel.sendEvent(
                    MainScreenEvent.UpdateDeviceLocation(
                        longitude = longitude,
                        latitude = latitude
                    )
                )
            },
        )
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissionsToRequest.isNotEmpty()) {
            if (permissionsToRequest.map { shouldShowRequestPermissionRationale(it) }.any { it }) {
                viewModel.sendEvent(MainScreenEvent.OnShowPermissionRationaleDialog)
            } else {
                requestMultiplePermissionsLauncher.launch(permissionsToRequest.toTypedArray())
            }
        } else {
            initiateLocationUpdates()
            scheduleDailyNotification()
        }
    }

    override fun onDestroy() {
        fusedLocationManager.stopLocationUpdates()
        super.onDestroy()
    }
}