package com.klavs.wherecaniwatch.data.repository.wciwservice

import com.klavs.wherecaniwatch.data.entities.WciwResponse
import com.klavs.wherecaniwatch.data.entities.WciwResponseItem
import com.klavs.wherecaniwatch.utils.Resource

interface WCIWServiceRepository {
    suspend fun getProducts(keywords: String): Resource<List<WciwResponseItem>>
}