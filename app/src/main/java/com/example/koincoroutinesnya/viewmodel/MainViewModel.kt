package com.example.koincoroutinesnya.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.koincoroutinesnya.model.UseCaseResult
import com.example.koincoroutinesnya.model.data.Cat
import com.example.koincoroutinesnya.model.repository.CatRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val catRepository: CatRepository): ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val catsList = MutableLiveData<List<Cat>>()
    val showError = SingleLiveEvent<String>()

    fun loadCats() {
        // show progressBar during the operation on the MAIN (default) thread
        showLoading.value = true

        // launch the coroutine
        launch {
            // Switching from MAIN to IO thread for API operation
            // Updating our data list with the new one from API
            val result = withContext(Dispatchers.IO) {
                catRepository.getCatList()
            }

            //Hide progressBar once the operation is done on the MAIN (default) thread
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> catsList.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

}