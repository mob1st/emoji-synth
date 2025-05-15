package com.mob1st.emoji.domain.data.unicode

import com.mob1st.emoji.infra.clients.TxtHttpClient
import com.mob1st.emoji.domain.entities.Emoji

class UnicodeEmojiApi(
    private val httpClient: TxtHttpClient = TxtHttpClient(),
    private val parser: EmojiDtoParser = EmojiDtoParser(),
) {

    suspend fun getAll(): List<Emoji>  {
        val lines = httpClient.get(EMOJI_TEST)
        return parser.parse(lines)
    }

    companion object {
        private const val EMOJI_TEST = "https://unicode.org/Public/emoji/latest/emoji-test.txt"
    }
}
