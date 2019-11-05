package com.bishwajeet.questionnaire.di

import com.bishwajeet.questionnaire.view.home.HomeActivity
import com.bishwajeet.questionnaire.view.home.HomeActivityModule
import com.bishwajeet.questionnaire.view.home.questionsFragment.QuestionsFragmentProvider
import com.bishwajeet.questionnaire.view.onboarding.OnboardingActivity
import com.bishwajeet.questionnaire.view.onboarding.OnboardingActivityModule
import com.bishwajeet.questionnaire.view.splash.SplashActivity
import com.bishwajeet.questionnaire.view.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity


    @PerActivityScope
    @ContributesAndroidInjector(modules = [OnboardingActivityModule::class])
    abstract fun bindOnboardingActivity(): OnboardingActivity


    @PerActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class, QuestionsFragmentProvider::class])
    abstract fun bindHomeActivity(): HomeActivity
}