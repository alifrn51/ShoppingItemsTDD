package com.example.shoppinglistingtdd.di

import android.content.Context
import androidx.room.Room
import com.example.shoppinglistingtdd.commn.Constants.BASE_URL
import com.example.shoppinglistingtdd.commn.Constants.DATABASE_NAME
import com.example.shoppinglistingtdd.data.local.ShoppingItemDatabase
import com.example.shoppinglistingtdd.data.remote.PixabayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object AppModule {

    @Singleton
    @Provides
    fun providesShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java , DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()


    @Singleton
    @Provides
    fun providesShoppingApi(): PixabayApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }
}