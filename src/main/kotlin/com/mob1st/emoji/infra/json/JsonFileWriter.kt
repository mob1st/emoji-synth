package com.mob1st.emoji.infra.json

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class JsonFileWriter(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun write(
        filePath: String,
        jsonElement: String,
    ) = withContext(dispatcher) {
        File(filePath)
            .writeText(jsonElement)
    }
}