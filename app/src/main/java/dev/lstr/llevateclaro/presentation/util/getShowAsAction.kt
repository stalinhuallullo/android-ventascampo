package dev.lstr.llevateclaro.presentation.util

import android.view.MenuItem

fun MenuItem.getShowAsAction(): Int {
    var f = this.javaClass.getDeclaredField("mShowAsAction")
    f.isAccessible = true
    return f.getInt(this)
}