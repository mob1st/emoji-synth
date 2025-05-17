package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.infra.json.JsonFileWriter

class GithubPagesApi(
    private val emojiParser: GithubEmojiPageParser = GithubEmojiPageParser(),
    private val jsonFileWriter: JsonFileWriter = JsonFileWriter(),
) {

    suspend fun save(list: EmojiList) {
        jsonFileWriter.write(
            filePath = "docs/emojis_${list.language}.json",
            jsonElement = emojiParser.parse(list)
        )
    }
}