package com.bishwajeet.questionnaire.view.onboarding

import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class OnboardingActivityModule {

    @Binds
    abstract fun provideOnboardingActivityView(activity: OnboardingActivity): IOnboardingContract.OnboardingView

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideOnboardingActivityPresenter(onboardingActivityView: IOnboardingContract.OnboardingView, databaseInterface: IFirebaseDatabase, iSharedPreferenceContract: ISharedPreferenceContract): IOnboardingContract.OnboardingPresenter =
                OnboardingActivityPresenter(onboardingActivityView, databaseInterface, iSharedPreferenceContract)
    }
}