package com.valaz.ufrmim_projetdevmob.network

import android.content.res.Resources.NotFoundException
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.NullBody
import io.ktor.http.contentType
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object KtorClient {

    private const val NETWORK_TIME_OUT = 6_000L

    val httpClientAndroid = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_TIME_OUT
            connectTimeoutMillis = NETWORK_TIME_OUT
            socketTimeoutMillis = NETWORK_TIME_OUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    suspend inline fun <reified Out> httpCall(
        httpMethod: HttpMethod,
        endpoint: String,
    ): Out {
        return httpCall<NullBody, Out>(httpMethod, endpoint)
    }

    suspend inline fun <reified In, reified Out> httpCall(
        httpMethod: HttpMethod,
        endpoint: String,
        requestBody: In? = null,
    ): Out {
        //val baseUrl = "http://192.168.60.184:3000/"
//        val baseUrl = "http://10.11.28.11:3000/"
        val baseUrl = "http://192.168.1.96:3000/"
        val url = baseUrl + endpoint

        val httpRequestBuilderWithoutBody: HttpRequestBuilder.() -> Unit = {}
        val httpRequestBuilderWithBody: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }

        val responseBody: Out = performHttpMethod(
            httpMethod,
            url,
            httpRequestBuilderWithoutBody,
            httpRequestBuilderWithBody,
            requestBody
        )

        delay(1000)

        return responseBody
    }

    suspend inline fun <reified In, reified Out> performHttpMethod(
        httpMethod: HttpMethod,
        url: String,
        httpRequestBuilderWithoutBody: HttpRequestBuilder.() -> Unit,
        httpRequestBuilderWithBody: HttpRequestBuilder.() -> Unit,
        requestBody: In? = null,
    ): Out {
        var response: HttpResponse? = null

        try {
            response = when (httpMethod) {
                HttpMethod.Get -> {
                    httpClientAndroid.get(url, httpRequestBuilderWithoutBody)
                }

                HttpMethod.Post -> {
                    httpClientAndroid.post(url, httpRequestBuilderWithBody)
                }

                HttpMethod.Put -> {
                    httpClientAndroid.put(url, httpRequestBuilderWithBody)
                }

                HttpMethod.Delete -> {
                    httpClientAndroid.delete(url, httpRequestBuilderWithoutBody)
                }

                else -> {
                    throw UnsupportedOperationException("HTTP Method not supported")
                }
            }
            return response.body()
        } catch (ex: ResponseException) {
            // this is for status code issues, so network is fine
            Log.w("HttpClient", "Network call in error: ", ex)
            throw ex
        } catch (ex: SerializationException) {
            // network is fine and status code 200, but data is malformed
            val message = StringBuilder("Json SerializationException - \n")
                .append("RequestBody: ")
                .append("${requestBody?.let { it::class }}\n")
                .append("Response: ")
                .append(response?.let { Out::class })
                .append("\nCause: ")
                .append(ex.cause)
            Log.w(
                "HttpClient",
                message.toString(),
                ex,
            )
            throw ex
        } catch (ex: JsonConvertException) {
            // network is fine and status code 200, but data is malformed
            val message = StringBuilder("JsonConvertException - \n")
                .append("RequestBody: ")
                .append("${requestBody?.let { it::class }}\n")
                .append("Response: ")
                .append(response?.let { Out::class })
                .append("\nCause: ")
                .append(ex.cause)
            Log.w(
                "HttpClient",
                message.toString(),
                ex,
            )
            throw ex
        } catch (ex: NoTransformationFoundException) {
            // network is fine and status code 200, but data is malformed
            val message = StringBuilder("Json NoTransformationFoundException - \n")
                .append("RequestBody: ")
                .append("${requestBody?.let { it::class }}\n")
                .append("Response: ")
                .append(response?.let { Out::class })
                .append("\nCause: ")
                .append(ex.cause)
            Log.w(
                "HttpClient",
                message.toString(),
                ex,
            )
            throw ex
        } catch (t: Throwable) {
            t.message?.let {
                //Network error, server unreachable
                Log.e("HttpClient", "Catch network error : ", t)
                throw NotFoundException(t.message)
            } ?: run {
                Log.e("HttpClient", "Catch UNKNOWN network error : ", t)
                throw t
            }
        }
    }
}