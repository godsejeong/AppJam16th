package v.com.soloplaylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import v.com.soloplaylist.R
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.utils.RetrofitUtils

import retrofit2.Callback
import retrofit2.Response
import v.com.soloplaylist.data.SaveData
import v.com.soloplaylist.utils.ORMUtil

class LoginActivity : AppCompatActivity() {
    var name = ""
    var passwd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSignup.onClick {
            startActivity<SignInActivity>()
        }

        loginBtn.onClick {
            name = loginId.text.toString()
            passwd = loginPasswd.text.toString()

            if (name.isEmpty()) {
                loginId.error = "이름을 입력해주세요"
                loginId.requestFocus()
            }

            if (passwd.isEmpty()) {
                loginPasswd.error = "비밀번호를 입력해주세요"
                loginPasswd.requestFocus()
            }

            if (name.isNotEmpty() && passwd.isNotEmpty()) {
                login()
            }
        }
    }

    fun login() {
        var res = RetrofitUtils.postService.Sign(name,passwd)
        res.enqueue(object : Callback<EmptyData> {
            override fun onFailure(call: Call<EmptyData>?, t: Throwable?) {
                Log.e("LoginError", t!!.message)
            }

            override fun onResponse(call: Call<EmptyData>?, response: Response<EmptyData>) {
//                var success = response.body()!!.success
//                Log.e("test",response.body()!!.message)
                when (response.code()) {
                    200 -> {
                        startActivity<MainActivity>()
                        finish()
                        var token = response.body()!!.token
                        Log.e("token",token)
                        Log.e("", Gson().toJson(response.body()!!))
                        ORMUtil(this@LoginActivity).tokenORM.save(response.body()!!)
                        toast("로그인이 완료되었습니다.")
                    }

                    else -> {
//                        toast(response.body()!!.message!!)
                        toast("아이디나 비밀번호가 일치하지 않습니다.")
                    }
                }
            }
        })
    }

    fun Getuser(token : String){
        var res: Call<SaveData> = RetrofitUtils.postService.UserInfo(token)
        res.enqueue(object : Callback<SaveData> {

            override fun onResponse(call: Call<SaveData>?, response: Response<SaveData>?) {
                when {
                    response!!.code() == 200 -> response.body().let {
                        Log.e("name", Gson().toJson(response.body()!!))
                        ORMUtil(this@LoginActivity).userORM.save(response.body()!!.user!!)
                    }
                    else ->{
                        toast(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<SaveData>?, t: Throwable?) {
                Log.e("getuser Error", t!!.message)
                toast("Sever Error")
            }
        })
    }
}
