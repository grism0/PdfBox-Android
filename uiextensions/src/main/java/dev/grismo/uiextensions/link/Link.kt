package dev.grismo.uiextensions.link

import android.graphics.RectF
import android.net.Uri
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDActionURI

data class Link(val link: PDActionURI, val text: String, val area: RectF) {

    override fun toString(): String {
        return "Link{link:${link.uri};rectF:$area;text:$text}"
    }

}