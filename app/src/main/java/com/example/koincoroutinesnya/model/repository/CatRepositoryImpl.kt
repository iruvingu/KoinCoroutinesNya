package com.example.koincoroutinesnya.model.repository

import com.example.koincoroutinesnya.model.UseCaseResult
import com.example.koincoroutinesnya.model.data.Cat
import com.example.koincoroutinesnya.network.CatApi

const val NUMBER_OF_CATS = 30

class CatRepositoryImpl(private val catApi: CatApi) : CatRepository {
    override suspend fun getCatList(): UseCaseResult<List<Cat>> {
        /*
         We try to return a list of cats from the API
         Await the result from web service and then return it, catching any error from API
         */
        return try {
            val result = catApi.getCats(limit = NUMBER_OF_CATS).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}