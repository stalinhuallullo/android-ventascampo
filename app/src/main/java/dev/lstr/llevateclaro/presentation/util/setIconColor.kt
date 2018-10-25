package dev.lstr.llevateclaro.presentation.util

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable

fun Drawable.setIconColor(color: Int) {
    mutate()
    setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}