package com.bishwajeet.questionnaire.view.home.questionsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bishwajeet.questionnaire.R
import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.IfPositiveModel
import com.bishwajeet.questionnaire.model.pojo.Question
import com.bishwajeet.questionnaire.utils.ANSWER_KEY
import com.bishwajeet.questionnaire.utils.INTENT_OPERATION_MODE
import com.bishwajeet.questionnaire.utils.QUESTION_KEY
import com.bishwajeet.questionnaire.utils.RADIO_GROUP_ID
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.Exception
import javax.inject.Inject

class QuestionsFragment : dagger.android.support.DaggerFragment(), IQuestionsContract.QuestionsView {

    @Inject
    lateinit var presenter: IQuestionsContract.QuestionsPresenter
    private lateinit var callback: OnOptionSelectedListener
    private lateinit var cvOptions: CardView
    private lateinit var bottomSheetDialog: BottomSheetDialog

    companion object {

        fun newInstance(question: Question, mode: Boolean, answer: String?): QuestionsFragment {
            val args = Bundle()
            args.putParcelable(QUESTION_KEY, question)
            args.putBoolean(INTENT_OPERATION_MODE, mode)
            args.putString(ANSWER_KEY, answer)
            val questionFragment = QuestionsFragment()
            questionFragment.arguments = args
            return questionFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView: View = inflater.inflate(R.layout.fragment_questions, container, false)
        cvOptions = mView.findViewById(R.id.cvOptions)

        if (null != arguments) {
            val questionObject = arguments!!.getParcelable<Question>(QUESTION_KEY)
            val isWriteMode: Boolean = arguments!!.getBoolean(INTENT_OPERATION_MODE)

            var answer = ""
            if (!isWriteMode && arguments!!.containsKey(ANSWER_KEY)) {
                answer = arguments!!.getString(ANSWER_KEY)
            }

            val options = questionObject!!.question_type.options
            val itemCount = options.size

            val radioButtons = arrayOfNulls<RadioButton>(itemCount)
            val radioGroup = RadioGroup(requireContext())
            radioGroup.id = RADIO_GROUP_ID
            radioGroup.orientation = RadioGroup.VERTICAL
            radioGroup.gravity = Gravity.CENTER

            if (isWriteMode) {
                radioGroup.isClickable = true
                radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    val currentAnswer = Answer(questionObject.id.toString(), mView.findViewById<RadioButton>(checkedId).text as String)
                    presenter.saveSelection(currentAnswer)
                    if (!presenter.shouldWaitForSubQuestion(questionObject, currentAnswer)) {
                        callback.onOptionSelected()
                    }
                }
            } else {
                radioGroup.setOnCheckedChangeListener(null)
                radioGroup.isClickable = false
            }


            for (counter in 0 until itemCount) {
                radioButtons[counter] = RadioButton(requireContext())
                radioButtons[counter]?.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_selector_options)
                radioButtons[counter]?.buttonDrawable = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
                radioButtons[counter]?.typeface = ResourcesCompat.getFont(requireContext(), R.font.raleway_medium)
                radioButtons[counter]?.textSize = 18F
                radioButtons[counter]?.setPadding(16, 16, 16, 16)

                val params = RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(15, 15, 15, 15)
                radioButtons[counter]?.layoutParams = params


                radioButtons[counter]?.text = questionObject.question_type.options[counter]
                radioButtons[counter]?.id = counter + 100

                radioButtons[counter]?.isEnabled = isWriteMode

                if (!isWriteMode && questionObject.question_type.options[counter] == answer) {
                    radioButtons[counter]?.isChecked = true
                }

                radioGroup.addView(radioButtons[counter])
            }
            cvOptions.addView(radioGroup)
        }
        return mView
    }


    override fun onStart() {
        super.onStart()
        presenter.onLoading()
    }


    fun setOnOptionSelectedListener(callback: OnOptionSelectedListener) {
        this.callback = callback
    }


    @SuppressLint("InflateParams")
    override fun showConditionalQuestionView(ifPositiveModel: IfPositiveModel, operationmode: Boolean, answer: String) {
        try {
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            val shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

            bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(view)

            val tvFollowUpQuestion: TextView = view.findViewById(R.id.tvFollowUpQuestion)
            val tvAnswer: TextView = view.findViewById(R.id.tvAnswer)
            val sbAnswer: SeekBar = view.findViewById(R.id.sbAnswer)
            sbAnswer.max = ifPositiveModel.if_positive.question_type.range.to
            sbAnswer.max = ifPositiveModel.if_positive.question_type.range.to
            tvFollowUpQuestion.text = ifPositiveModel.if_positive.question

            if (operationmode) {
                sbAnswer.progress = 30
                sbAnswer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        tvAnswer.visibility = View.VISIBLE
                        tvAnswer.text = progress.toString()
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        Handler().postDelayed({
                            shake.repeatMode = Animation.INFINITE
                            shake.repeatCount = Animation.INFINITE
                            tvAnswer.startAnimation(shake)
                        }, 3000)
                    }
                })

                tvAnswer.setOnClickListener {
                    tvAnswer.clearAnimation()
                    presenter.saveSelection(Answer(ifPositiveModel.if_positive.id.toString(), tvAnswer.text.toString()))
                    bottomSheetDialog.dismiss()
                    callback.onOptionSelected()
                }
            } else {
                sbAnswer.progress = answer.toInt()
                tvAnswer.text = answer
            }

            bottomSheetDialog.show()
        } catch (exception: Exception) {
            Log.i("Questionnaire", "Seems like fragment is still not attached yet")
            Log.e("Questionnaire", exception.toString())
        }
    }


    interface OnOptionSelectedListener {
        fun onOptionSelected()
    }
}