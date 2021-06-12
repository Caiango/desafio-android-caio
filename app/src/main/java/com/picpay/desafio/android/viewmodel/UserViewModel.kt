package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.retrofit.UserService

class UserViewModel: ViewModel() {

    val userList = UserService.receivedList
}