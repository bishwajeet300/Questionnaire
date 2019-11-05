package com.bishwajeet.questionnaire.view.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bishwajeet.questionnaire.R
import com.bishwajeet.questionnaire.utils.*
import com.bishwajeet.questionnaire.view.home.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import javax.inject.Inject

class OnboardingActivity : DaggerAppCompatActivity(), IOnboardingContract.OnboardingView {

    @Inject
    lateinit var presenter: IOnboardingContract.OnboardingPresenter
    private lateinit var tvUsername: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_onboarding)

        tvUsername = findViewById(R.id.tvUsername)
        intent = Intent(this@OnboardingActivity, HomeActivity::class.java)

        llNewUser.setOnClickListener {
            intent.putExtra(INTENT_OPERATION_MODE, INTENT_WRITE_MODE)
            startActivityForResult(intent, INTENT_WRITE_REQUEST_MODE)
        }

        llOldUser.setOnClickListener {
            intent.putExtra(INTENT_OPERATION_MODE, INTENT_READ_MODE)
            startActivityForResult(intent, INTENT_READ_REQUEST_MODE)
        }
    }


    override fun onStart() {
        super.onStart()
        presenter.onDataLoad()
    }


    override fun dataLoadSuccessful(username: String) {
        val greetingText = getString(R.string.greeting).plus(" ").plus(username)
        tvUsername.text = greetingText
    }


    override fun disableReadMode() {
        llOldUser.isEnabled = false
        llOldUser.setOnClickListener {}
    }


    override fun enableReadMode() {
        llOldUser.isEnabled = true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_READ_REQUEST_MODE.hashCode()) {
                tvUsername.text = getString(R.string.update_message)
            } else if(requestCode == INTENT_WRITE_REQUEST_MODE.hashCode()) {
                tvUsername.text = getString(R.string.awesome).plus("\n").plus(getString(R.string.greeting_2))
            }
        }
    }
}
