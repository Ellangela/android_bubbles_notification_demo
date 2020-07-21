package com.example.bubbles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CHANNEL_ID = "1001"
        private const val CHANNEL_NAME = "通知渠道-名字"
        private const val NOTIFY_ID = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            createBubble()
        }
    }

    private fun createBubble() {
        createNotificationChannel()
        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, BubbleActivity::class.java), 0)
        val bubbleMetadata = NotificationCompat.BubbleMetadata.Builder()
                //气泡展开后高度
                .setDesiredHeight(600)
                .setIcon(IconCompat.createWithResource(this, R.drawable.ic_launcher_foreground))
                .setIntent(pendingIntent)
                .setAutoExpandBubble(false)
                .setSuppressNotification(true)
                .build()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("通知-标题")
                .setContentText("通知-正文:${Random.nextInt(100)}")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setBubbleMetadata(bubbleMetadata)
                .setAutoCancel(true)
                .build()
        NotificationManagerCompat.from(this).notify(NOTIFY_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "通知渠道-描述"
                setAllowBubbles(true)
            }.also {
                NotificationManagerCompat.from(this).createNotificationChannel(it)
            }
    }
}