package com.bishwajeet.questionnaire.model.preferences

import android.content.Context
import android.content.SharedPreferences
import com.bishwajeet.questionnaire.utils.USERNAME_KEY
import java.util.*
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(context: Context) : ISharedPreferenceContract {

    val preferences: SharedPreferences = context.getSharedPreferences("QuestionnairePreferences", Context.MODE_PRIVATE)


    override fun storeUsername(username: String) {

        preferences.edit().putString(USERNAME_KEY, username).apply()
    }


    override fun fetchUsername(): String {
        return preferences.getString(USERNAME_KEY, "johndoe" + UUID.randomUUID())
    }
}