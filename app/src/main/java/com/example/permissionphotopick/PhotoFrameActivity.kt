package com.example.permissionphotopick

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {
private var currentPosition =0
    private val photoList = mutableListOf<Uri>()
private  var timer:Timer? = null
private val photoImageView : ImageView by lazy {
    findViewById<ImageView>(R.id.photoImageView)
}
    private val backgroundPhotoImageView:ImageView by lazy {
        findViewById<ImageView>(R.id.backgroundPhotoImageView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phote_frame)
        getPhotoUriFromIntent()

    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }
private fun startTimer(){
    timer(period = 1000*5){
        runOnUiThread{
            val current=currentPosition
            val next = if (photoList.size<=currentPosition+1) 0 else currentPosition +1
            backgroundPhotoImageView.setImageURI(photoList[current])
            photoImageView.alpha = 0f //투명도가 0 => 안보임
        photoImageView.setImageURI(photoList[next])
            photoImageView.animate()
                .alpha(1.0f)
                .setDuration(1000)
                .start()

            currentPosition = next
        }

    }
//
}

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
    startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
    timer?.cancel()
    }
}