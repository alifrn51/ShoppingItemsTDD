package com.example.shoppinglistingtdd.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglistingtdd.commn.Resource
import com.example.shoppinglistingtdd.data.local.ShoppingItem
import com.example.shoppinglistingtdd.data.remote.responses.ImageResponse

class FakeShoppingRepository: ShoppingItemRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observeAllShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observeTotalShoppingItems = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }


    private fun refreshLiveData(){
        observeAllShoppingItems.postValue(shoppingItems)
        observeTotalShoppingItems.postValue(getTotalShoppingItem())
    }

    private fun getTotalShoppingItem(): Float{
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observeAllShoppingItems
    }

    override fun observeTotalShoppingItems(): LiveData<Float> {
        return observeTotalShoppingItems
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error" , null)
        }else{
            Resource.success(ImageResponse(listOf() , 0 , 0))
        }
    }
}