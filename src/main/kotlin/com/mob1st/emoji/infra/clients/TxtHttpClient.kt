package com.mob1st.emoji.infra.clients

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI

class TxtHttpClient(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun get(url: String): List<String> {
        return withContext(dispatcher) {
            URI(url)
                .toURL()
                .openStream()
                .bufferedReader()
                .useLines { lines ->
                    lines.toList()
                }
        }
    }
}