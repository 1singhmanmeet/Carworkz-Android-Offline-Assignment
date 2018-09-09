package com.singh.multimeet.carworkzassignment.persistence

interface RepositoryDataListener<T>{
    fun onDataLoaded(responseList:List<T>)
}