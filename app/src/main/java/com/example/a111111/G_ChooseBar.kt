package com.example.a111111

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout

class G_ChooseBar(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs)  {
    init {
        LayoutInflater.from(context).inflate(R.layout.g_choose_bar, this)

        val TestSelectionButton: Button = findViewById(R.id.TestSelection_button)
        val AnalyzeButton: Button = findViewById(R.id.analyze_button)

        TestSelectionButton.setOnClickListener {
            val intent = Intent(context,G_TestChoose::class.java)
            context.startActivity(intent)
        }

        AnalyzeButton.setOnClickListener {
            val intent = Intent(context,G_Analyze::class.java)
            context.startActivity(intent)
        }
    }
}

