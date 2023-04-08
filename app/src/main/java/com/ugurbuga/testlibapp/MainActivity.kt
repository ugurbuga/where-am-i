package com.ugurbuga.testlibapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ugurbuga.testlib.TestFile

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestFile.test()
    }
}