package com.mob1st.emoji.cli

import com.mob1st.emoji.domain.usecases.LoadEmojiSetUseCase
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("UNCHECKED_CAST")
@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    println("---Starting project---")
    val useCase = LoadEmojiSetUseCase()
    useCase("en")
    println("---")
}