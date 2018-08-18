package v.com.soloplaylist.utils

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.data.GesiData
import v.com.soloplaylist.data.SaveData
import v.com.soloplaylist.data.ServiceCommentData
import java.io.File

interface Services {
    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String): Call<EmptyData>

    @GET("sign")
    fun UserInfo(@Header("Authorization") Authorization: String): Call<SaveData>

    @Multipart
    @POST("users")
    fun User(@Part("userid") userid: String,
             @Part("username") username: String,
             @Part("password") password: String,
             @Part("ddate") ddate: String,
             @Part thumbnail: MultipartBody.Part): Call<EmptyData>

    @FormUrlEncoded
    @POST("posts")
    fun Posts(@Header("Authorization") Authorization: String,
              @Field("content") content: String): Call<GesiData>

    @FormUrlEncoded
    @POST("comments")
    fun Comment(@Header("Authorization") Authorization: String,
                @Field("content") content : String,
                @Field("postid") postid: String): Call<ServiceCommentData>

    @GET("posts")
    fun GetPosts(@Header("Authorization") Authorization: String): Call<GesiData>

    @GET("comments")
    fun Comments(@Header("Authorization") Authorization: String,
                 @Header("postid") postid: String): Call<ServiceCommentData>

}