// AlarmReceiver.kt
package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    private var manager: NotificationManager? = null
    private var builder: NotificationCompat.Builder? = null

    override fun onReceive(context: Context, intent: Intent) {
        val hour = intent.getIntExtra("hour", 0)
        val minute = intent.getIntExtra("minute", 0)
        val message = intent.getStringExtra("alarm_message")

        // 시간과 분을 사용하여 알림을 표시
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            manager!!.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }

        val notificationMessage = "약 먹을 시간입니다."
        val notification = builder?.setContentTitle("알람")?.setContentText(notificationMessage)
            ?.setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            ?.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))  // 진동 패턴 추가
            ?.build()

        manager?.notify(1, notification)
    }

    companion object {
        private const val CHANNEL_ID = "channel1"
        private const val CHANNEL_NAME = "Channel1"
    }
}
