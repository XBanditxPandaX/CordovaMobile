package com.mastercyber.tp1

import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toImageBitmap() =
    Image.makeFromEncoded(this).toComposeImageBitmap()