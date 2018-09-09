package com.singh.multimeet.carworkzassignment.activites.login

import com.singh.multimeet.carworkzassignment.BasePresenter
import com.singh.multimeet.carworkzassignment.BaseView
import com.singh.multimeet.carworkzassignment.persistence.model.User


public interface LoginContract{
     interface View : BaseView<Presenter> {

        fun onLoginComplete(isSuccess:Boolean,response:String)
    }

    interface Presenter : BasePresenter {

        fun login(username:String,password:String)
    }
}