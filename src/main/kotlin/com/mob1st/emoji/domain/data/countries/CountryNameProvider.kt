package com.mob1st.emoji.domain.data.countries

import com.mob1st.emoji.domain.entities.EmojiLocalization
import java.util.Locale

object CountryNameProvider {

    fun isCountryFlag(emoji: String): Boolean {
        val codePoints = emoji.codePoints().toArray()
        return codePoints.size == 2 &&
                codePoints.all { it in 0x1F1E6..0x1F1FF }
    }

    fun getCountryName(
        language: String,
        englishName: String
    ): EmojiLocalization? {
        val parts = englishName.split(":")
        if (parts.first() != "flag") {
            return null
        }
        val name = parts.last().trim()
        return if (language == "en") {
            EmojiLocalization(
                name = name,
                tags = emptyList()
            )
        } else {
            getCountryNameInLanguage(
                language,
                name
            )?.let { localizedName ->
                EmojiLocalization(
                    name = localizedName,
                    tags = emptyList()
                )
            }
        }
    }


    private fun getCountryNameInLanguage(
        targetLang: String,
        englishName: String
    ): String? {
        val isoCode = Locale.getISOCountries().find { code ->
            val countryLocale = Locale.Builder().setRegion(code).build()
            countryLocale.getDisplayCountry(Locale.ENGLISH).equals(englishName, ignoreCase = true)
        } ?: return null

        val targetLocale = Locale.Builder().setLanguage(targetLang).build()
        val localizedCountry = Locale.Builder().setRegion(isoCode).build()
        return localizedCountry.getDisplayCountry(targetLocale)
    }
}