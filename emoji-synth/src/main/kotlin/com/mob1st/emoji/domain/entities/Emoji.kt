package com.mob1st.emoji.domain.entities

data class Emoji(
    val codePoint: String = "",
    val unicode: String = "",
    val group: String = "",
    val subgroup: String = "",
    val name: String = ""
) {
    data class Localization(
        val name: String = "",
        val tags: List<String> = emptyList()
    )
}