package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.infra.EmojiNormalizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.Normalizer
import kotlin.collections.iterator

class GithubPageParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend fun parse(
        language: String,
        emojis: List<Emoji>,
        localizationMap: Map<String, Emoji.Localization>,
    ) : JSONObject = withContext(dispatcher) {
        val array = JSONArray().apply {
            var notFound = 0
            emojis.forEach { emoji ->
                val localizationJson = localizationMap.toJson(emoji.unicode)
                val value = if (localizationJson != null) {
                    localizationJson
                } else {
                    notFound++
                    JSONObject.NULL
                }
                val emojiJson = emoji.toJson()
                emojiJson.put(
                    "localization",
                    value
                )
                put(emojiJson)

            }
            println("Not found count: $notFound")
        }
        JSONObject().apply {
            put("language", language)
            put("emojis", array)
        }
    }

    private fun Emoji.toJson(): JSONObject = JSONObject().apply {
        put("unicode", unicode)
        put("name", name)
        put("group", group)
        put("subgroup", subgroup)
        put("codePoint", codePoint)
    }

    private fun Map<String, Emoji.Localization>.toJson(unicode: String): JSONObject? {
        val key = EmojiNormalizer.normalizedUnicode(unicode)
        val localization = this[key]
        if (localization == null) {
            println("Localization not found for key: $key -- $unicode")
            return null
        }
        return JSONObject().apply {
            put("name", localization.name)
            put("tags", JSONArray().apply {
                localization.tags.forEach { tag ->
                    put(tag)
                }
            })
        }
    }

    private fun normalizeEmojiPresentation(input: String): String {
        val builder = StringBuilder()
        var i = 0
        while (i < input.length) {
            val cp = input.codePointAt(i)
            builder.appendCodePoint(cp)

            if (!input.hasNextFE0F(i, cp)) {
                builder.appendCodePoint(0xFE0F)
            }

            i += Character.charCount(cp)
        }
        return builder.toString()
    }

    private fun String.hasNextFE0F(index: Int, cp: Int): Boolean {
        val nextIndex = index + Character.charCount(cp)
        if (nextIndex >= this.length) return false
        return this.codePointAt(nextIndex) == 0xFE0F
    }

}