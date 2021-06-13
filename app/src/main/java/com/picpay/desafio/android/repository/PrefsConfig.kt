package com.picpay.desafio.android.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.retrofit.User
import com.picpay.desafio.android.utils.Constants
import java.lang.reflect.Type

class PrefsConfig(context: Context) {
    val mContext = context
    val sharedPref: SharedPreferences =
        mContext.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    var gson = Gson()

    fun saveIntoPrefs(list: ArrayList<User>) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val jsonString = gson.toJson(list)

        editor.putString(Constants.LIST, jsonString)
        editor.apply()
    }

    fun getFromPrefs(): ArrayList<User> {
        val jsonString = sharedPref.getString(Constants.LIST, "")
        var list = ArrayList<User>()
        if (jsonString != "") {
            val type: Type = object : TypeToken<ArrayList<User>>() {}.type
            list = gson.fromJson<ArrayList<User>>(jsonString, type)
        }
        return list
    }
}