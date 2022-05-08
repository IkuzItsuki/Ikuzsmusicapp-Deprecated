package com.ikuz.ikuzsmusicapp.utils

import android.os.Environment
import java.io.File

class Constant {
    companion object{
    val imaSongDir = File(
        Environment.getExternalStorageDirectory(),
        "IMAMusic")

    val albumArtDir = File(imaSongDir.absolutePath + "/album_art")
    }
}