package com.bishwajeet.questionnaire.model.firebase.database

import com.bishwajeet.questionnaire.model.pojo.Answer
import com.bishwajeet.questionnaire.model.pojo.Questionnaire
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseDatabaseManager : IFirebaseDatabase {

    companion object {
        private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    }


    override fun getQuestionnaire(onResult: (Questionnaire) -> Unit) {
        database.reference
                .child("questionnaire")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) = Unit

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val questionnaire = snapshot.getValue(Questionnaire::class.java)

                        questionnaire?.run { onResult(Questionnaire(categories, questions)) }
                    }
                })
    }


    override fun getUserAnswer(userId: String, onResult: (LinkedList<Answer>) -> Unit) {
        database.reference
            .child("answers")
            .child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) = Unit

                override fun onDataChange(snapshot: DataSnapshot) {
                    val answerList: LinkedList<Answer> = LinkedList()
                    var currentAnswer: Answer
                    for (postSnapshot in snapshot.children) {
                        currentAnswer = Answer(questionId = postSnapshot.key.toString(), answer = postSnapshot.value.toString())
                        answerList.add(currentAnswer)
                    }
                    answerList.run(onResult)
                }
            })
    }


    override fun saveAnswer(userId: String, answer: Answer, onResult: (Boolean) -> Unit) {
        val userAnswerReference = database.reference.child("answers").child(userId).child(answer.questionId)
        userAnswerReference.setValue(answer.answer).addOnCompleteListener { onResult(it.isSuccessful && it.isComplete) }
    }


    override fun getAnswer(userId: String, questionId: String, onResult: (Answer) -> Unit) {
        database.reference
            .child("answers")
            .child(userId)
            .child(questionId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) = Unit

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(null != snapshot.value) {
                        val currentAnswer = Answer(questionId, snapshot.value as String)
                        currentAnswer.run(onResult)
                    } else {
                        val currentAnswer = Answer(questionId, "")
                        currentAnswer.run(onResult)
                    }
                }
            })
    }


    override fun saveQuestionnaireAnswer(userId: String, answerList: List<Answer>, onResult: (Boolean) -> Unit) {
        val userAnswerReference = database.reference.child("answers").child(userId)
        userAnswerReference.setValue(answerList).addOnCompleteListener { onResult(it.isSuccessful && it.isComplete) }
    }
}