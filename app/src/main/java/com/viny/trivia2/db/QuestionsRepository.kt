package com.viny.trivia2.db

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import com.viny.trivia2.models.Question
import com.viny.trivia2.models.db.QuestionEntity

class QuestionsRepository(context: Context) {

    private val dao: Dao<QuestionEntity, String>

    init {
        val helper = OpenHelperManager.getHelper(context, DBQuestion::class.java)
        dao = helper.getDao(QuestionEntity::class.java)
    }

    fun getQuestion(id : String): QuestionEntity{
        return dao.queryForId(id)
    }

    fun getQuestionRandom(): QuestionEntity? {
        val allQuestions = dao.queryForAll()
        return if (allQuestions.isNotEmpty()) allQuestions.random() else null
    }

    fun hasQuestions() : Boolean {
        return dao.countOf() > 0
    }

    fun getAllQuestions(): List<QuestionEntity> {
        return dao.queryForAll()
    }

    fun addQuestion(question: QuestionEntity) {
        dao.create(question)
    }

    fun deleteQuestion(id: String) {
        dao.deleteById(id)
    }
}