package v.com.soloplaylist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import v.com.soloplaylist.R
import v.com.soloplaylist.data.CommentData
import v.com.soloplaylist.data.PostData

class CommentRecyclerAdapter(items: ArrayList<CommentData>, context: Context) : RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {

    var items: ArrayList<CommentData> = ArrayList()
    var adaptercontext: Context? = null

    init {
        this.items = items
        this.adaptercontext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.mainitem, parent, false)
        return CommentRecyclerAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.name.text = item.name

        Glide.with(adaptercontext!!).load(item.img).into(holder.Profile)
        holder.rank.text = item.rank
        holder.content.text = item.conetnet
        holder.name.text = item.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.cemmteritemname)
        var Profile : CircularImageView = itemView.findViewById(R.id.commentProfile)
        var rank : TextView = itemView.findViewById(R.id.commentRank)
        var content : TextView = itemView.findViewById(R.id.commentcontent)
    }
}