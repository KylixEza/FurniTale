package com.raion.furnitale.core.data.source.remote

import com.raion.furnitale.core.data.source.remote.network.ApiResponse
import com.raion.furnitale.core.data.source.remote.network.DummyApiService
import com.raion.furnitale.core.data.source.remote.response.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: DummyApiService) {

    fun getLivingRoomList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val livingRoomArr = apiService.getLivingRoomList()
            emit(ApiResponse.Success(livingRoomArr))
        }

    fun getBedRoomList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val bedRoomArr = apiService.getBedRoomList()
            emit(ApiResponse.Success(bedRoomArr))
        }

    fun getKitchenList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val kitchenArr = apiService.getKitchenList()
            emit(ApiResponse.Success(kitchenArr))
        }

    fun getBathRoomList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val bathRoomArr = apiService.getBathroomList()
            emit(ApiResponse.Success(bathRoomArr))
        }

    fun getOutdoorList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val outdoorArr = apiService.getOutdoorList()
            emit(ApiResponse.Success(outdoorArr))
        }

    fun getAccessoriesList(): Flow<ApiResponse<List<ProductResponse>>> =
        flow {
            val accessoriesArr = apiService.getAccessoriesList()
            emit(ApiResponse.Success(accessoriesArr))
        }

    fun getDetailProduct(id: Int): Flow<ApiResponse<ProductResponse>> =
        flow {
            val productDetail = apiService.getProductDetail(id)
            emit(ApiResponse.Success(productDetail))
        }.flowOn(Dispatchers.IO)
}