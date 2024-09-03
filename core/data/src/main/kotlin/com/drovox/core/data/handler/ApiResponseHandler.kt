package com.drovox.core.data.handler

import com.drovox.core.network.model.ApiResponse
import com.drovox.core.model.sealed.Result

fun <D> handleDataApiResponse(
    requestResult: Result<ApiResponse<D>>,
): Result<D> = when (requestResult) {
    is Result.Success -> {
        if (requestResult.data.resultCode == 200L && requestResult.data.data != null) {
            Result.Success(data = requestResult.data.data!!)
        } else {
            Result.Error(
                message = requestResult.data.message ?: "Error"
            )
        }
    }

    is Result.Error -> {
        Result.Error(requestResult.message)
    }
}