package com.mob1st.emoji.cli

import com.mob1st.emoji.domain.usecases.LoadEmojiSetUseCase
import com.mob1st.emoji.infra.log.logger
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("UNCHECKED_CAST")
@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")
    logger.info("Starting CLI")
    val useCase = LoadEmojiSetUseCase()
    //useCase("en")
    useCase("en")
    logger.info("Finished CLI")
}