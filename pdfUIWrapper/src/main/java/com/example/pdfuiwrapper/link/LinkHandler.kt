package com.example.pdfuiwrapper.link

import android.graphics.Rect
import android.graphics.RectF
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDAction
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDActionURI
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotation
import com.tom_roush.pdfbox.text.PDFTextStripperByArea

object LinkHandler {

    @JvmStatic
    fun getLinks(pdPage: PDPage): List<Link> {
        val stripper = PDFTextStripperByArea()
        pdPage.annotations.forEach { annot ->
            annot.getActionURI()?.let {
                val x = annot.rectangle.lowerLeftX
                var y = annot.rectangle.upperRightY
                val width = annot.rectangle.width
                val height = annot.rectangle.height
                if (pdPage.rotation == 0) {
                    y = pdPage.mediaBox.height - y
                }
                stripper.addRegion(
                    "${pdPage.annotations.indexOf(annot)}",
                    RectF(x, y, width, height)
                )
            }
        }
        stripper.extractRegions(pdPage)
        val result = mutableListOf<Link>()
        pdPage.annotations.forEach { annot ->
            annot.getActionURI()?.let {
                val regionName = "${pdPage.annotations.indexOf(annot)}"
                stripper.getRecFForRegion(regionName)?.let { area ->
                    result.add(Link(it, stripper.getTextForRegion(regionName), area))
                }
            }
        }
        return result
    }

    @JvmStatic
    fun PDAnnotation.getActionURI(): PDActionURI? {
        val method = javaClass.getDeclaredMethod("getAction")
        if (method.returnType == PDAction::class.java) {
            return method.invoke(this) as? PDActionURI
        }
        return null
    }

}