package com.example.suitmediaapp.first

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediaapp.R
import com.example.suitmediaapp.second.SecondActivity

class FirstActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var sentenceInput: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        setUpClickListener()
    }

    private fun checkPalindrome(sentence: String): Boolean {
        val cleanedSentence = sentence.replace("\\s".toRegex(), "").lowercase()
        return cleanedSentence == cleanedSentence.reversed()
    }

    private fun setUpClickListener() {
        nameInput = findViewById(R.id.name_input)
        sentenceInput = findViewById(R.id.sentence_input)
        checkButton = findViewById(R.id.check_button)
        nextButton = findViewById(R.id.next_button)

        checkButton.setOnClickListener {
            val sentence = sentenceInput.text.toString()
            if (checkPalindrome(sentence)) {
                showDialog("Palindrome")
            } else {
                showDialog("Not Palindrome")
            }
        }

        nextButton.setOnClickListener {
            val name = nameInput.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.EXTRA_NAME, name)
            intent.putExtra(SecondActivity.EXTRA_SELECTED, "Selected User Name")
            startActivity(intent)
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
