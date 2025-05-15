package com.mob1st.emoji.domain.data.unicode

import com.mob1st.emoji.domain.entities.Emoji
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmojiDtoParser(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun parse(lines: List<String>): List<Emoji> = withContext(coroutineDispatcher) {
        // Implement the parsing logic here
        var currentGroup = ""
        var currentSubgroup = ""
        val emojis = mutableListOf<Emoji>()
        lines
            .forEach { line ->
                when {
                    line.startsWith("# group") -> {
                        currentGroup = line.splitGrouper()
                    }
                    line.startsWith("# subgroup") -> {
                        currentSubgroup = line.splitGrouper()
                    }
                    line.contains("; fully-qualified") -> {
                        val parts = line.split(regex)
                        val dto = Emoji(
                            group = currentGroup.trim(),
                            subgroup = currentSubgroup.trim(),
                            codePoint = parts[0].trim(),
                            unicode = parts[2].trim(),
                            name = parts[3].trim(),
                        )
                        emojis.add(dto)
                    }
                    else -> {}
                }
            }
        emojis
    }

    private fun String.splitGrouper() = split(":").last()

    companion object {
        private val regex = Regex("""\s*;\s*|\s+#\s+|\s+E\d+\.\d+\s+""")
    }
}