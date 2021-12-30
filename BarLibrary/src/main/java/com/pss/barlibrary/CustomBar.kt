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


        /**
         * Display the icon of the notification bar and the background in a contrasting color
         * 알림 bar의 아이콘과 백그라운드를 대비되는 색으로 표시
         */
        fun setContrastBar(a: Activity, color: Int) {
            var flags = a.window?.decorView?.systemUiVisibility
            if (flags != null) {
                if (checkColor(color)) {
                    flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    a.window?.decorView?.systemUiVisibility = flags
                } else {
                    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    a.window?.decorView?.systemUiVisibility = flags
                }
            }
            a.window?.statusBarColor = color
        }

        private fun checkColor(color: Int) =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255 >= 0.5
    }
}