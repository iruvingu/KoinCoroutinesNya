package com.example.koincoroutinesnya.model.repository

import com.example.koincoroutinesnya.model.UseCaseResult
import com.example.koincoroutinesnya.model.data.Cat

interface CatRepository {
    suspend fun getCatList(): UseCaseResult<List<Cat>>
}
