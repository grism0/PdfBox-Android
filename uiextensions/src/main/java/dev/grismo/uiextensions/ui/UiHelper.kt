package dev.grismo.uiextensions.ui

import android.content.Context
import android.graphics.Bitmap
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.rendering.ImageType
import com.tom_roush.pdfbox.rendering.PDFRenderer
import java.io.File
import java.io.FileOutputStream

object UiHelper {

    fun getBitmap(context: Context, document: PDDocument): Bitmap {
        // Create a renderer for the document
        val renderer = PDFRenderer(document)
        // Render the image to an RGB Bitmap
        val result = renderer.renderImage(0, 1f, ImageType.RGB)

        // Save the render result to an image
        val path: String = context.applicationContext.cacheDir.absolutePath + "/render-${System.currentTimeMillis()}.jpg"
        val renderFile = File(path)
        val fileOut = FileOutputStream(renderFile)
        result.compress(Bitmap.CompressFormat.WEBP, 100, fileOut)
        fileOut.close()
        return result
    }

}