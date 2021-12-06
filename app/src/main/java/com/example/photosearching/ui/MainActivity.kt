package com.example.photosearching.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.photosearching.R
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint annotation'ı ile var activityde Hilt'i etkinleştirin.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}