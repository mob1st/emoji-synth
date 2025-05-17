package com.mob1st.emoji.domain.data.pages

import kotlinx.serialization.Serializable

@Serializable
data class EmojiList(
    val language: String,
    val emojis: List<EmojiDto>,
)

@Serializable
data class EmojiDto(
    val codePoints: String,
    val unicode: String,
    val group: String,
    val subgroup: String,
    val name: String,
)

@Serializable
data class LocalizationDto(
    val name: String,
    val tags: List<String>,
)