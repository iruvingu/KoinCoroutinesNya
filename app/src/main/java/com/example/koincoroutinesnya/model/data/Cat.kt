package com.example.koincoroutinesnya.model.data

import com.google.gson.annotations.SerializedName

data class Cat(val id: String,
               @SerializedName("url") val imageUrl: String)