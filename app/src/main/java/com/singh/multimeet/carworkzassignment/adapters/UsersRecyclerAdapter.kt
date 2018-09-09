package com.singh.multimeet.carworkzassignment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.singh.multimeet.carworkzassignment.R
import com.singh.multimeet.carworkzassignment.persistence.model.User

public class UsersRecyclerAdapter(var userList:List<User>):RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>(){


    var mainUserList:ArrayList<User>?=null

    init {
        mainUserList=userList as ArrayList<User>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):UserViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.user_item_view,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder:UserViewHolder, position: Int) {
        holder.username.text=userList[position].username
        holder.headText.text=userList[position].username[0].toString()
        holder.profileLink.text=userList[position].profileLink
    }

    inner class UserViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val username=view.findViewById<TextView>(R.id.name)
        val image=view.findViewById<ImageView>(R.id.image)
        val headText=view.findViewById<TextView>(R.id.headText)
        val profileLink=view.findViewById<TextView>(R.id.profile_link)

    }

    fun onSearchQuery(text:String){
        val newUserList=ArrayList<User>()

        if(text.isEmpty()){
            userList=mainUserList!!.toList()
            notifyDataSetChanged()
            return
        }
        for(user in mainUserList!!){
            if(user.username.toLowerCase().contains(text.toLowerCase())){
                newUserList.add(user)
            }
        }
        userList=newUserList.toList()
        notifyDataSetChanged()
    }

}