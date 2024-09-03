package com.drovox.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<D>(
    @SerialName("result_code")
    val resultCode: Long? = null,
    val message: String? = null,
    val data: D? = null,
)
