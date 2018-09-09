package com.singh.multimeet.carworkzassignment.activites.login

import android.util.Base64
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.StringRequestListener
import com.singh.multimeet.carworkzassignment.util.Constants
import com.singh.multimeet.carworkzassignment.util.NetworkUtil
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.ByteString
import org.json.JSONException
import org.json.JSONObject
import org.reactivestreams.Subscription
import java.io.IOException


public class LoginPresenter(val viewContract:LoginContract.View):LoginContract.Presenter{


    val TAG=LoginPresenter::class.simpleName

    override fun login(username: String, password: String) {
        val httpClient=NetworkUtil.makeRequest(username,password)
        val request = Request.Builder()
                .url(Constants.LOGIN_URL)
                .build()
        val response=httpClient.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                viewContract.onLoginComplete(false,e!!.message!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                viewContract.onLoginComplete(true,response!!.body()!!.string())
            }

        })

    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}