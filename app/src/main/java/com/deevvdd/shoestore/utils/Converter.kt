package com.deevvdd.shoestore.utils

import androidx.databinding.InverseMethod
import timber.log.Timber

object Converter {
    @InverseMethod("stringToDouble")
    @JvmStatic
    fun doubleToString(value: Double?): String {
        Timber.d("Double $value")
        return value?.toString() ?: ""
    }

    @JvmStatic
    fun stringToDouble(value: String): Double? {
        return if (value.isNotEmpty()) {
            value.toDouble()
        } else {
            null
        }
    }
}