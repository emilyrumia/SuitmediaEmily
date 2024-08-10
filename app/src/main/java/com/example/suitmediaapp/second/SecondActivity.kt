package com.example.suitmediaapp.second

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediaapp.R
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.suitmediaapp.first.FirstActivity
import com.example.suitmediaapp.third.ThirdActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var userTextView: TextView
    private lateinit var selectedTextView: TextView
    private lateinit var chooseUserButton: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        backButton = findViewById(R.id.back_button)
        userTextView = findViewById(R.id.user_text)
        selectedTextView = findViewById(R.id.selected_user_name)
        chooseUserButton = findViewById(R.id.choose_user_button)

        backButton.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        val name = intent.getStringExtra(EXTRA_NAME)
        val selected = intent.getStringExtra(EXTRA_SELECTED)

        userTextView.text = name
        selectedTextView.text = selected

        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putExtra(ThirdActivity.EXTRA_NAME, name)
            Log.d("Emily", "s: " + name)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_NAME = "User"
        const val EXTRA_SELECTED = "Selected User Name"
    }
}

