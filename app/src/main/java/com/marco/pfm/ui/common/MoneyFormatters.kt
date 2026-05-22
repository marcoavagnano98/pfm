package com.marco.pfm.ui.common

import kotlin.math.abs

fun formatMinorAmount(amountMinor: Long): String {
    val sign = if (amountMinor < 0) "-" else ""
    val absolute = abs(amountMinor)
    val major = absolute / 100
    val minor = absolute % 100
    return "$sign$major.${minor.toString().padStart(2, '0')}"
}

fun formatInputMinorAmount(amountMinor: Long): String {
    val formatted = formatMinorAmount(amountMinor)
    return if (formatted.endsWith(".00")) formatted.dropLast(3) else formatted
}

fun parseMinorAmount(rawValue: String): Long? {
    val normalized = rawValue.trim().replace(',', '.')
    if (normalized.isBlank() || normalized == "-") return null

    val isNegative = normalized.startsWith("-")
    val unsigned = if (isNegative) normalized.drop(1) else normalized
    if (unsigned.isBlank()) return null

    val parts = unsigned.split('.')
    if (parts.size > 2) return null

    val majorPart = parts[0].ifBlank { "0" }
    val minorPart = parts.getOrNull(1).orEmpty()
    if (!majorPart.all(Char::isDigit) || !minorPart.all(Char::isDigit) || minorPart.length > 2) {
        return null
    }

    val major = majorPart.toLongOrNull() ?: return null
    val minor = minorPart.padEnd(2, '0').ifBlank { "00" }.toLongOrNull() ?: return null
    val amount = major * 100 + minor
    return if (isNegative) -amount else amount
}
