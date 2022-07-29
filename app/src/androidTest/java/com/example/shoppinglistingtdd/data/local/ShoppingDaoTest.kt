package com.example.shoppinglistingtdd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoppinglistingtdd.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup(){

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext() ,
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()

    }

    @After
    fun teardown(){
        database.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun insertShoppingItem() = runBlocking {
        val shoppingItem = ShoppingItem("item" , 1 , 1f , "url" , id = 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlocking {
        val shoppingItem = ShoppingItem("item" , 1 , 1f , "url" , id = 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }


    @Test
    fun observeAllShoppingItems() = runBlocking {

        val shoppingItem1 = ShoppingItem("item" , 1 , 8f , "url" , id = 1)
        val shoppingItem2 = ShoppingItem("item" , 3 , 1.8f , "url" , id = 2)
        val shoppingItem3 = ShoppingItem("item" , 4 , 0.5f , "url" , id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).isNotEmpty()
    }

    @Test
    fun observeTotalShoppingItems() = runBlocking {
        val shoppingItem1 = ShoppingItem("item" , 1 , 8f , "url" , id = 1)
        val shoppingItem2 = ShoppingItem("item" , 3 , 1.8f , "url" , id = 2)
        val shoppingItem3 = ShoppingItem("item" , 4 , 0.5f , "url" , id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalShoppingItem = dao.observeTotalShoppingItem().getOrAwaitValue()

        assertThat(totalShoppingItem).isEqualTo(1 * 8f + 3 * 1.8f + 4 * 0.5f)

    }

}