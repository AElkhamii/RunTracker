package com.example.core.data.networking

import com.example.core.data.BuildConfig
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

/* Ktor is a networking library that we use in this project
 * Why Ktor?
 * Because ktor already comes with an build in plugin that makes it easy for us
 * to implement our authentication mechanism to handel all the token refresh, and stuff behind the scene
 * with a library like retrofit we need to implement these properties in our own*/
/* Ktor is a pure kotlin library, so if at some point want to migrate this code to KMP, yo won't need to change anything */


/**** Three utility method for each http method we are going to use in this app ****/
/* First: Get request */
suspend inline fun <reified Response:Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network>{
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}
/* Second : Delete request */
suspend inline fun <reified Response:Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network>{
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

/* Third: Post request */
suspend inline fun <reified Request,reified Response:Any> HttpClient.poste(
    route: String,
    body: Request
): Result<Response, DataError.Network>{
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}


/**** Make a safe call to use it in the upper three functions ****/
/* This function used to make a safe call */
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network>{
    val response =  try {
        // Actual request
        execute()
    } catch (e: UnresolvedAddressException){ // If you have no connection to the internet or try to connect to non-existed URL exception
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    }
    catch (e: SerializationException){
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    /* We need to take care while we catch a general exception in a suspend function
     * Because this suspend function will through a cancellation exception and this cancellation
     * will be used to propagate up to all of these single coroutine scopes in which your actual coroutine is running
     * so this cancellation exception will be caught by this general exception
     * to solve this only catch certain exception such as the upper ones
     * or make a check if the exception is a cancellation exception */
    }catch (e: Exception){
        /* This is how to solve the upper problem */
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    /* Return the response and parsing it to our result by using the bellow function that we created to parse the response */
    return responseToDataResult(response)
}

/* It is a function that automatically parc the Http Status code that return from our request to corresponding dara error */
suspend inline fun <reified T> responseToDataResult(response: HttpResponse): Result<T, DataError.Network>{
    return when(response.status.value){
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

/* This function used to get the rout we want to request to.
 * it also construct that whole route with protocol with base URL */
fun constructRoute(route: String): String{
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> BuildConfig.BASE_URL + "/$route"
    }
}



