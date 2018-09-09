package com.singh.multimeet.carworkzassignment.persistence.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
public class User(@PrimaryKey val id:String,
                  val username:String,
                  val avatarUrl:String,
                  val profileLink:String
                  )