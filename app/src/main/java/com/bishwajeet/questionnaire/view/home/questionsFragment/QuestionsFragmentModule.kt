package com.bishwajeet.questionnaire.view.home.questionsFragment

import com.bishwajeet.questionnaire.model.firebase.database.IFirebaseDatabase
import com.bishwajeet.questionnaire.model.preferences.ISharedPreferenceContract
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class QuestionsFragmentModule {

    @Binds
    abstract fun provideQuestionsFragmentView(questionsFragment: QuestionsFragment): IQuestionsContract.QuestionsView

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideSubMenuActivityPresenter(questionsView: IQuestionsContract.QuestionsView, databaseInterface: IFirebaseDatabase, sharedPreferencesContract: ISharedPreferenceContract): IQuestionsContract.QuestionsPresenter =
                QuestionsFragmentPresenter(questionsView, databaseInterface, sharedPreferencesContract)
    }
}