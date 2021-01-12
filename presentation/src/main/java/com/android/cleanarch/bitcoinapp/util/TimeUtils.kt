package com.android.cleanarch.bitcoinapp.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    private val months = arrayOf(
        "",
        "Jan ",
        "Feb ",
        "Mar ",
        "Apr ",
        "May ",
        "Jun ",
        "Jul ",
        "Aug ",
        "Sep ",
        "Oct ",
        "Nov ",
        "Dec "
    )


    /**
     * @param dateLong the date as Long
     * @param formatPattern the format pattern, default is "yyyy-MM-dd"
     * @param locale the local, default is Locale.US
     */
    @JvmStatic
    fun formatDateFromLongAsString(
        dateLong: Long,
        formatPattern: String = DEFAULT_DATE_FORMAT,
        locale: Locale = Locale.US
    ): String {
        val sdf = SimpleDateFormat(formatPattern, locale)
        val date = Date(dateLong * 1000)
        return sdf.format(date)
    }

    @JvmStatic
    fun formatDateStringWithMonths(timeStamp: Long): String {
        val date = formatDateFromLongAsString(timeStamp).split("-")
        return "${months[Integer.parseInt(date[1])]}${date[2]}, ${date[0]}"

    }


}