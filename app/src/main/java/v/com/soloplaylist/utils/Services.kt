package v.com.soloplaylist.utils

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import v.com.soloplaylist.data.EmptyData

interface Services {
    @FormUrlEncoded
    @POST("sign")
    fun Sign(@Field("userid") userid: String,
             @Field("password") password: String) : Call<EmptyData>

}