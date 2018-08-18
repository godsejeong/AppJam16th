package v.com.soloplaylist.utils

import com.google.gson.Gson
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import retrofit2.Call
import v.com.soloplaylist.data.GesiData
import v.com.soloplaylist.data.ServiceCommentData

class GetComment(token : String,id : String) : Thread(){
    val res: Call<ServiceCommentData> = RetrofitUtils.postService.Comments(token,id)
    lateinit var data : org.json.simple.JSONArray

    override fun run() {
        var code = res.clone().execute().code()
        when(code){
            200->{
                data = JSONParser().parse(Gson().toJson(res.clone().execute().body()!!.comments)) as JSONArray
            }
        }
    }

    fun getpost() : org.json.simple.JSONArray{
        return data
    }
}