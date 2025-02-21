package com.klavs.wherecaniwatch.data.datasource.wciwservice

import android.util.Log
import com.klavs.wherecaniwatch.data.entities.ShowData
import com.klavs.wherecaniwatch.ktor.KtorService
import com.klavs.wherecaniwatch.utils.Resource
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class WCIWServiceDatasourceImpl(private val service: KtorService) : WCIWServiceDatasource {
    override suspend fun getProducts(keywords: String): Resource<List<ShowData>> {
        return try {
            val response = service.getProducts(keywords)
            if (response.status.isSuccess()) {
                runCatching {
                    val movies = response.body<List<ShowData>>()
                    Resource.Success(movies)
                }.getOrElse {
                    Log.e("Datasource", "parsing error: " + it.message)
                    Resource.Error(Exception("parsing error: " + it.message))
                }
            } else {
                val errorMessage = runCatching { response.body<String>() }.getOrElse { response.status.description }
                Resource.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("Datasource", "getProducts: ", e)
            Resource.Error(e)
        }
    }
}