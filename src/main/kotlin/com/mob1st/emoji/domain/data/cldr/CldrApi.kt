package com.mob1st.emoji.domain.data.cldr

import com.mob1st.emoji.infra.clients.XmlHttpClient
import com.mob1st.emoji.domain.entities.Emoji

class CldrApi(
    private val httpClient: XmlHttpClient = XmlHttpClient(),
    private val parser: CldrDocumentParser = CldrDocumentParser(),
) {

    suspend fun getLocalizations(locale: String): Map<String, Emoji.Localization> {
        val document = httpClient.get("$URL/$locale.xml")
        return parser.parse(document)
    }

    companion object {
        private const val URL =
            "https://raw.githubusercontent.com/unicode-org/cldr/refs/heads/main/common/annotations"
    }

}

