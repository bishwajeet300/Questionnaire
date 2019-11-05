package com.bishwajeet.questionnaire.di

import com.bishwajeet.questionnaire.QuestionnaireApp
import com.bishwajeet.questionnaire.model.firebase.FirebaseModule
import com.bishwajeet.questionnaire.model.firebase.database.FirebaseDatabaseManager
import com.bishwajeet.questionnaire.model.preferences.SharedPreferenceManager
import com.bishwajeet.questionnaire.model.preferences.SharedPreferenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    FirebaseModule::class,
    SharedPreferenceModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: QuestionnaireApp)


    override fun inject(instance: DaggerApplication)


    fun getSharedPreferenceManager(): SharedPreferenceManager


    fun getFirebaseDatabaseManager(): FirebaseDatabaseManager


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: QuestionnaireApp): Builder


        fun build(): AppComponent
    }
}