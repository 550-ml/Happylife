package com.example.a111111

class Z_Card(
    val id: Int,
    val title: String,
    val content: String,
    var hasButtons: Boolean,
    var onYesClickListener: (() -> Unit)? = null,
    var onNoClickListener: (() -> Unit)? = null
) {
    var showButtons: Boolean = true
        private set

    fun setShowButtons(show: Boolean) {
        showButtons = show
    }

    companion object {
        fun create(
            id: Int,
            title: String,
            content: String,
            hasButtons: Boolean,
            onYesClickListener: (() -> Unit)? = null,
            onNoClickListener: (() -> Unit)? = null
        ) = Z_Card(
            id,
            title,
            content,
            hasButtons,
            onYesClickListener,
            onNoClickListener
        )
    }
}
