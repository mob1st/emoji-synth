package com.mob1st.emoji.domain.usecases

import com.mob1st.emoji.domain.data.cldr.CldrApi
import com.mob1st.emoji.domain.data.pages.GithubPagesApi
import com.mob1st.emoji.domain.data.unicode.UnicodeEmojiApi
import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode
import com.mob1st.emoji.infra.emoji.EmojiNormalizer
import com.mob1st.emoji.infra.log.logger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.math.log

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
        val localizationMap = responses[1] as Map<NormalizedUnicode, EmojiLocalization>
        githubPagesApi.saveEmojis(
            emojis = emojis,
        )
        val finalEmojiMap = mutableMapOf<String, EmojiLocalization>()
        var notFoundCount = 0
        emojis.forEach {
            val key = NormalizedUnicode.create(it.unicode)
            val localization = localizationMap[key]
            if (localization != null) {
                 finalEmojiMap[it.unicode] = localization
            } else {
                logger.debug(
                    "Emoji not found in localization map. unicode: ${it.unicode} - key: ${key.value} - name: ${it.name}"
                )
                notFoundCount++
            }
        }
        logger.info("Not found count $notFoundCount")

        githubPagesApi.saveTranslations(
            translations = localizationMap,
        )
    }


}