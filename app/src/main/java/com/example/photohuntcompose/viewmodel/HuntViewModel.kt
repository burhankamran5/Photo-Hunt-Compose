package com.example.photohuntcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photohuntcompose.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HuntViewModel : ViewModel() {

    val selectedLocation = MutableStateFlow<String?>(null)
    val currentItems = MutableStateFlow<List<String>>(emptyList())
    val currentItem = MutableStateFlow<String?>(null)
    val itemsLeft = MutableStateFlow(0)
    val score = MutableStateFlow(0)
    var currentIndex = 0
    var onFinished: (() -> Unit)? = null


    fun selectLocation(location:String){
        selectedLocation.value = location
        generateItems(location)
    }

fun setScore(reward: Int) {
    score.value += reward
}

    private fun generateItems(location: String) {
        viewModelScope.launch {
            try {
                val randomNumber = (1..10000).random()

                val prompt = when (location) {
                    "Home" -> "List 10 unique single-word items that are commonly found in a home. Just list the items separated by commas, without any introductory sentences. Example: Couch, Table, etc. Random number: $randomNumber"
                    "Outside" -> "List 10 unique single-word items that are commonly found outside. Just list the items separated by commas, without any introductory sentences. Example: Tree, Rock, etc. Random number: $randomNumber"
                    else -> "List 10 unique single-word items, separated by commas. Random number: $randomNumber"
                }

                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash",
                    apiKey = BuildConfig.API_KEY
                )

                val response = generativeModel.generateContent(prompt)
                val items = response.text
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotBlank() }
                    ?.map { it.replace(Regex("[^A-Za-z ]"), "").trim() }

                Log.d("HuntViewModel", "Generated items for $location: $items")
                if (items != null) {
                   currentItems.value = items
                    itemsLeft.value = items.size
                    currentIndex = 0
                    pickNextItem()
                } else {
                    Log.e("HuntViewModel", "Failed to generate items: No items generated")
                    currentItems.value = emptyList()
                    itemsLeft.value = 0
                }
            } catch (e: Exception) {
                Log.e("HuntViewModel", "Error generating items", e)
                // Handle the error appropriately
                currentItems.value = emptyList()
                itemsLeft.value = 0
            }
        }
    }

    fun pickNextItem() {
        viewModelScope.launch {
            if (currentIndex < currentItems.value.size) {
                currentItem.value = currentItems.value[currentIndex]
                itemsLeft.value = currentItems.value.size - currentIndex
                Log.d("HuntViewModel", "Picked item: ${currentItem.value}")
                currentIndex++
            } else {
                onFinished?.invoke()
                currentItem.value = "No more items"
                itemsLeft.value = 0
                Log.d("HuntViewModel", "No more items to pick")
            }
        }
    }


    fun reset() {
        selectedLocation.value = null
        currentItems.value = emptyList()
        currentItem.value = null
        itemsLeft.value = 0
        score.value = 0
        currentIndex = 0
    }

}