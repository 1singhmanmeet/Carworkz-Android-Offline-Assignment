package com.singh.multimeet.carworkzassignment.persistence.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.singh.multimeet.carworkzassignment.persistence.model.User
import io.reactivex.Flowable


@Dao
public interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flowable<List<User>>

    @Query("SELECT * FROM users WHERE username LIKE '%' || :name || '%'")
    fun getUsers(name:String):Flowable<List<User>>
}