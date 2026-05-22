package com.marco.pfm.ui.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MoneyFormattersTest {
    @Test
    fun `parse amount into minor units`() {
        assertEquals(1_234L, parseMinorAmount("12.34"))
        assertEquals(1_200L, parseMinorAmount("12"))
        assertEquals(1_250L, parseMinorAmount("12,50"))
    }

    @Test
    fun `reject invalid amount text`() {
        assertNull(parseMinorAmount(""))
        assertNull(parseMinorAmount("12.345"))
        assertNull(parseMinorAmount("abc"))
    }

    @Test
    fun `format minor amount for display and input`() {
        assertEquals("12.34", formatMinorAmount(1_234))
        assertEquals("-12.34", formatMinorAmount(-1_234))
        assertEquals("12", formatInputMinorAmount(1_200))
    }
}
