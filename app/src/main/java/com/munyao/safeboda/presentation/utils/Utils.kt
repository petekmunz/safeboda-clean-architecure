package com.munyao.safeboda.presentation.utils

import android.content.Context
import android.widget.Toast

object Utils {

    fun Context.showToast(message: String): Toast {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
        return toast
    }

    fun Context.showLongToast(message: String): Toast {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
        return toast
    }
}