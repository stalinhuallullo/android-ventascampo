package dev.lstr.llevateclaro.data.model

import android.content.Context
import android.graphics.Bitmap
import dev.lstr.llevateclaro.data.datasource.pref.CurrentPublicidad

class PublicidadItem{
    public var publicidadE : PublicidadE? =null
    public var image: Bitmap? = null

    constructor(id_modulo: String, image: Bitmap,context: Context) {

        this.publicidadE = CurrentPublicidad.getInstance(context!!).getPublicidad(id_modulo)
        this.image = image
    }

    constructor(publicidadE: PublicidadE, image: Bitmap)
    {
        this.publicidadE = publicidadE
        this.image = image
    }
}