package com.singh.multimeet.carworkzassignment.activites.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.singh.multimeet.carworkzassignment.R
import com.singh.multimeet.carworkzassignment.activites.users.Users
import com.singh.multimeet.carworkzassignment.util.Constants
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class Login : AppCompatActivity(),LoginContract.View{

    var TAG=Login::class.simpleName
    var loginPresenter:LoginPresenter?=null
    var sharedPreferences:SharedPreferences?=null
    var progressDialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences=getSharedPreferences(Constants.LOCAL_PREFS, Context.MODE_PRIVATE)
        loginPresenter=LoginPresenter(this)

        // checking if user is already logged-in, then go to Users page.
        if(!sharedPreferences!!.getString(Constants.USERNAME,"").equals(""))
            goToUsersPage()

        login.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(username.text.isNotEmpty() && password.text.isNotEmpty()){
                    progressDialog= ProgressDialog(this@Login)
                    progressDialog!!.setMessage("Loading...")
                    progressDialog!!.setCancelable(false)
                    progressDialog!!.show()
                    loginPresenter!!.login(username.text.toString(),password.text.toString())
                }
                else{
                    progressDialog!!.dismiss()
                    Toast.makeText(this@Login,"Both the fields are required!!!",
                            Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    // function to go to user activity

    fun goToUsersPage(){
        val usersIntent = Intent(this, Users::class.java)
        usersIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(usersIntent)
        finish()
    }

    override fun onLoginComplete(isSuccess: Boolean, response: String) {
        progressDialog!!.dismiss()
        Log.e(TAG,response)
        if(response.contains("Bad credentials")){
            runOnUiThread(object:Runnable{
                override fun run() {
                    Toast.makeText(applicationContext,"Invalid Username or Password!!!",
                            Toast.LENGTH_SHORT).show()
                }
            })
            return
        }
        if(isSuccess){
            try {
                val jsonObject = JSONObject(response)
                val edit = sharedPreferences!!.edit()
                edit.putString(Constants.USERNAME, jsonObject.getString(Constants.USERNAME))
                edit.putString(Constants.EMAIL, jsonObject.getString(Constants.EMAIL))
                edit.commit()
                goToUsersPage()

            }catch (e:Exception){
                progressDialog!!.dismiss()

            }
        }else{
            progressDialog!!.dismiss()

        }
    }

    override fun setPresenter(presenter: LoginContract.Presenter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
