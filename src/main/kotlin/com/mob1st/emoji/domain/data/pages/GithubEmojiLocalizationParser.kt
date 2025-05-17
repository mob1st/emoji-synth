package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode
import com.mob1st.emoji.infra.json.globalJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class GithubEmojiLocalizationParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val json: Json = globalJson
) {

    suspend fun parse(
        translations: Map<NormalizedUnicode, EmojiLocalization>,
    ): String = withContext(dispatcher) {
        json.encodeToString(translations)
    }

}