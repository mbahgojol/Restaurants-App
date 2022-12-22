package com.blank.composerestaurant.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.Density

object TopWithFooter : Arrangement.Vertical {
    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray
    ) {
        var y = 0
        sizes.forEachIndexed { index, size ->
            outPositions[index] = y
            y += size
        }
        if (y < totalSize) {
            val lastIndex =
                outPositions.lastIndex
            outPositions[lastIndex] =
                totalSize - sizes.last()
        }
    }
}