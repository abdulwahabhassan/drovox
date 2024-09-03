package com.drovox.core.data.handler

import com.drovox.core.network.model.ApiError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.nio.charset.Charset

fun handleHttpException(e: HttpException, json: Json): ApiError? {
    return try {
        e.response()?.errorBody()?.source()?.readString(Charset.defaultCharset())?.let {
            json.decodeFromString<ApiError>(it)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}
