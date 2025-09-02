package com.viny.trivia2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.viny.trivia2.models.db.QuestionEntity

class DBQuestion(context : Context): OrmLiteSqliteOpenHelper(
    context,
    "questions.db",
    null,
    1
) {
    override fun onCreate(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?
    ) {
        TableUtils.createTableIfNotExists(connectionSource, QuestionEntity::class.java)
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TableUtils.dropTable<QuestionEntity, Any>(connectionSource, QuestionEntity::class.java, true)
        onCreate(database, connectionSource)
    }

    fun getQuestionDao(): Dao<QuestionEntity, String> {
        return getDao(QuestionEntity::class.java)
    }
}