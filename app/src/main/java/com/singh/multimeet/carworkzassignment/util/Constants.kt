package com.singh.multimeet.carworkzassignment.util

import android.util.Log
import com.singh.multimeet.carworkzassignment.persistence.model.User
import org.json.JSONException
import org.json.JSONObject

public class Constants{

    companion object {
        val ACCESS_TOKEN="352752a93b4966ef2ae936cd195599c060b14ffb"
        val LOGIN_URL="https://api.github.com/user"
        val LOCAL_PREFS="user_prefs"
        val BASE_URL="https://api.github.com/"
        val USERS_URL="https://api.github.com/users"
        val ID:String="id"
        val USERNAME:String="login"
        val NAME:String="name"
        val AVATAR_URL:String="avatar_url"
        val LOCATION:String="location"
        val PROFILE_LINK:String="html_url"
        val EMAIL:String="email"
        val PUBLIC_REPOS:String="public_repos"
        val FOLLOWERS:String="followers"
        val FOLLOWING:String="following"

        // function to parse all useers

        // function to parse required values from json feed and map to @User bean class

        fun convertToUser(jsonFeed:String): User? {
            var user:User?=null
            try {
                val userJsonObject=JSONObject(jsonFeed)
                val username=userJsonObject.getString(USERNAME)
                val avatarUrl=userJsonObject.getString(AVATAR_URL)
                val profileLink=userJsonObject.getString(PROFILE_LINK)
                val id=userJsonObject.getInt(ID).toString()
                user= User(id,username,
                        avatarUrl,
                        profileLink)
            }catch (e:JSONException){
                Log.e(Constants::class.simpleName,"Error: ${e.message}")
            }
            return user
        }
    }
}