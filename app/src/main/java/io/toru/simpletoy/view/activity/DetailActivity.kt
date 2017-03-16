package io.toru.simpletoy.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import io.toru.simpletoy.R

class DetailActivity : AppCompatActivity() {

    val textView: TextView by lazy{
        findViewById(R.id.txt_name) as TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        textView.text = intent.getStringExtra("Name")
    }
}
