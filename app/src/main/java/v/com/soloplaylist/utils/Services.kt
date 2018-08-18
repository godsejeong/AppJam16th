package v.com.soloplaylist.utils

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.data.SaveData
import java.io.File

interface Services {
    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid : String,
             @Field("password") password: String) : Call<EmptyData>

    @GET("sign")
    fun UserInfo(@Header("Authorization") Authorization : String) : Call<SaveData>

    @Multipart
    @POST("users")
    fun User(@Part("userid") userid : String,
             @Part("username") username : String,
             @Part("password") password : String,
             @Part("ddate") ddate : String,
             @Part thumbnail : MultipartBody.Part) : Call<EmptyData>
}