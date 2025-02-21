package com.klavs.wherecaniwatch.ktor

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.klavs.wherecaniwatch.R
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import java.net.URLEncoder
import java.util.Locale

class KtorService (private val client: HttpClient, private val context: Context) {
    private val hostUrl = "https://streaming-availability.p.rapidapi.com" +
            "/shows/search/title?country=${Locale.getDefault().country}&title=keyword&series_granularity=show&&output_language=${Locale.getDefault().language}"
    suspend fun getProducts(keyword: String): HttpResponse {
        val encodedQuery = URLEncoder.encode(keyword.trim(), "UTF-8")
        val response = client.get(hostUrl.replace("keyword", encodedQuery)) {
            header("x-rapidapi-key", context.getString(R.string.rapid_api_key))
        }
        //Log.d("KtorService", "getProducts (${hostUrl + searchQuery}): ${response.bodyAsText()}")
        //logJsonResponse("KtorService", response.bodyAsText())
        return response
    }
}

fun logLongMessage(tag: String, message: String, chunkSize: Int = 4000) {
    var start = 0
    while (start < message.length) {
        val end = minOf(start + chunkSize, message.length)
        Log.d(tag, message.substring(start, end))
        start = end
    }
}

fun logJsonResponse(tag: String, json: String) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val formattedJson = gson.toJson(gson.fromJson(json, Any::class.java))
    logLongMessage(tag, formattedJson)
}