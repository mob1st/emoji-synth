package com.mob1st.emoji

private val regex = Regex("""\s*;\s*|\s+#\s+|\s+E\d+\.\d+\s+""")

fun parse(lines: List<String>): List<EmojiGroup> {
    val groups = mutableListOf<EmojiGroup>()
    var currentGroupName: String? = null

    val subgroups = mutableListOf<EmojiGroup.SubGroup>()
    var currentSubGroupName: String? = null

    val emojis = mutableListOf<EmojiDto>()
    val filtered = lines.dropWhile { !it.startsWith("# group") }
    filtered.forEach { line ->
        if (line.startsWith("# group")) {
            currentGroupName?.let {
                if (subgroups.isNotEmpty()) {
                    val group = EmojiGroup(
                        name = it,
                        subgroups = subgroups.toList()
                    )
                    groups.add(group)
                }
            }
            subgroups.clear()
            val columns = line.split(":")
            currentGroupName = columns.last().trim()
        }
        if (line.startsWith("# subgroup")) {
            currentSubGroupName?.let {
                if (emojis.isNotEmpty()) {
                    val subgroup = EmojiGroup.SubGroup(
                        name = it,
                        emojis = emojis.toList()
                    )
                    subgroups.add(subgroup)
                }
            }
            emojis.clear()
            val columns = line.split(":")
            currentSubGroupName = columns.last().trim()
        }
        if (line.contains("; fully-qualified")) {
            val parts = line.split(regex)
            emojis.add(
                EmojiDto(
                    codePoint = parts[0].trim(),
                    unicode = parts[2].trim(),
                    name = parts[3].trim()
                )
            )
        }
    }
    return groups
}