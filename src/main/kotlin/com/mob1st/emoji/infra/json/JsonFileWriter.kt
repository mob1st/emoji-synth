package com.mob1st.emoji.infra.json

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.File

class JsonFileWriter(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val json: Json = globalJson
) {
    suspend fun write(
        filePath: String,
        jsonElement: JsonElement,
    ) = withContext(dispatcher) {
        File(filePath)
            .writeText(
                json.encodeToString(jsonElement)
            )
    }
}