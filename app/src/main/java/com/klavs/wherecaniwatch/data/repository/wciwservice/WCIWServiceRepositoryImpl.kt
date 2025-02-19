package com.klavs.wherecaniwatch.data.repository.wciwservice

import com.klavs.wherecaniwatch.data.datasource.wciwservice.WCIWServiceDatasource
import com.klavs.wherecaniwatch.data.entities.WciwResponse
import com.klavs.wherecaniwatch.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WCIWServiceRepositoryImpl(private val ds: WCIWServiceDatasource) : WCIWServiceRepository {
    override suspend fun getProducts(keywords: String) =
        withContext(Dispatchers.IO) { ds.getProducts(keywords) }
}