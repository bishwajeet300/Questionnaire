package com.bishwajeet.questionnaire.view.home.questionsFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.utils.getQuestionsAnswer
import java.util.*

class QuestionsAdapter(fragmentManager: FragmentManager, private val items: List<Question>, private val mode: Boolean, private val answerList: LinkedList<Answer>?) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return QuestionsFragment.newInstance(items[position], mode, getQuestionsAnswer(items[position], answerList))
    }

    override fun getCount(): Int {
        return items.size
    }
}