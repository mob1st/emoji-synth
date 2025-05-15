package com.mob1st.emoji

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

private const val EMOJI_TEST = "https://unicode.org/Public/emoji/latest/emoji-test.txt"

/**
 * Load the emoji tree data set from [EMOJI_TEST]
 */
suspend fun loadEmojiText(): List<String> {
    val stream = withContext(Dispatchers.IO) {
        // Load the emoji test data from the URL
        val uri = URI(EMOJI_TEST)
        uri.toURL().openStream()
    }

    return withContext(Dispatchers.Default) {
        stream
            .bufferedReader()
            .useLines { lines -> lines.toList() }
    }
}
