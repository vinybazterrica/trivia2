package com.viny.trivia2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viny.trivia2.databinding.ActivityMainBinding
import com.viny.trivia2.helper.StorageHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMaxScore()
        setListener()
    }

    private fun setListener(){
        binding.btnJugar.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMaxScore(){
        binding.tvMaxScorePoint.text = StorageHelper.getMaxScore(this).toString()
    }

    override fun onResume() {
        super.onResume()
        setMaxScore()
    }
}