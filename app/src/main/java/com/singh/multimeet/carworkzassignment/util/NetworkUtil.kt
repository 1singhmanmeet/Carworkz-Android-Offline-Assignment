package com.singh.multimeet.carworkzassignment.util

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class NetworkUtil() {

    companion object {
        private var client: OkHttpClient.Builder?=null

        fun makeRequest(username:String,password:String):OkHttpClient{
            if(client==null) {
                client = OkHttpClient.Builder()
                client!!.authenticator(Authenticator { route, response ->
                    if (responseCount(response) >= 3) {
                        return@Authenticator null
                    }
                    val credential = Credentials.basic(username, password)
                    response.request().newBuilder().header("Authorization", credential).build()
                })
                client!!.connectTimeout(10, TimeUnit.SECONDS)
                client!!.writeTimeout(10, TimeUnit.SECONDS)
                client!!.readTimeout(30, TimeUnit.SECONDS)
            }
            return client!!.build()
        }

        private fun responseCount(response: Response): Int {
            var tempResponse:Response? =null
            tempResponse = response
            var result = 1
            tempResponse = tempResponse.priorResponse()
            try {
                while (tempResponse != null) {
                    tempResponse = tempResponse.priorResponse()!!
                    result++
                }
                return result
            }catch (e:Exception){
                return 3
            }

        }
    }


}