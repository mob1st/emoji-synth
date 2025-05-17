package com.mob1st.emoji.infra.json

import kotlinx.serialization.json.Json

val globalJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    prettyPrint = false
    encodeDefaults = false
}