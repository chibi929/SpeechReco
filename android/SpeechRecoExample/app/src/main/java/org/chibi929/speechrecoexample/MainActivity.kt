package org.chibi929.speechrecoexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.speech.RecognizerIntent
import android.content.Intent
import android.app.Activity

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE: Int = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            try {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "VoiceRecognitionTest")
                startActivityForResult(intent, REQUEST_CODE)
            } catch (e: ActivityNotFoundException) {
                // このインテントに応答できるアクティビティがインストールされていない場合
                Toast.makeText(this, "ActivityNotFoundException", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === REQUEST_CODE && resultCode === Activity.RESULT_OK) {
            var resultsString = ""

            // 結果文字列リスト
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            results?.forEach {
                resultsString += it
            }

            // トーストを使って結果を表示
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
