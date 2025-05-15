package com.mob1st.emoji

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    println("---Starting project---")

    coroutineScope {
        val responses = awaitAll(
            async {
                val text = loadEmojiText()
                parse(text)
            }
        )
        responses.first().forEach { group ->
            println("group: ${group.name}")
            group.subgroups.forEach { subgroup ->
                println("subgroup: ${subgroup.name}")
                subgroup.emojis.forEach { emoji ->
                    println("emoji: ${emoji.codePoint} ${emoji.unicode} ${emoji.name}")
                }
            }
            println("----\n")
        }
    }

    // load data from:
    // https://unicode.org/Public/emoji/latest/emoji-test.txt

    // load data from
    // https://raw.githubusercontent.com/unicode-org/cldr/refs/heads/main/common/annotations/en.xml

    println("---")
}