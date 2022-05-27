package com.safeboda.data

import com.safeboda.data.utils.DateUtil
import org.junit.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

class DateUtilUnitTest {
    @Test
    fun `iso_time_to_user_friendly_returns_correct_format`() {
        val returnedValue = DateUtil.iso8601ToSimpleUserFormat("2008-01-14T04:33:35Z")
        assertThat(returnedValue, not(null))
    }

    @Test
    fun `iso_time_to_user_friendly_returns_null_when_exception`() {
        val returnedValue = DateUtil.iso8601ToSimpleUserFormat("Thu, 26 May 22 18:57:31 +0000")
        assertThat(returnedValue, `is`(null))
    }
}