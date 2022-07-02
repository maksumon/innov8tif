package com.maksumon.innov8tif.extensions
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView

fun SearchView.closeFocus(menuItem: MenuItem) {
    this.isIconified = true
    this.clearFocus()
    menuItem.collapseActionView()
}

fun SearchView.setCloseButtonAction(menuItem: MenuItem) {
    val closeBtnId = this.context.resources
        .getIdentifier("android:id/search_close_btn", null, null)
    val closeBtn = this.findViewById<ImageView>(closeBtnId)
    closeBtn?.setOnClickListener {
        this.closeFocus(menuItem)
    }
}