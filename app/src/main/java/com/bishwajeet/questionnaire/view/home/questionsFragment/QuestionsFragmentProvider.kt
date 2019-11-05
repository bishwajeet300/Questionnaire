package com.bishwajeet.questionnaire.view.home.questionsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class QuestionsFragmentProvider{

    @ContributesAndroidInjector(modules = [QuestionsFragmentModule::class])
    abstract fun bindQuestionsFragment(): QuestionsFragment
}