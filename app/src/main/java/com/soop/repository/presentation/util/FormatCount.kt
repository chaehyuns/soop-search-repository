package com.soop.repository.presentation.util

import java.text.DecimalFormat

fun Int.formatCount(): String {
    return if (this >= 1000) {
        DecimalFormat("#.#k").format(this / 1000.0)
    } else {
        this.toString()
    }
}
