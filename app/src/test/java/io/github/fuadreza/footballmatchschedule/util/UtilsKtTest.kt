package io.github.fuadreza.footballmatchschedule.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class UtilsKtTest {

    @Test
    fun testToTanggal() {
        val tgl= "2018-10-31"
        assertEquals("31-10-2018", toTanggal(tgl))
    }

    @Test
    fun testDateFormat(){
        val tgl= "2018-10-31 20:00:00"
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val result = formatter.parse(tgl)

        print(result)

    }
}