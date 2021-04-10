package com.qingguo.myapplication

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import cn.jzvd.JzvdStd
import java.sql.BatchUpdateException

class FloatingService : Service() {

    val TAG = "FloatingService"
    private lateinit var manager: WindowManager
    private lateinit var button: Button
    private lateinit var view: View
    private lateinit var player: JzvdStd
    private lateinit var layoutParams: WindowManager.LayoutParams
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showFloatingWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    @SuppressLint("ClickableViewAccessibility")
    fun showFloatingWindow() {

        val url = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";
        if (Settings.canDrawOverlays(this)) {
            manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager


             player = JzvdStd(applicationContext)
            view = LayoutInflater.from(applicationContext)
                .inflate(R.layout.player_demo_layout, null, false)
            button = Button(applicationContext)
            button.text = "this is floating"
//            val player:JzvdStd=view.findViewById(R.id.jz_video)
            button.setBackgroundColor(Color.BLUE)
            button.isFocusableInTouchMode = true
            layoutParams = WindowManager.LayoutParams()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
            }

//            layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            layoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            layoutParams.format = PixelFormat.RGBA_8888
            layoutParams.width = 300
            layoutParams.height = 300
            layoutParams.x = 300
            layoutParams.y = 300

            player.setUp(url, "")
            player.isFocusableInTouchMode = true
            player.setOnTouchListener(FloatingOnTouchListener())
            manager.addView(player, layoutParams)
            player.isFocusableInTouchMode = true

            player.setOnTouchListener(object :View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    Log.e(TAG,"playerplayerplayerplayer"+v?.x)
                    return true;
                }

            })


        }
    }

    private inner class FloatingOnTouchListener : View.OnTouchListener {

        var x = 0
        var y = 0
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {

            when (event?.action) {

                MotionEvent.ACTION_DOWN -> {

                    x = event.rawX.toInt()
                    y = event.rawY.toInt()

                }
                MotionEvent.ACTION_MOVE -> {

                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    Log.e(TAG, v.toString() + ";;x:" + x + ";y:" + y)
                    layoutParams.x = layoutParams.x + movedX
                    layoutParams.y = layoutParams.y + movedY

                    // 更新悬浮窗控件布局
                    manager.updateViewLayout(player, layoutParams)
                }
            }
            return false
        }

    }
}