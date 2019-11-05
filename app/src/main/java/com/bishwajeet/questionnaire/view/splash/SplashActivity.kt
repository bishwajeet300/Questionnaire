package com.bishwajeet.questionnaire.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.bishwajeet.questionnaire.R
import com.bishwajeet.questionnaire.utils.INTENT_FROM_KEY
import com.bishwajeet.questionnaire.utils.INTENT_SPLASH_VALUE
import com.bishwajeet.questionnaire.utils.isUsernameValid
import com.bishwajeet.questionnaire.view.onboarding.OnboardingActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), ISplashContract.SplashView {

    @Inject
    lateinit var presenter: ISplashContract.SplashPresenter
    private lateinit var ivDone: ImageView
    private lateinit var etUsername: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_splash)

        ivDone = findViewById(R.id.ivDone)
        etUsername = findViewById(R.id.etUsername)

        ivDone.setOnClickListener {
            if (!TextUtils.isEmpty(etUsername.text) && isUsernameValid(etUsername.text.toString())) {
                presenter.saveUsername(etUsername.text.toString())
                intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                intent.putExtra(INTENT_FROM_KEY, INTENT_SPLASH_VALUE)
                startActivity(intent)
                finish()
            }
        }

        etUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                ivDone.performClick()
                true
            } else {
                false
            }
        }
    }


    override fun onSplashActivityLoading() {
        Handler().postDelayed({
            etUsername.visibility = View.VISIBLE
            etUsername.animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.move_up_100)
            etUsername.animate()
        }, 1500)

        Handler().postDelayed({
            ivDone.visibility = View.VISIBLE
            ivDone.animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.move_up_100)
            ivDone.animate()
        }, 2000)
    }
}