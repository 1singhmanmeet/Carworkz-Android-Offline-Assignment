package com.singh.multimeet.carworkzassignment.persistence.source

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.singh.multimeet.carworkzassignment.persistence.model.User
import com.singh.multimeet.carworkzassignment.util.Constants
import io.reactivex.*
import org.json.JSONArray
import org.json.JSONException


public class RemoteDataSource():DataSource<User>{


    override fun getUsers(name: String): Flowable<List<User>> {
        TODO("not implemented")
    }

    override fun getAll(): Flowable<List<User>> {
        return Flowable.create(object:FlowableOnSubscribe<List<User>>{
            override fun subscribe(emitter: FlowableEmitter<List<User>>) {
                AndroidNetworking.get("${Constants.USERS_URL}")
                        .setTag("getUsers")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(object: StringRequestListener {
                            override fun onResponse(response: String?) {
                                val userList=ArrayList<User>()
                                try {
                                    Log.e("manmeeet","response: ${response}")
                                    val responseArray=JSONArray(response)
                                    var i=0

                                    while(i<responseArray!!.length()){
                                        Log.e("Remote",responseArray.get(i).toString())
                                        userList.add(Constants.convertToUser(responseArray.get(i).toString())!!)
                                        i++
                                    }
                                    emitter.onNext(userList)
                                    emitter.onComplete()
                                }catch (e:JSONException){
                                    e.printStackTrace()
                                }
                                emitter.onComplete()
                            }

                            override fun onError(anError: ANError?) {
                                anError!!.printStackTrace()
                                Log.e("Remotedatasource","Error: ${anError!!.message}")
                            }

                        })
            }

        },BackpressureStrategy.BUFFER)
    }

    override fun insert(user: User):Completable {
        TODO("not implemented")
    }

}