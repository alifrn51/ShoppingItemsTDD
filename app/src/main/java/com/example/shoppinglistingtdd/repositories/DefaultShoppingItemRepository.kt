package com.example.shoppinglistingtdd.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglistingtdd.commn.Resource
import com.example.shoppinglistingtdd.data.local.ShoppingDao
import com.example.shoppinglistingtdd.data.local.ShoppingItem
import com.example.shoppinglistingtdd.data.remote.PixabayApi
import com.example.shoppinglistingtdd.data.remote.responses.ImageResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingItemRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi
): ShoppingItemRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalShoppingItems(): LiveData<Float> {
        return shoppingDao.observeTotalShoppingItem()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {

            val response = pixabayApi.searchForImage(imageQuery)

            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("AN unknown error occoured" , null)
            }else{
                return Resource.error("An unknown error occured" , null)
            }

        }catch (e: Exception){
            return Resource.error("Couldn't reach the server. Check your internet connection" , null)
        }
    }
}