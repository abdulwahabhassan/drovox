package com.drovox.app.ui

import com.drovox.core.data.datastore.UserPreferencesDataStore
import com.drovox.core.domain.usecase.devicelocation.UpdateDeviceLocationUseCase
import com.drovox.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val updateLocationUseCase: UpdateDeviceLocationUseCase,
) :
    BaseViewModel<MainScreenEvent, MainScreenState, MainScreenOneShotState>(
        MainScreenState()
    ) {
    override suspend fun handleUiEvents(event: MainScreenEvent) {
        when (event) {
            MainScreenEvent.OnDismissAlertDialog -> {
                setUiState { copy(showAlertDialog = false, alertMessage = "") }
            }

            is MainScreenEvent.OnShowAlertDialog -> {
                setUiState { copy(showAlertDialog = true, alertMessage = event.message) }
            }

            MainScreenEvent.OnShowPermissionRationaleDialog -> {
                setUiState {
                    copy(
                        showPermissionRationale = true,
                        permissionRationale = "Permission to deliver notifications and access location is required for app to function properly!. Please grant access."
                    )
                }
            }

            MainScreenEvent.OnDismissPermissionRationaleDialog -> {
                setUiState {
                    copy(
                        showPermissionRationale = false,
                        permissionRationale = ""
                    )
                }
            }

            is MainScreenEvent.UpdateDeviceLocation -> {
                updateLocationUseCase(longitude = event.longitude, latitude = event.latitude)
            }
        }
    }
}