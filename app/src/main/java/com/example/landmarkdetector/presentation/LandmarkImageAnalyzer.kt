package com.example.landmarkdetector.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.landmarkdetector.domain.Classification
import com.example.landmarkdetector.domain.LandMarkClassifier

class LandmarkImageAnalyzer(
    private val classifier: LandMarkClassifier,
    private val onResult: (List<Classification>) -> Unit
):ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centreCrop(321, 321)
            val results = classifier.classify(bitmap, rotationDegrees)
            onResult(results)
        }
        frameSkipCounter++
        image.close()
    }
}
