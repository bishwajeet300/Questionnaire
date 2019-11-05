package com.bishwajeet.questionnaire.view.home

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.utils.*
import com.bishwajeet.questionnaire.view.home.questionsFragment.QuestionsAdapter
import com.bishwajeet.questionnaire.view.home.questionsFragment.QuestionsFragment
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject


class HomeActivity : DaggerAppCompatActivity(), IHomeContract.HomeView, QuestionsFragment.OnOptionSelectedListener {

    @Inject
    lateinit var presenter: IHomeContract.HomePresenter

    private var OPERATIONMODE: Boolean = false
    private lateinit var tvTitle: TextView
    private lateinit var tvCategories: TextView
    private lateinit var tvQuestions: TextView
    private lateinit var tvLastQuestion: TextView
    private lateinit var tvSecondLastQuestion: TextView
    private lateinit var tvCurrentQuestion: TextView
    private lateinit var llParent: LinearLayout
    private lateinit var vpQuestions: QuestionsViewPager
    private lateinit var pagerAdapter: QuestionsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        if (null == savedInstanceState) {
            presenter.onDataLoad(OPERATIONMODE, true)
        } else {
            presenter.onDataLoad(OPERATIONMODE, false)
        }
    }


    private fun initUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(com.bishwajeet.questionnaire.R.layout.activity_home)

        tvTitle = findViewById(com.bishwajeet.questionnaire.R.id.tvTitle)
        tvCategories = findViewById(com.bishwajeet.questionnaire.R.id.tvCategories)
        tvQuestions = findViewById(com.bishwajeet.questionnaire.R.id.tvQuestions)
        tvLastQuestion = findViewById(com.bishwajeet.questionnaire.R.id.tvLastQuestion)
        tvSecondLastQuestion = findViewById(com.bishwajeet.questionnaire.R.id.tvSecondLastQuestion)
        tvCurrentQuestion = findViewById(com.bishwajeet.questionnaire.R.id.tvCurrentQuestion)
        llParent = findViewById(com.bishwajeet.questionnaire.R.id.llParent)
        vpQuestions = findViewById(com.bishwajeet.questionnaire.R.id.vpQuestions)

        if (intent.hasExtra(INTENT_OPERATION_MODE)) {
            when (intent.getStringExtra(INTENT_OPERATION_MODE)) {
                INTENT_READ_MODE -> {
                    tvTitle.text = getString(com.bishwajeet.questionnaire.R.string.app_name_view)
                    OPERATIONMODE = false
                    vpQuestions.offscreenPageLimit = 5
                    vpQuestions.setSwipePagingEnabled(true)

                    vpQuestions.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                        override fun onPageScrollStateChanged(state: Int) {}

                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                        override fun onPageSelected(position: Int) {
                            presenter.onPageSelected(position)
                        }
                    })
                }
                INTENT_WRITE_MODE -> {
                    tvTitle.text = getString(com.bishwajeet.questionnaire.R.string.app_name)
                    OPERATIONMODE = true
                    vpQuestions.offscreenPageLimit = 1
                    vpQuestions.setSwipePagingEnabled(true)
                }
            }
        }
    }


    override fun dataLoadSuccessful(items: List<Question>, mode: Boolean, answerList: LinkedList<Answer>?) {
        pagerAdapter = QuestionsAdapter(supportFragmentManager, items, mode, answerList)
        vpQuestions.adapter = pagerAdapter
    }


    override fun setCategoryCounter(strCategoryCounter: String) {
        val spannable = SpannableStringBuilder()
        spannable.append(strCategoryCounter)
        spannable.append(" ")
        spannable.append(getString(com.bishwajeet.questionnaire.R.string.categories))
        spannable.setSpan(TextAppearanceSpan(this@HomeActivity, com.bishwajeet.questionnaire.R.style.CounterStyleTextRegular), 0, strCategoryCounter.indexOf("/"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        tvCategories.text = spannable
    }


    override fun setQuestionCounter(strQuestionCounter: String) {
        val spannable = SpannableStringBuilder()
        spannable.append(strQuestionCounter)
        spannable.append(" ")
        spannable.append(getString(com.bishwajeet.questionnaire.R.string.questions))
        spannable.setSpan(TextAppearanceSpan(this@HomeActivity, com.bishwajeet.questionnaire.R.style.CounterStyleTextLarge), 0, strQuestionCounter.indexOf("/"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        tvQuestions.text = spannable
    }


    override fun setCurrentQuestion(strCurrentQuestion: String) {
        tvCurrentQuestion.animation = AnimationUtils.loadAnimation(this@HomeActivity, com.bishwajeet.questionnaire.R.anim.move_up_100)
        tvCurrentQuestion.text = strCurrentQuestion
        tvCurrentQuestion.animate()
    }


    override fun setLastQuestion(strLastQuestion: String) {
        tvLastQuestion.animation = AnimationUtils.loadAnimation(this@HomeActivity, com.bishwajeet.questionnaire.R.anim.move_up_200)
        tvLastQuestion.text = strLastQuestion
        tvLastQuestion.animate()
    }


    override fun setSecondLastQuestion(strSecondLastQuestion: String) {
        tvSecondLastQuestion.animation = AnimationUtils.loadAnimation(this@HomeActivity, com.bishwajeet.questionnaire.R.anim.move_up_300)
        tvSecondLastQuestion.text = strSecondLastQuestion
        tvSecondLastQuestion.animate()
    }


    override fun onOptionSelected() {

        vpQuestions.postDelayed({
            if (vpQuestions.adapter!!.count >= vpQuestions.currentItem) {
                presenter.onPageSelected(vpQuestions.currentItem + 1)
            }
            vpQuestions.setCurrentItem(vpQuestions.currentItem + 1, true)
        }, 500)
    }


    override fun closeQuestionnaire() {
        Handler().postDelayed({
            vpQuestions.clearAnimation()
            setResult(Activity.RESULT_OK)
            finish()
            Toast.makeText(this@HomeActivity, "Questionnaire Saved", Toast.LENGTH_LONG).show()
        }, 500)
    }


    override fun showUserContent() {

    }


    override fun isLaunchedInReadMode(): Boolean {
        return !OPERATIONMODE
    }


    override fun showSubQuestion(ifPositiveModel: IfPositiveModel, answer: String) {
        (supportFragmentManager.fragments[vpQuestions.currentItem] as QuestionsFragment).showConditionalQuestionView(ifPositiveModel, false, answer)
    }


    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is QuestionsFragment) {
            fragment.setOnOptionSelectedListener(this)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POSITION_KEY, vpQuestions.currentItem)
        outState.putString(CATEGORY_COUNTER_KEY, tvCategories.text.toString())
        outState.putString(QUESTION_COUNTER_KEY, tvQuestions.text.toString())
        outState.putString(CURRENT_QUESTION_KEY, tvCurrentQuestion.text.toString())
        outState.putString(LAST_QUESTION_KEY, tvLastQuestion.text.toString())
        outState.putString(SECOND_LAST_QUESTION_KEY, tvSecondLastQuestion.text.toString())

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            val position: Int = savedInstanceState.getInt(POSITION_KEY)

            tvCategories.text = savedInstanceState.getString(CATEGORY_COUNTER_KEY)
            tvQuestions.text = savedInstanceState.getString(QUESTION_COUNTER_KEY)
            tvLastQuestion.text = savedInstanceState.getString(LAST_QUESTION_KEY)
            tvSecondLastQuestion.text = savedInstanceState.getString(SECOND_LAST_QUESTION_KEY)
            tvCurrentQuestion.text = savedInstanceState.getString(CURRENT_QUESTION_KEY)

            vpQuestions.postDelayed({
                if (vpQuestions.currentItem != position) {
                    vpQuestions.setCurrentItem(position, true)
                }
            }, 100)
        }
    }
}