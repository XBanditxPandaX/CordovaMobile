package com.mastercyber.tp1

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toImageBitmap() = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()