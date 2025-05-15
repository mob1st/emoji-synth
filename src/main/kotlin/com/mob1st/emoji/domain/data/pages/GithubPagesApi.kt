package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.infra.json.JsonFileWriter

class GithubPagesApi(
    private val parser: GithubPageParser = GithubPageParser(),
    private val jsonFileWriter: JsonFileWriter = JsonFileWriter(),
) {
    suspend fun save(
        language: String,
        emojis: List<Emoji>,
        localizationMap: Map<String, Emoji.Localization>,
    ) {
        val json = parser.parse(
            language = language,
            emojis = emojis,
            localizationMap = localizationMap
        )
        jsonFileWriter.write(
            filePath = "docs/emojis_$language.json",
            json = json
        )
    }
}