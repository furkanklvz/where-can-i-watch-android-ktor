package com.klavs.wherecaniwatch.data.datasource.wciwservice

import com.klavs.wherecaniwatch.data.entities.ShowData
import com.klavs.wherecaniwatch.utils.Resource

interface WCIWServiceDatasource {
    suspend fun getProducts(keywords: String): Resource<List<ShowData>>
}