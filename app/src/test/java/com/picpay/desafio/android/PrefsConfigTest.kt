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

        whenever(sharedPrefs.getString(Constants.LIST, "")).thenReturn("")
        whenever(context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPrefs)

        val mPrefs = PrefsConfig(context)

        // when
        val users = mPrefs.getFromPrefs()

        // then
        assertEquals(users, expectedUsers)
    }

    @Test
    fun getFromPrefs_listExists() {

        // given
        val expectedUsers = listOf(User("img", "test", 1, "test"))

        val json = "[{\"id\":1,\"img\":\"img\",\"name\":\"test\",\"username\":\"test\"}]"
        whenever(sharedPrefs.getString(Constants.LIST, "")).thenReturn(json)
        whenever(context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPrefs)

        val mPrefs = PrefsConfig(context)

        // when
        val users = mPrefs.getFromPrefs()

        // then
        assertEquals(expectedUsers, users)
    }
}