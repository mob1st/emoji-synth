package com.mob1st.emoji.domain.entities

import com.mob1st.emoji.infra.emoji.EmojiNormalizer
import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val codePoints: String = "",
    val unicode: String = "",
    val group: String = "",
    val subgroup: String = "",
    val name: String = ""
)

@Serializable
data class EmojiLocalization(
    val name: String = "",
    val tags: List<String> = emptyList()
)

@Serializable
@JvmInline
value class NormalizedUnicode private constructor(
    val value: String
) {

    companion object {
        fun create(value: String): NormalizedUnicode {
            return NormalizedUnicode(
                EmojiNormalizer.normalizedUnicode(value)
            )
        }
    }
}