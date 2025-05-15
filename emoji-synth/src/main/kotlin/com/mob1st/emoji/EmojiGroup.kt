package com.mob1st.emoji

data class EmojiGroup(
    val name: String,
    val subgroups: List<SubGroup>,
) {
    data class SubGroup(
        val name: String,
        val emojis: List<EmojiDto>,
    )
}

data class EmojiDto(
    val codePoint: String,
    val unicode: String,
    val name: String,
)