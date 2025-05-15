package com.mob1st.emoji.infra.clients

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import java.net.URI
import java.net.URLConnection
import javax.xml.parsers.DocumentBuilderFactory

class XmlHttpClient(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun get(url: String): Document = withContext(dispatcher) {
        val connection = connection(url)
        val stream = connection.getInputStream()
        val builderFactory = DocumentBuilderFactory.newInstance().apply {
            isValidating = false
            isNamespaceAware = true
            setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
        }
        val builder = builderFactory.newDocumentBuilder()
        builder.parse(stream).also {
            stream.close()
        }
    }

    private fun connection(url: String): URLConnection = URI(url)
        .toURL()
        .openConnection()
        .apply {
            connectTimeout = TIMEOUT
            readTimeout = TIMEOUT
        }

    companion object {
        private const val TIMEOUT = 10000
    }
}