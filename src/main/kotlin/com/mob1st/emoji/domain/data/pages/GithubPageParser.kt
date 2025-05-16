package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.infra.EmojiNormalizer
import com.mob1st.emoji.infra.json.globalJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

class GithubPageParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val json: Json = globalJson
) {

    suspend fun parse(
        language: String,
        emojis: List<Emoji>,
        localizationMap: Map<String, Emoji.Localization>,
    ) : JsonElement = withContext(dispatcher) {
        var notFound = 0
        val dtos = emojis.map { emoji ->
            val localization = localizationMap.find(emoji.unicode)
            if (localization == null) {
                notFound++
            }
            EmojiDto(
                unicode = emoji.unicode,
                name = emoji.name,
                group = emoji.group,
                subgroup = emoji.subgroup,
                codePoint = emoji.codePoint,
                localization = localization?.let {
                    LocalizationDto(
                        name = it.name,
                        tags = it.tags
                    )
                }
            )
        }
        if (notFound > 0) {
            println("Not found localizations: $notFound")
        }
        val emojiList = EmojiList(
            language = language,
            emojis = dtos
        )
        json.encodeToJsonElement(emojiList)
    }

    private fun Map<String, Emoji.Localization>.find(unicode: String): Emoji.Localization? {
        val key = EmojiNormalizer.normalizedUnicode(unicode)
        val localization = this[key]
        if (localization == null) {
            println("Localization not found for key: $key -- $unicode")
        }
        return localization
    }

}