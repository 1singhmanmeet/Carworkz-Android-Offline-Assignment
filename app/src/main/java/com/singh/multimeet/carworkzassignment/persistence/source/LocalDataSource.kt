package com.singh.multimeet.carworkzassignment.persistence.source

import com.singh.multimeet.carworkzassignment.persistence.data.UserDao
import com.singh.multimeet.carworkzassignment.persistence.model.User
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.Flowable

public class LocalDataSource(val userDao:UserDao):DataSource<User>{
    override fun getUsers(name: String): Flowable<List<User>> {
        return userDao.getUsers(name)
    }


    override fun getAll(): Flowable<List<User>> {
        return userDao.getAllUsers()
    }

    override fun insert(user: User):Completable {
        return Completable.fromAction {
            userDao.insertUser(user)
        }

    }

}