package com.cgomezq.hackernews.common.formatter

import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun LocalDateTime.toTimesAgo(yesterdayText: String): String {
    val dateTime = this
    val format = DecimalFormat("#.#")
    val today = LocalDateTime.now()
    val yesterday = today.minusDays(1)
    val differenceInDays = dateTime.until(today, ChronoUnit.DAYS)
    return when {
        today.toLocalDate() == dateTime.toLocalDate() -> {
            val differenceInMinutes = dateTime.until(today, ChronoUnit.MINUTES)
            return if (differenceInMinutes > 60) {
                val hours = differenceInMinutes.toFloat() / 60
                "${format.format(hours)}h"
            } else {
                "${differenceInMinutes}m"
            }
        }

        yesterday.toLocalDate() == dateTime.toLocalDate() -> yesterdayText

        differenceInDays in 2..4 ->
            DateTimeFormatter.ofPattern("EEEE")
                .format(dateTime)

        else ->
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .format(dateTime)
    }
}