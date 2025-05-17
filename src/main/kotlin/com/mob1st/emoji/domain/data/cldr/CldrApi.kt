package com.mob1st.emoji.domain.data.cldr

import com.mob1st.emoji.infra.clients.XmlHttpClient
import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode

class CldrApi(
    private val httpClient: XmlHttpClient = XmlHttpClient(),
    private val parser: CldrDocumentParser = CldrDocumentParser(),
) {

    suspend fun getLocalizations(language: String): Map<NormalizedUnicode, EmojiLocalization> {
        val document = httpClient.get("$URL/$language.xml")
        return parser.parse(document)
    }

    companion object {
        private const val URL =
            "https://raw.githubusercontent.com/unicode-org/cldr/refs/heads/main/common/annotations"
    }

}

