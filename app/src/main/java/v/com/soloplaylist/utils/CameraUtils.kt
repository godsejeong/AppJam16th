package v.com.soloplaylist.utils

import android.app.Activity
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraUtils{

    //camera_저장경로 설정
    fun getOutputMediaFileUri() : File? {
        if (isExternalStorageAvailable()) {
            val imagePath = "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "SoloPlayList")
            storageDir!!.mkdir()
            var image = File.createTempFile(imagePath, ".jpg", storageDir)
//            path = image!!.absolutePath
            Log.e("imagepath", image.toString())
            return image
        }

        return null
    }

    private fun isExternalStorageAvailable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    //uri->filepath 변환
    fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }
}