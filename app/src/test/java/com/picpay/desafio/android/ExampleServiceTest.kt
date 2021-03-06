package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.repository.PrefsConfig
import com.picpay.desafio.android.retrofit.PicPayService
import com.picpay.desafio.android.retrofit.User
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ExampleServiceTest {

    private val api = mock<PicPayService>()
    private val service = ExampleService(api)

    @Test
    fun exampleTest() {
        // given
        val call = mock<Call<List<User>>>()
        val expectedUsers = emptyList<User>()

        // when

        whenever(call.execute()).thenReturn(Response.success(null))
        whenever(api.getUsers()).thenReturn(call)

        val users = service.example()

        // then
        assertEquals(users, expectedUsers)
    }

}