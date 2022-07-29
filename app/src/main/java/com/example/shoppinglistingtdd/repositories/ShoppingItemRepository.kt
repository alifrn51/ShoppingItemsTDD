package com.example.shoppinglistingtdd.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglistingtdd.commn.Resource
import com.example.shoppinglistingtdd.data.local.ShoppingItem
import com.example.shoppinglistingtdd.data.remote.responses.ImageResponse
import retrofit2.Response

interface ShoppingItemRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalShoppingItems(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}