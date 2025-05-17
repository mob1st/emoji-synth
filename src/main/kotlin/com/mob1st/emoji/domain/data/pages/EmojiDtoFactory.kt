package com.mob1st.emoji.domain.data.pages

import com.mob1st.emoji.domain.data.countries.CountryNameProvider
import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode
import com.mob1st.emoji.infra.log.logger

object EmojiDtoFactory {
    fun create(
        language: String,
        emojis: List<Emoji>,
        localizationMap: Map<NormalizedUnicode, EmojiLocalization>
    ): List<EmojiDto> {
        var foundCount = 0
        var notFoundCount = 0
        val dtos = mutableListOf<EmojiDto>()
        emojis.forEach {
            val key = NormalizedUnicode.create(it.unicode)
            val localization = localizationMap.getOrElse(key) {
                CountryNameProvider.getCountryName(
                    language,
                    it.name
                )
            }
            if (localization != null) {
                foundCount++
                dtos.add(it.toEmojiDto(localization))
            } else {
                logger.debug(
                    "Emoji not found in localization map. unicode: ${it.unicode} - key: ${key.value} - name: ${it.name}"
                )
                notFoundCount++
            }
        }
        logger.info("Found count $foundCount")
        logger.info("Not found count $notFoundCount")
        return dtos
    }

    private fun Emoji.toEmojiDto(localization: EmojiLocalization): EmojiDto {
        return EmojiDto(
            codePoints = codePoints,
            unicode = unicode,
            group = group,
            subgroup = subgroup,
            name = name,
            localizationDto = LocalizationDto(
                name = localization.name,
                tags = localization.tags
            )
        )
    }
}