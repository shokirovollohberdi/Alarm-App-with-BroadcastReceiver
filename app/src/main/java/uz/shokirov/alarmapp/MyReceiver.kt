package uz.shokirov.alarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.Log
import uz.shokirov.utils.AlarmCash
import java.text.SimpleDateFormat
import java.util.*

class MyReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        AlarmCash.init(context)
        val list = AlarmCash.alarmList

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val hh = SimpleDateFormat("HH").format(calendar.time).toInt()
        val mm = SimpleDateFormat("mm").format(calendar.time).toInt()
        val mediaPlayer = MediaPlayer.create(context, R.raw.sos_music)
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        for (alarms in list) {
            if (hh == alarms.hour && mm == alarms.min && alarms.isRun) {
                var handler = Handler(Looper.getMainLooper())
                val runnable = Runnable {
                    while (true) {
                        Thread.sleep(500)
                        v.vibrate(500)
                        if (!mediaPlayer.isPlaying) {
                            break
                        }
                    }
                }
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.start()
                } else {
                    mediaPlayer.start()
                }
                if (alarms.vibration) {
                    v.vibrate(500)
                    handler.postDelayed(runnable, 100)
                }
            }
        }
        Log.d("run", "mediPlayer")
    }
}