package com.drovox.core.data.handler

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import com.drovox.core.model.sealed.Result
import com.drovox.core.network.connectivity.NetworkMonitor
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> handleNetworkRequest(
    dispatcher: CoroutineDispatcher,
    networkMonitor: NetworkMonitor,
    json: Json,
    networkRequest: suspend () -> T,
): Result<T> = withContext(dispatcher) {
    if (networkMonitor.isOnline.first()::not.invoke()) {
        Result.Error("Check your internet connection!")
    } else {
        try {
            Result.Success(networkRequest.invoke())
        } catch (e: HttpException) {
            val response = handleHttpException(e, json)
            Result.Error(
                message = response?.message ?: "Request could not be completed",
            )
        } catch (e: ConnectException) {
            Result.Error(
                message = "Connection issue! Check that mobile data is on and that you have internet.",
            )
        }
        catch (e: UnknownHostException) {
            Result.Error(
                message = "Connection issue! Check that you have internet.",
            )
        }
        catch (e: SocketTimeoutException){
            Result.Error(message = e.localizedMessage ?: "Request timed out!")
        }
        catch (e: Exception) {
            Result.Error(message = e.localizedMessage ?: "Request could not be completed")
        }
    }
}

