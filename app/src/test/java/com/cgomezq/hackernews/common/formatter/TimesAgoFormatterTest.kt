package com.cgomezq.hackernews.common.formatter

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimesAgoFormatterTest {

    @Test
    fun toTimesAgo_givenTodayDate_shouldReturnTextHour() {
        val date = LocalDateTime.now().minusHours(4)

        val result = date.toTimesAgo("")
        assertEquals("4h", result)
    }

    @Test
    fun toTimesAgo_givenTodayDateMinus30minutes_shouldReturnTextMinutes() {
        val date = LocalDateTime.now().minusMinutes(30)

        val result = date.toTimesAgo("")
        assertEquals("30m", result)
    }

    @Test
    fun toTimesAgo_givenYesterdayDate_shouldReturnYesterdayText() {
        val date = LocalDateTime.now().minusDays(1)
        val yesterdayText = "yesterday"

        val result = date.toTimesAgo(yesterdayText)
        assertEquals(yesterdayText, result)
    }

    @Test
    fun toTimesAgo_givenTwoDaysAgo_shouldReturnDayOfWeek() {
        val date = LocalDateTime.now().minusDays(2)
        val expectedValue = DateTimeFormatter.ofPattern("EEEE").format(date)

        val result = date.toTimesAgo("")
        assertEquals(expectedValue, result)
    }

    @Test
    fun toTimesAgo_givenFiveDaysAgo_shouldReturnFullDate() {
        val date = LocalDateTime.now().minusDays(5)
        val expectedValue = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)

        val result = date.toTimesAgo("")
        assertEquals(expectedValue, result)
    }
}