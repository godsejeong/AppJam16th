package v.com.soloplaylist.utils

import com.google.gson.Gson
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import retrofit2.Call
import v.com.soloplaylist.data.GesiData

class GetPostUtils(token : String) : Thread(){
    val res: Call<GesiData> = RetrofitUtils.postService.GetPosts(token)
    lateinit var data : org.json.simple.JSONArray

    override fun run() {
        var code = res.clone().execute().code()
        when(code){
            200->{
                data = JSONParser().parse(Gson().toJson(res.clone().execute().body()!!.posts)) as JSONArray
            }
        }
    }

    fun getpost() : org.json.simple.JSONArray{
        return data
    }
}