package v.com.soloplaylist.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitUtils {
    var url = "http://norr.uy.to:6010/api/"

    var retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)

    fun createMultipartBody(file: File, name: String): MultipartBody.Part {
        val mFile = RequestBody.create(MediaType.parse("images/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, mFile)
    }
}
