package com.bishwajeet.questionnaire.model.preferences

interface ISharedPreferenceContract {

    fun storeUsername(username: String)

    fun fetchUsername() : String
}