package v.com.soloplaylist.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import v.com.soloplaylist.activity.CommentActivity
import v.com.soloplaylist.R
import v.com.soloplaylist.data.PostData

class MainRecylcerAdapter(items: ArrayList<PostData>, context: Context) : RecyclerView.Adapter<MainRecylcerAdapter.ViewHolder>() {
    var items: ArrayList<PostData> = ArrayList()
    var adaptercontext: Context? = null

    init {
        this.items = items
        this.adaptercontext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.mainitems, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.name.text = item.name
        Glide.with(adaptercontext!!).load(item.profileimg).into(holder.Profile)
        holder.rank.text = item.rank
        holder.content.text = item.content
        holder.lovetext.text = item.like.toString()
        holder.commenttext.text = item.like.toString()

        holder.itemView.onClick {
            var intent = Intent(adaptercontext, CommentActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("profile",item.profileimg)
            intent.putExtra("rank",item.rank)
            intent.putExtra("love",item.like)
            intent.putExtra("comment",item.comment)
            intent.putExtra("content",item.content)
            intent.putExtra("postid",item.id)
            adaptercontext!!.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.itemNickname)
        var Profile : CircularImageView = itemView.findViewById(R.id.itemProfile)
        var rank : TextView = itemView.findViewById(R.id.itemRank)
        var content : TextView = itemView.findViewById(R.id.itemcontent)
        var loveimg : ImageView = itemView.findViewById(R.id.Itemlike)
        var lovetext : TextView = itemView.findViewById(R.id.itemliketext)
        var commentimg : ImageView = itemView.findViewById(R.id.itemChaticon)
        var commenttext : TextView = itemView.findViewById(R.id.itmeComment)
    }
}
