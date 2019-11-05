package com.bishwajeet.questionnaire.model.pojo

import android.annotation.SuppressLint
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class IfPositiveModel(
        var if_positive: IfPositive = IfPositive(),
        var predicate: Predicate = Predicate()
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class IfPositive(
            var category: String = "",
            var id: Long = Long.MIN_VALUE,
            var question: String = "",
            var question_type: QuestionType = QuestionType()
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class QuestionType(
                var range: Range = Range(),
                var type: String = ""
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Range(
                    var from: Int = 0,
                    var to: Int = 0
            ) : Parcelable
        }
    }

    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Predicate(
            var exactEquals: List<String> = listOf()
    ) : Parcelable
}