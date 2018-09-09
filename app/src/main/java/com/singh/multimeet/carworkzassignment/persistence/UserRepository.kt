package com.singh.multimeet.carworkzassignment.persistence

import android.util.Log
import com.singh.multimeet.carworkzassignment.persistence.model.User
import com.singh.multimeet.carworkzassignment.persistence.source.LocalDataSource
import com.singh.multimeet.carworkzassignment.persistence.source.RemoteDataSource
import io.reactivex.Flowable
import io.reactivex.FlowableSubscriber
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription

public class UserRepository(val localDataSource: LocalDataSource,
                            val remoteDataSource: RemoteDataSource
                            ){

    var repositoryDataListener: RepositoryDataListener<User>?=null
    val compositeDisposable=CompositeDisposable()
    fun setDataListener(repositoryDataListener: RepositoryDataListener<User>){
        this.repositoryDataListener=repositoryDataListener
    }

    fun getAllUsers(){
        var userList:List<User>?=null
        Log.e("Repo: ","Inside User Repo")
        fetchUsersFromRemote(false)
        localDataSource.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:FlowableSubscriber<List<User>>{
                    override fun onComplete() {

                        if(userList!=null && userList!!.size>0){
                            Log.e("Presenter: ","Database empty")
                            repositoryDataListener!!.onDataLoaded(userList!!)
                        }else{
                            Log.e("Presenter: ","fetching data from server")
                            fetchUsersFromRemote(false)
                        }
                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(Long.MAX_VALUE)
                    }

                    override fun onNext(t: List<User>?) {
                        userList=t!!
                    }

                    override fun onError(t: Throwable?) {
                        Log.e("UserRepository","Error: ${t!!.message}")

                    }

                })
    }

    var nameToSearch:String?=null

    fun getUsers(name:String){
        nameToSearch=name
        var userList:List<User>?=null
        localDataSource.getUsers(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:FlowableSubscriber<List<User>>{
                    override fun onComplete() {
                        if(userList!=null){
                            repositoryDataListener!!.onDataLoaded(userList!!)
                        }else{
                            fetchUsersFromRemote(true)
                        }
                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(Long.MAX_VALUE)
                    }

                    override fun onNext(t: List<User>?) {
                        userList=t!!
                    }

                    override fun onError(t: Throwable?) {

                        Log.e("UserRepository","Error: ${t!!.message}")
                    }

                })
    }

    fun fetchUsersFromRemote(isSearch:Boolean){
        var userList:List<User>?=null
        Log.e("Repo","Fetching!!")
        remoteDataSource.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:FlowableSubscriber<List<User>>{
                    override fun onComplete() {
                        if(userList!=null){
                            if(!isSearch) {
                                Log.e("User Repo: ","fetched from remote")

                                for (user in userList!!) {
                                    compositeDisposable.add(localDataSource.insert(user)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe())

                                }
                                repositoryDataListener!!.onDataLoaded(userList!!)
                            }else{
                                getUsers(nameToSearch!!)
                            }
                        }

                    }

                    override fun onSubscribe(s: Subscription) {
                        s.request(Long.MAX_VALUE)
                    }

                    override fun onNext(t: List<User>?) {
                        userList=t!!
                    }

                    override fun onError(t: Throwable?) {
                        t!!.printStackTrace()
                        Log.e("UserRepository","Error: ${t!!.message}")
                    }

                })
    }
}