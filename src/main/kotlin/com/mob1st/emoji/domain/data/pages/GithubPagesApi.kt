package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode
import com.mob1st.emoji.infra.json.JsonFileWriter

class GithubPagesApi(
    private val emojiParser: GithubEmojiPageParser = GithubEmojiPageParser(),
    private val localizationParser: GithubEmojiLocalizationParser = GithubEmojiLocalizationParser(),
    private val jsonFileWriter: JsonFileWriter = JsonFileWriter(),
) {
    suspend fun saveEmojis(
        emojis: List<Emoji>,
    ) {
        val json = emojiParser.parse(
            emojis = emojis,
        )
        jsonFileWriter.write(
            filePath = "docs/emojis.json",
            jsonElement = json
        )
    }

    suspend fun saveTranslations(
        translations: Map<NormalizedUnicode, EmojiLocalization>,
    ) {
        val json = localizationParser.parse(
            translations = translations,
        )
        jsonFileWriter.write(
            filePath = "docs/localizations.json",
            jsonElement = json
        )
    }
}