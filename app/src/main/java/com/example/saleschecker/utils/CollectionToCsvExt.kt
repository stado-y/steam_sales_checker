package com.example.saleschecker.utils


fun <T> Collection<T>.toCsv(
    bounds: String = "\"",
): String {
    return this.joinToString(
        prefix = bounds,
        postfix = bounds,
        separator = ",",
    )
}


