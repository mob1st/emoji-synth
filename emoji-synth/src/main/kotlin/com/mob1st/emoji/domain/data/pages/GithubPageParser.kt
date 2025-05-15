package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
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
                val emojiJson = emoji.toJson()
                val localizationJson = localizationMap.toJson(emoji.unicode)
                val value = if (localizationJson != null) {
                    localizationJson
                } else {
                    notFound++
                    JSONObject.NULL
                }
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
        val key = Normalizer.normalize(unicode, Normalizer.Form.NFC)
        val localization = this[key]
        return if (localization == null) {
            println("No localization found for $unicode")
            return null
        } else JSONObject().apply {
            put("name", localization.name)
            put("tags", JSONArray().apply {
                localization.tags.forEach { tag ->
                    put(tag)
                }
            })
        }
    }

}