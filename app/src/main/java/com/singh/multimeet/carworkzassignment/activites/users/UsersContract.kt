package com.singh.multimeet.carworkzassignment.activites.users

import com.singh.multimeet.carworkzassignment.BasePresenter
import com.singh.multimeet.carworkzassignment.BaseView
import com.singh.multimeet.carworkzassignment.persistence.model.User

public interface UsersContract{

    interface View:BaseView<Presenter>{
        fun onUsersLoad(userList: List<User>)
    }

    interface Presenter:BasePresenter{
        fun getUsers()
    }
}