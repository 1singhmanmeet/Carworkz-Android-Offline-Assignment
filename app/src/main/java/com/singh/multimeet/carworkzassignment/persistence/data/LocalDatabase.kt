package com.singh.multimeet.carworkzassignment.persistence.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.singh.multimeet.carworkzassignment.persistence.model.User

@Database(entities = [(User::class)],version = 1)
 abstract class LocalDatabase:RoomDatabase(){

    abstract fun userDao():UserDao

    companion object {

        @Volatile private var INSTANCE:LocalDatabase?=null

        fun getInstance(context:Context):LocalDatabase=
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        LocalDatabase::class.java,"Users.db")
                        .build()
    }
}