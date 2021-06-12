package com.picpay.desafio.android.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.R
import com.picpay.desafio.android.repository.PrefsConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    private lateinit var mPrefs: PrefsConfig

    companion object {
        val receivedList: MutableLiveData<ArrayList<User>> = MutableLiveData()
    }

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

    fun searchData(context: Context, progressBar: ProgressBar) {
        service.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    val message = if (!isNetworkAvailbale(context)) {
                        context.getString(R.string.Interneterror)
                    } else {
                        context.getString(R.string.error)
                    }
                    mPrefs = PrefsConfig(context)
                    receivedList.postValue(mPrefs.getFromPrefs())
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    mPrefs = PrefsConfig(context)
                    progressBar.visibility = View.GONE

                    if (response.body() != null) {
                        receivedList.postValue(response.body() as ArrayList<User>)
                        mPrefs.saveIntoPrefs(response.body() as ArrayList<User>)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.no_results),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            })
    }

    fun isNetworkAvailbale(context: Context): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = conManager.activeNetworkInfo
        return internetInfo != null && internetInfo.isConnected
    }
}