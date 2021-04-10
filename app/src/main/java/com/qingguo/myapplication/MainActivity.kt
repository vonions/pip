package com.qingguo.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.JzvdStd


class MainActivity : AppCompatActivity() {

    val url = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.click).setOnClickListener { view ->
            startFloatingService(view)

        }



        val player: JzvdStd =findViewById(R.id.jz_video)

        player.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4","")
    }


    fun intentSetting() {

        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ), 111
        )
    }

    fun startFloatingService(view: View) {

        if (!Settings.canDrawOverlays(this)) {
            intentSetting()
        } else {

            Log.e("BANBEN","手机版本号为："+Build.VERSION.SDK_INT+"|||||"+Build.VERSION_CODES.N)
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

                //画中画

                val intent=Intent(this,FloatActivity::class.java)

                startActivity(intent)

            }else{
                //悬浮窗
                val floatView = FloatView(applicationContext, 0, 0)
                val jzvdStd=JzvdStd(this)

                jzvdStd.setUp(url,"")
                jzvdStd.startButton.performClick()

                floatView.addView(jzvdStd)
                floatView.addToWindow()
                //设置成关闭x 图标
                jzvdStd.fullscreenButton.setImageResource(R.mipmap.ic_colse)
                jzvdStd.fullscreenButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {

                        floatView.removeFromWindow()
                    }

                })
                floatView.setOnClickListener {
                Toast.makeText(this.applicationContext,"hhh",Toast.LENGTH_LONG).show()
                }
            }



        }

    }
}
