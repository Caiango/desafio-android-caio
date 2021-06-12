package com.picpay.desafio.android.view

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.retrofit.User
import com.picpay.desafio.android.retrofit.UserService
import com.picpay.desafio.android.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var mUserService: UserService
    private lateinit var mainViewModel: UserViewModel

    override fun onResume() {
        super.onResume()
        mainViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserService = UserService()
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar.visibility = View.VISIBLE

        mainViewModel.userList.observe(this, Observer {
            userList = it
            adapter.users = userList

        })

        mUserService.searchData(this, progressBar)

    }
}
