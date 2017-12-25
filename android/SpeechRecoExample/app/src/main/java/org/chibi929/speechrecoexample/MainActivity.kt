package org.chibi929.speechrecoexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import android.speech.RecognizerIntent
import android.content.Intent
import android.app.Activity
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val _requestCode: Int = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recognizerIntentButton.setOnClickListener {
           val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
           intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
           intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "RecognizerIntent")
           startActivityForResult(intent, _requestCode)
        }

        speechRecognizerButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
            intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            val recognizer = SpeechRecognizer.createSpeechRecognizer(this)
            recognizer.setRecognitionListener(listener)
            recognizer.startListening(intent)
        }
    }

    private val listener = object: RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            Log.d("CHIBI", "onReadyForSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d("CHIBI", "onRmsChanged")
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            Log.d("CHIBI", "onBufferReceived")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            Log.d("CHIBI", "onPartialResults")
            results(partialResults, interimResultText)
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            Log.d("CHIBI", "onEvent")
        }

        override fun onBeginningOfSpeech() {
            Log.d("CHIBI", "onBeginningOfSpeech")
        }

        override fun onEndOfSpeech() {
            Log.d("CHIBI", "onEndOfSpeech")
        }

        override fun onError(error: Int) {
            Log.d("CHIBI", "onError: " + error)
        }

        override fun onResults(results: Bundle?) {
            Log.d("CHIBI", "onResult")
            results(results, resultText)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this._requestCode && resultCode == Activity.RESULT_OK) {
            results(data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun results(data: Bundle?, textView: TextView) {
        var resultsString = ""

        val results = data?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        results?.forEach {
            resultsString += it
        }

        textView.text = resultsString
    }

    private fun results(data: Intent?) {
        var resultsString = ""

        val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        results?.forEach {
            resultsString += it
        }

        resultText.text = resultsString
    }
}
