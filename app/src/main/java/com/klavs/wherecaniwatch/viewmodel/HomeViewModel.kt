package com.klavs.wherecaniwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klavs.wherecaniwatch.data.entities.ShowData
import com.klavs.wherecaniwatch.data.repository.wciwservice.WCIWServiceRepository
import com.klavs.wherecaniwatch.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel (private val trendyolRepo: WCIWServiceRepository): ViewModel() {
    private val _productsResult = MutableStateFlow<Resource<List<ShowData>>>(Resource.Idle)
    val productsResult = _productsResult.asStateFlow()

    fun getMovies(keywords: String){
        _productsResult.value = Resource.Loading
        viewModelScope.launch {
            _productsResult.value = trendyolRepo.getProducts(keywords)
        }
    }

    fun resetResult() {
        _productsResult.value = Resource.Idle
    }
}