// AlarmActivity.kt
package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class AlarmActivity : AppCompatActivity() {
    private var alarmManager: AlarmManager? = null
    private var mCalendar: GregorianCalendar? = null
    private var notificationManager: NotificationManager? = null

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mCalendar = GregorianCalendar()
        Log.v("HelloAlarmActivity", mCalendar!!.time.toString())
        setContentView(R.layout.activity_alarm)

        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val button = findViewById<Button>(R.id.btnNoti)

        button.setOnClickListener {
            val hour = timePicker.currentHour
            val minute = timePicker.currentMinute
            val userTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
            setAlarm(userTime)
        }
    }

    // AlarmActivity.kt
    private fun setAlarm(userTime: String) {
        val receiverIntent = Intent(this@AlarmActivity, AlarmReceiver::class.java)

        // 시간과 분 데이터를 인텐트에 추가
        val timeArray = userTime.split(":")
        val hour = timeArray[0].toInt()
        val minute = timeArray[1].toInt()
        receiverIntent.putExtra("hour", hour)
        receiverIntent.putExtra("minute", minute)
        receiverIntent.putExtra("alarm_message", "약 먹을 시간입니다.")

        val pendingIntent = PendingIntent.getBroadcast(
            this@AlarmActivity,
            0,
            receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val currentTime = Calendar.getInstance()
        val calendar = Calendar.getInstance()

        // 현재 시간보다 이전이면 내일 같은 시간으로 설정
        if (currentTime.get(Calendar.HOUR_OF_DAY) > hour ||
            (currentTime.get(Calendar.HOUR_OF_DAY) == hour && currentTime.get(Calendar.MINUTE) >= minute)
        ) {
            calendar.add(Calendar.DATE, 1)
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // 사용자가 입력한 시간으로 알람 설정
        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show()
    }
}