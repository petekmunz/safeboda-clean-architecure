package com.safeboda.data.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val USER_FRIENDLY_FORMAT = "MMM dd, yyyy"

    fun iso8601ToSimpleUserFormat(stringDate: String): String? {
        return try {
            val isoSdf = SimpleDateFormat(FORMAT_ISO_8601, Locale.getDefault())
            val date = isoSdf.parse(stringDate)

            val userFriendlySdf = SimpleDateFormat(USER_FRIENDLY_FORMAT, Locale.getDefault())
            if (date != null) {
                userFriendlySdf.format(date)
            } else {
                null
            }
        } catch (e: ParseException) {
            null
        }
    }
}