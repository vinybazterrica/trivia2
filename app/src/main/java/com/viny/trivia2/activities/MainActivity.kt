package com.viny.trivia2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.viny.trivia2.R
import com.viny.trivia2.databinding.ActivityMainBinding
import com.viny.trivia2.helper.IntentHelper
import com.viny.trivia2.helper.StorageHelper

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMaxScore()
        showSocialButtons()
        setListener()
    }

    private fun setListener() {
        binding.btnJugar.setOnClickListener {
            IntentHelper.goToQuestions(this, null)
        }

        binding.lavGithub.setOnClickListener {
            goToGithub()
        }

        binding.lavLinkedin.setOnClickListener {
            goToLinkedin()
        }
    }

    private fun setMaxScore() {
        binding.tvMaxScorePoint.text = StorageHelper.getMaxScore().toString()
    }

    private fun showSocialButtons(){
        binding.lavGithub.setAnimation(R.raw.ic_lottie_github)
        binding.lavGithub.playAnimation()

        binding.lavLinkedin.setAnimation(R.raw.ic_lottie_linkedin)
        binding.lavLinkedin.playAnimation()

        setListener()
    }


    override fun onResume() {
        super.onResume()
        setMaxScore()
    }
}