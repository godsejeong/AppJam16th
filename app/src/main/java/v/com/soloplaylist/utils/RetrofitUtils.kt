package v.com.soloplaylist.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {
    var url = "http://aws.soylatte.kr:3000"

    var retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)
}
