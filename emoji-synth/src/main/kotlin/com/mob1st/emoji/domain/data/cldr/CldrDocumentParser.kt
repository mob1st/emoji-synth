package com.mob1st.emoji.domain.data.cldr

import com.mob1st.emoji.domain.entities.Emoji
import com.mob1st.emoji.infra.EmojiNormalizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.text.Normalizer

class CldrDocumentParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend fun parse(
        document: Document
    ): Map<String, Emoji.Localization> = withContext(dispatcher) {
        val annotations = document.getElementsByTagName("annotation")

        val result = mutableMapOf<String, Emoji.Localization>()

        for (i in 0 until annotations.length) {
            val node = annotations.item(i)

            val cp = node.attributes?.getNamedItem("cp")?.nodeValue ?: continue
            val type = node.attributes?.getNamedItem("type")?.nodeValue

            val key = EmojiNormalizer.normalizedUnicode(cp)
            val temp = result.getOrDefault(
                key,
                Emoji.Localization()
            )
            val final = if (type == "tts") {
                temp.copy(name = node.getName())
            } else {
                temp.copy(tags = node.getTags())
            }
            result[key] = final
        }
        result
    }

    private fun Node.getName() = textContent.trim()
    private fun Node.getTags() = textContent
        .split('|')
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    private fun Document.getLanguage(): String {
        val nodes = getElementsByTagName("language")
        for (i in 0 until nodes.length) {
            val node = nodes.item(i)
            val typeAttr = node.attributes?.getNamedItem("type")?.nodeValue
            if (typeAttr != null) {
                return typeAttr
            }
        }
        error("Language not found")
    }
}