package v.com.soloplaylist.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_post.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import v.com.soloplaylist.R
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.utils.ORMUtil
import v.com.soloplaylist.utils.RetrofitUtils
import retrofit2.Callback
import retrofit2.Response
import v.com.soloplaylist.data.GesiData
import v.com.soloplaylist.fragment.MainFragment

class AddPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        var list: List<Any> = ORMUtil(this).tokenORM.find(EmptyData())
        var sign = list[list.size - 1] as EmptyData

        val returnIntent = Intent()

        addfinish.onClick {
            var res = RetrofitUtils.postService.Posts(sign.token,addText.text.toString())
            res.enqueue(object : Callback<GesiData> {
                override fun onFailure(call: Call<GesiData>?, t: Throwable?) {
                    toast(t!!.message!!)
                }

                override fun onResponse(call: Call<GesiData>?, response: Response<GesiData>?) {

                    when(response!!.code()){
                        200 ->{
                            setResult(Activity.RESULT_OK,returnIntent)
                            finish()
                            toast("정상적으로 게시물을 등록하였습니다.")
                        }
                        else->{
                            toast(response.code())
                        }
                    }
                }

            })
        }

    }


}
