package com.bishwajeet.questionnaire.di

import android.content.Context
import com.bishwajeet.questionnaire.QuestionnaireApp
import com.bishwajeet.questionnaire.model.firebase.database.FirebaseDatabaseManager
import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import com.bishwajeet.questionnaire.model.preferences.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideContext(application: QuestionnaireApp): Context = application.applicationContext


        @JvmStatic
        @Provides
        @Singleton
        fun provideSharedPreferencesManager(context: Context): ISharedPreferenceContract = SharedPreferenceManager(context)


        @JvmStatic
        @Provides
        @Singleton
        fun provideDataServices(): IFirebaseDatabase = FirebaseDatabaseManager()
    }
}