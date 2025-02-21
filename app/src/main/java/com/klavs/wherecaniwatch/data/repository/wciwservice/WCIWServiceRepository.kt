package com.klavs.wherecaniwatch.data.repository.wciwservice

import com.klavs.wherecaniwatch.data.entities.ShowData
import com.klavs.wherecaniwatch.utils.Resource

interface WCIWServiceRepository {
    suspend fun getProducts(keywords: String): Resource<List<ShowData>>
}