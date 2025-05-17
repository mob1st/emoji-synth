package com.mob1st.emoji.domain.data.unicode


object Variations {
    val skinToneRange = 0x1F3FB..0x1F3FF
    val genderSymbols = setOf(0x2640, 0x2642)
    val genderPeople = setOf(0x1F9D1, 0x1F468, 0x1F469)
    /*
    val zeroWidthJoiner = 0x200D
    val variationSelector16 = 0xFE0F
    val tagRange = 0xE0020..0xE007E
    val cancelTag = 0xE007F
     */

    fun shouldIncludeLine(line: String): Boolean {
        if (!line.contains("; fully-qualified")) return false

        val codePoints = line
            .substringBefore(";")
            .trim()
            .split(" ")
            .mapNotNull { it.toIntOrNull(16) }
        val firstCp = codePoints.first()
        val hasOnlyOneCp = codePoints.size == 1
        val isOriginalGenderSymbol = firstCp in genderSymbols && hasOnlyOneCp
        val isOriginalGenderPeople = firstCp in genderPeople && hasOnlyOneCp
        val isNoVariant = codePoints.none {
            it in skinToneRange ||
            it in genderSymbols ||
            it in genderPeople
        }
        return isOriginalGenderPeople || isOriginalGenderSymbol || isNoVariant
    }
}