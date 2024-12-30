package com.example.madcamp_week1_moonwon.utils



import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun captureBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()
    val file = File(cachePath, "shared_image.png")
    val stream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.close()
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}
