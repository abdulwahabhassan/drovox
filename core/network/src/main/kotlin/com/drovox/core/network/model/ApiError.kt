package com.drovox.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val message: String? = null,
)
