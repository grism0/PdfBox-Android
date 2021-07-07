package dev.grismo.uiextensions.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.github.chrisbanes.photoview.PhotoView
import com.tom_roush.pdfbox.pdmodel.PDDocument
import dev.grismo.uiextensions.link.Link
import dev.grismo.uiextensions.link.LinkHandler
import dev.grismo.uiextensions.link.LinkHandler.getPossibleHit
import dev.grismo.uiextensions.link.LinkListener

@SuppressLint("ClickableViewAccessibility")
class PDFView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    PhotoView(context, attrs, defStyle) {

    var linkListener: LinkListener? = null
    var links: List<Link>? = null

    private val onTouchListener = OnTouchListener { v, event ->
        event?.let { ev ->
            linkListener?.let { listener ->
                links?.getPossibleHit(ev.x, ev.y)?.let { hit ->
                    listener.onLinkClicked(hit)
                    true
                }
            }
        }
        false
    }

    fun showPage(document: PDDocument) {
        setImageBitmap(UiHelper.getBitmap(context, document))
        links = LinkHandler.getLinks(document.getPage(0))
        setOnTouchListener(onTouchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }


}