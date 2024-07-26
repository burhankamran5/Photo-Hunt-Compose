package com.example.photohuntcompose.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photohuntcompose.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PredictionViewModel : ViewModel() {

    val capturedImage = MutableStateFlow<Bitmap?>(null)
    val predictedName = MutableStateFlow<String?>(null)

    private var retryCount = 0
    private val maxRetries = 2

    fun setCapturedImage(bitmap: Bitmap?) {
        capturedImage.value = bitmap
    }

    fun incrementRetryCount() {
        retryCount++
    }
    fun resetRetryCount() {
        retryCount = 0
        predictedName.value = null
    }
    fun shouldRetry(): Boolean {
        return retryCount < maxRetries
    }
    fun predictImageName() {
        viewModelScope.launch {
            try {
                val bitmap = capturedImage.value
                if (bitmap != null) {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = BuildConfig.API_KEY
                    )

                    val inputContent = content {
                        image(bitmap)
                        text("Provide a single-word name for the item in this image.")
                    }

                    val response = generativeModel.generateContent(inputContent)
                    val predictName= response.text?.split(" ")?.firstOrNull()?.replace(Regex("[^A-Za-z]"), "") ?: "Prediction failed"

                    predictedName.value = predictName
                    Log.d("PredictionViewModel", "Predicted name: $predictedName")
                } else {
                    predictedName.value = "No image available"
                }
            } catch (e: Exception) {
                Log.e("PredictionViewModel", "Error predicting image name", e)
                predictedName.value = "Prediction failed"
            }
        }
    }


    fun resetPrediction() {
        predictedName.value = null
    }

}