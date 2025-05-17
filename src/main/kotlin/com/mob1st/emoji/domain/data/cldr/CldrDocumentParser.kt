package com.mob1st.emoji.domain.data.cldr

import com.mob1st.emoji.domain.entities.EmojiLocalization
import com.mob1st.emoji.domain.entities.NormalizedUnicode
import com.mob1st.emoji.infra.emoji.EmojiNormalizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Node

class CldrDocumentParser(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    suspend fun parse(
        document: Document
    ): Map<NormalizedUnicode, EmojiLocalization> = withContext(dispatcher) {
        val annotations = document.getElementsByTagName("annotation")

        val result = mutableMapOf<NormalizedUnicode, EmojiLocalization>()

        for (i in 0 until annotations.length) {
            val node = annotations.item(i)

            val cp = node.attributes?.getNamedItem("cp")?.nodeValue ?: continue
            val type = node.attributes?.getNamedItem("type")?.nodeValue

            val key = NormalizedUnicode.create(cp)
            val temp = result.getOrDefault(
                key,
                EmojiLocalization()
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