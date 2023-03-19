package com.example.a111111

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class Z_Acting(context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)

    fun getCurrentUser(): String? {
        val currentUser = sharedPreferences.getString("username", "")
        return currentUser
    }
}
