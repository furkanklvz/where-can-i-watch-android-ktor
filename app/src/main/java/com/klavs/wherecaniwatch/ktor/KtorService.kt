package com.klavs.wherecaniwatch.ktor

import android.content.Context
import android.util.Log
import com.klavs.wherecaniwatch.R
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

class KtorService (private val client: HttpClient, private val context: Context) {
    private val hostUrl = "https://where-can-i-watch1.p.rapidapi.com/search/us/"
    suspend fun getProducts(keywords: String): HttpResponse {
        val movieName = keywords.trim().replace(" ", "%20")
        val response = client.get(hostUrl + movieName) {
            header("x-rapidapi-key", context.getString(R.string.rapid_api_key))
        }
        Log.d("KtorService", "getProducts (${hostUrl + movieName}): ${response.bodyAsText()}")
        return response
    }
}