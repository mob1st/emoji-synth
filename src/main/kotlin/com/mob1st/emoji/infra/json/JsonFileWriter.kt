package com.mob1st.emoji.infra.json

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class JsonFileWriter(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun write(
        filePath: String,
        json: JSONObject,
    ) = withContext(dispatcher) {
        File(filePath)
            .writeText(
                json.toString(2)
            )
    }
}