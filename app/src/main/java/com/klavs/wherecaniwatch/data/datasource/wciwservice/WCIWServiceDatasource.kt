package com.klavs.wherecaniwatch.data.datasource.wciwservice

import com.klavs.wherecaniwatch.data.entities.WciwResponse
import com.klavs.wherecaniwatch.data.entities.WciwResponseItem
import com.klavs.wherecaniwatch.utils.Resource

interface WCIWServiceDatasource {
    suspend fun getProducts(keywords: String): Resource<List<WciwResponseItem>>
}