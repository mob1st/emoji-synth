package com.mob1st.emoji.infra

import java.text.Normalizer

object EmojiNormalizer {

    private val variantSelectorRegex = "[\uFE0E\uFE0F]".toRegex()
    private val skinToneModifierRegex = "[\uD83C\uDFFB-\uD83C\uDFFF]".toRegex()

    fun normalizedUnicode(emoji: String): String {
        val normalized = Normalizer.normalize(emoji, Normalizer.Form.NFC)
        val noVs = normalized.replace(variantSelectorRegex, "")
        return noVs.replace(skinToneModifierRegex, "")
    }

}