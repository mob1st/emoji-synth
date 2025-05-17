package com.mob1st.emoji.infra.emoji

import java.text.Normalizer

object EmojiNormalizer {

    private val variantSelectorRegex = "[\uFE0E\uFE0F]".toRegex()

    fun normalizedUnicode(emoji: String): String {
        val normalized = Normalizer.normalize(emoji, Normalizer.Form.NFC)
        return normalized.replace(variantSelectorRegex, "")
    }

}