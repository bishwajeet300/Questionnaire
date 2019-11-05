package com.bishwajeet.questionnaire.model.pojo

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class Answer (
        var questionId: String = "",
        var answer: String = ""
) : Parcelable








