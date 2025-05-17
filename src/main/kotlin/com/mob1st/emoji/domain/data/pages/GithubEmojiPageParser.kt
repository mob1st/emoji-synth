package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.infra.json.globalJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class GithubEmojiPageParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val json: Json = globalJson
) {

    suspend fun parse(
        emojis: List<Emoji>,
    ) : String = withContext(dispatcher) {
        json.encodeToString(emojis)
    }

}