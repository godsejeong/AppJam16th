package v.com.soloplaylist.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.simple.JSONObject
import v.com.soloplaylist.activity.AddPostActivity
import v.com.soloplaylist.R
import v.com.soloplaylist.adapter.MainRecylcerAdapter
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.data.PostData
import v.com.soloplaylist.utils.GetPostUtils
import v.com.soloplaylist.utils.ORMUtil

class MainFragment : Fragment() {
    var items = ArrayList<PostData>()
    var size = 0
    lateinit var adapter: MainRecylcerAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_main, container, false)

        var manager = LinearLayoutManager(activity)
        view.mainRecylcer.layoutManager = manager
        adapter = MainRecylcerAdapter(items, context!!)
        view.mainRecylcer.adapter = adapter

        view.mainoneText.onClick {
            mainoneText.setTypeface(null, Typeface.BOLD)
            maintwoText.setTypeface(null, Typeface.BOLD_ITALIC)
        }

        view.maintwoText.onClick {
            maintwoText.setTypeface(null, Typeface.BOLD)
            mainoneText.setTypeface(null, Typeface.BOLD_ITALIC)
        }
        view.mainFab.onClick {
            var intent = Intent(activity, AddPostActivity::class.java)
            startActivityForResult(intent, 100)
        }


        view.mainRecylcer.addOnScrollListener(object : RecyclerView.OnScrollListener() {              //스크롤 시 플로팅버튼 숨기기
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    mainFab.hide()
                } else if (dy < 0)
                    mainFab.show()
            }
        })

        itemadd()


        return view
    }

    fun itemadd() {
        var list: List<Any> = ORMUtil(context!!).tokenORM.find(EmptyData())
        var sign = list[list.size - 1] as EmptyData

        var posts = GetPostUtils(sign.token)
        posts.start()
        posts.join()

        var array = posts.getpost() as org.json.simple.JSONArray?
        for (i in 0 until array!!.size) {
            size = array!!.size

                var tmp = array[i] as JSONObject
                var content = tmp.get("content") as String
                var list = tmp.get("likes") as List<String>
                var comment = tmp.get("comments") as List<String>

                var user = tmp.get("user") as JSONObject
                var username = user.get("username") as String
                var rank = user.get("rank") as String
                var image = user.get("thumbnail") as String

                var postid = tmp.get("_id") as String
                items.add(PostData(postid, content, "http://norr.uy.to:6010/api$image", username, rank, list.size, comment.size))

                adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
//            Log.e("asdf", "왜 안됨?")
//            itemadd()
        }
    }
}