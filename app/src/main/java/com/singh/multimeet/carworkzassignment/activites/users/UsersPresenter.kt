package com.singh.multimeet.carworkzassignment.activites.users


import android.util.Log
import com.singh.multimeet.carworkzassignment.persistence.RepositoryDataListener
import com.singh.multimeet.carworkzassignment.persistence.UserRepository
import com.singh.multimeet.carworkzassignment.persistence.model.User

public class UsersPresenter(val userRepository:UserRepository ,val viewContract:UsersContract.View):UsersContract.Presenter{



    override fun getUsers() {
        userRepository.setDataListener(object:RepositoryDataListener<User>{
            override fun onDataLoaded(responseList: List<User>) {
                viewContract.onUsersLoad(responseList)
            }

        })
        Log.e("Presenter: ","Inside User Presenter")
        userRepository.getAllUsers()
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}