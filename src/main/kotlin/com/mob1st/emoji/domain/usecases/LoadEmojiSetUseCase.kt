package com.mob1st.emoji.domain.usecases

import com.mob1st.emoji.domain.data.cldr.CldrApi
import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.domain.data.pages.GithubPagesApi
import com.mob1st.emoji.domain.data.unicode.UnicodeEmojiApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class LoadEmojiSetUseCase(
    private val unicodeEmojiApi: UnicodeEmojiApi = UnicodeEmojiApi(),
    private val cldrApi: CldrApi = CldrApi(),
    private val githubPagesApi: GithubPagesApi = GithubPagesApi(),
) {

    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(
        language: String
    ) = coroutineScope {
        val responses = awaitAll(
            async { unicodeEmojiApi.getAll() },
            async { cldrApi.getLocalizations(language) }
        )
        val emojis = responses[0] as List<Emoji>
        val localizationMap = responses[1] as Map<String, Emoji.Localization>
        githubPagesApi.save(
            language = language,
            emojis = emojis,
            localizationMap = localizationMap
        )
    }


}