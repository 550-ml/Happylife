package com.example.a111111
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class WT_BaseActivity : AppCompatActivity() {
    lateinit var receiver: ForceOfflineReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WT_ActivityCollector.addActivity(this)
    }
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    private fun registerReceiver(receiver: WT_BaseActivity.ForceOfflineReceiver, intentFilter: IntentFilter) {

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private fun unregisterReceiver(receiver: WT_BaseActivity.ForceOfflineReceiver) {

    }

    override fun onDestroy() {
        super.onDestroy()
        WT_ActivityCollector.removeActivity(this)
    }
    inner class ForceOfflineReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")

                setMessage("You are forced to be offline. Please try to login again.")
                setCancelable(false)
                setPositiveButton("OK") { _, _ ->
                    WT_ActivityCollector.finishAll() // 销毁所有Activity
                    val i = Intent(context, WT_LoginActivity::class.java)
                    context.startActivity(i) // 重新启动LoginActivity
                }
                show()
            }
        }
    }
}
