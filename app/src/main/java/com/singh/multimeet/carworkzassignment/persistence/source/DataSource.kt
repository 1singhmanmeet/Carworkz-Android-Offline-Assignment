package com.singh.multimeet.carworkzassignment.persistence.source

import io.reactivex.Completable
import io.reactivex.Flowable

public interface DataSource<User>{

    fun getAll(): Flowable<List<User>>
    fun insert(user:User):Completable
    fun getUsers(name:String): Flowable<List<User>>
}