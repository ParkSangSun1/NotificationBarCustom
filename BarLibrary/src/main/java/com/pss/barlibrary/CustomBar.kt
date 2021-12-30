package com.pss.barlibrary

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

class CustomBar {
    companion object {


        /**
         * Notification bar Transparent status processing
         * 알림 bar 투명상태 처리
         */
        fun setTransparentBar(a: Activity) = checkSdkVersion(a)

        private fun checkSdkVersion(a: Activity) = with(Build.VERSION.SDK_INT) {
            if (this in 19..20) setTransparentBarNotificationBar(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                true,
                a
            )
            if (this >= 19) a.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            if (this >= 21) {
                setTransparentBarNotificationBar(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    false,
                    a
                )
                a.window.statusBarColor = Color.TRANSPARENT
            }
        }

        private fun setTransparentBarNotificationBar(lp: Int, b: Boolean, a: Activity) {
            val win = a.window
            val winParams = win.attributes
            if (b) winParams.flags = winParams.flags or lp
            else winParams.flags = winParams.flags and lp.inv()
            win.attributes = winParams
        }
    }
}