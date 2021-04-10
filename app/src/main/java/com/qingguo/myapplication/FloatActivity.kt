package com.qingguo.myapplication

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.JzvdStd
import org.jzvd.jzvideo.JZVideoA

class FloatActivity : AppCompatActivity() {

    private lateinit var player:JzvdStd
    private lateinit var bt:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.player_layout)
        enterPictureInPictureMode()

        player=findViewById<JzvdStd>(R.id.jz_video)
        val url = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
        player.setUp(url,"")
        bt= findViewById(R.id.bt_click)
        bt.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    enterPictureInPictureMode()
                }
            }

        })
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if(isInPictureInPictureMode){
            //画中画模式
            bt.visibility=View.GONE
            player.startButton.performClick()

        }else{
            //
            finish()
        }

    }


}