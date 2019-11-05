package com.bishwajeet.questionnaire.model.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(context: Context): SharedPreferenceManager {
        return SharedPreferenceManager(context)
    }
}