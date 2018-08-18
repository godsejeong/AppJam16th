package v.com.soloplaylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.mainitem.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import org.w3c.dom.Comment
import retrofit2.Call
import v.com.soloplaylist.R
import v.com.soloplaylist.adapter.CommentRecyclerAdapter
import v.com.soloplaylist.adapter.MainRecylcerAdapter
import v.com.soloplaylist.data.CommentData
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.data.PostData
import v.com.soloplaylist.utils.ORMUtil
import v.com.soloplaylist.utils.RetrofitUtils
import retrofit2.Callback
import retrofit2.Response
import v.com.soloplaylist.data.ServiceCommentData
import v.com.soloplaylist.utils.GetComment

class CommentActivity : AppCompatActivity() {
    var id = ""
    var comment = 0
    var content = ""
    var name = ""
    var rank = ""
    var like = 0
    var profileimg = ""

    var items = ArrayList<CommentData>()
    lateinit var adapter : CommentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        id = intent.getStringExtra("postid")
        comment = intent.getIntExtra("comment",0)
        name = intent.getStringExtra("name")
        profileimg = intent.getStringExtra("profile")
        like = intent.getIntExtra("love",0)
        content = intent.getStringExtra("content")
        rank = intent.getStringExtra("rank")

        additemNickname.text = name
        Glide.with(this).load(profileimg).into(additemProfile)
        additemRank.text = rank
        additemcontent.text = content
        additemliketext.text = like.toString()
        additmeComment.text = comment.toString()

        var manager  = LinearLayoutManager(this)
        commentlist.layoutManager = manager
        adapter = CommentRecyclerAdapter(items,this)
        commentlist.adapter = adapter


        var list: List<Any> = ORMUtil(this).tokenORM.find(EmptyData())
        var sign = list[list.size - 1] as EmptyData

        commentBtn.onClick {
            var res  = RetrofitUtils.postService.Comment(sign.token,commentText.text.toString(),id)
            res.enqueue(object : Callback<ServiceCommentData> {
                override fun onFailure(call: Call<ServiceCommentData>?, t: Throwable?) {
                    toast(t!!.message!!)
                }

                override fun onResponse(call: Call<ServiceCommentData>?, response: Response<ServiceCommentData>?) {
                    when(response!!.code()){
                        200->{
                            toast("댓글이 등록 되었습니다.")
                            var comment = GetComment(sign.token,id)
                            comment.start()
                            comment.join()
                            var array = comment.getpost() as org.json.simple.JSONArray
                            for(i in 0 until array.size){
                                var tmp = array[i] as org.json.simple.JSONObject
                                var content = tmp.get("content") as String
                                var user = tmp.get("user") as org.json.simple.JSONObject
                                var name = user.get("username") as String
                                var rank = user.get("rank") as String
                                var img = user.get("thumbnail") as String

                                items.add(CommentData(name, "http://norr.uy.to:6010/api$img",rank,content))

                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            })
        }

    }
}
