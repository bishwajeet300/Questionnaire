package com.bishwajeet.questionnaire.model.firebase

import com.bishwajeet.questionnaire.model.firebase.database.FirebaseDatabaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabaseManager(): FirebaseDatabaseManager {
        return FirebaseDatabaseManager()
    }
}