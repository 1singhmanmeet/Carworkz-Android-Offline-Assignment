package com.singh.multimeet.carworkzassignment.activites.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.singh.multimeet.carworkzassignment.R
import com.singh.multimeet.carworkzassignment.adapters.UsersRecyclerAdapter
import com.singh.multimeet.carworkzassignment.persistence.UserRepository
import com.singh.multimeet.carworkzassignment.persistence.data.LocalDatabase
import com.singh.multimeet.carworkzassignment.persistence.model.User
import com.singh.multimeet.carworkzassignment.persistence.source.LocalDataSource
import com.singh.multimeet.carworkzassignment.persistence.source.RemoteDataSource
import kotlinx.android.synthetic.main.activity_users.*
import android.support.v4.view.MenuItemCompat.getActionView
import android.R.menu



class Users : AppCompatActivity(),UsersContract.View,SearchView.OnQueryTextListener {



    var usersPresenter:UsersPresenter?=null
    var usersRepository:UserRepository?=null
    var usersAdapter:UsersRecyclerAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        setSupportActionBar(findViewById(R.id.toolbar))
        users.layoutManager=LinearLayoutManager(this)
        val localDataSource=LocalDataSource(LocalDatabase.getInstance(this).userDao())
        val remoteDataSource=RemoteDataSource()
        usersRepository= UserRepository(localDataSource,remoteDataSource)
        usersPresenter= UsersPresenter(usersRepository!!,this)
        //Log.e("Users: ","Inside activity")


    }

    override fun onStart() {
        super.onStart()
        usersPresenter!!.getUsers()
        refreshLayout.isRefreshing=true
    }

    override fun onUsersLoad(userList: List<User>) {
        refreshLayout.isRefreshing=false
        usersAdapter=UsersRecyclerAdapter(userList)
        users.adapter=usersAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.users_menu, menu)

        val searchItem = menu!!.findItem(R.id.searchBar)

        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Users"
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = true

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if(usersAdapter!=null){
            usersAdapter!!.onSearchQuery(newText!!)
        }
        return true
    }

    override fun setPresenter(presenter: UsersContract.Presenter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
