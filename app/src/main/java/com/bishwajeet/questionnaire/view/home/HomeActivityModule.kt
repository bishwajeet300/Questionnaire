package com.bishwajeet.questionnaire.view.home

import com.bishwajeet.questionnaire.di.PerActivityScope
import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class HomeActivityModule {

    @Binds
    @PerActivityScope
    abstract fun provideHomeActivityView(homeActivity: HomeActivity): IHomeContract.HomeView


    @Module
    companion object {

        @JvmStatic
        @Provides
        @PerActivityScope
        fun provideHomeActivityPresenter(homeActivityView: IHomeContract.HomeView, databaseInterface: IFirebaseDatabase, sharedPreferenceContract: ISharedPreferenceContract): IHomeContract.HomePresenter =
                HomeActivityPresenter(homeActivityView, databaseInterface, sharedPreferenceContract)
    }
}