package com.picpay.desafio.android

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.repository.PrefsConfig
import com.picpay.desafio.android.retrofit.User
import com.picpay.desafio.android.utils.Constants
import junit.framework.Assert.assertEquals
import org.junit.Test

class PrefsConfigTest {
    private val sharedPrefs = mock<SharedPreferences>()
    private val context = mock<Context>()

    @Test
    fun getFromPrefs_listNotExists() {

        // given
        val expectedUsers = ArrayList<User>()

        // when
        whenever(context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPrefs)
        whenever(sharedPrefs.getString(Constants.LIST, "")).thenReturn("")
        val mPrefs = PrefsConfig(context)
        val users = mPrefs.getFromPrefs()

        // then
        assertEquals(users, expectedUsers)
    }

    @Test
    fun getFromPrefs_listExists() {

        // given
        val expectedUsers = listOf(User("img", "test", 1, "test"))
        val stringJsonArrayList = "[{\"id\":1,\"img\":\"img\",\"name\":\"test\",\"username\":\"test\"}]"

        // when
        whenever(sharedPrefs.getString(Constants.LIST, "")).thenReturn(stringJsonArrayList)
        whenever(context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPrefs)
        val mPrefs = PrefsConfig(context)
        val users = mPrefs.getFromPrefs()

        // then
        assertEquals(users, expectedUsers)
    }
}