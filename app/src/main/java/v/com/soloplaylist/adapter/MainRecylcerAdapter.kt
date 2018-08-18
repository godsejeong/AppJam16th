package v.com.soloplaylist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                .inflate(R.layout.mainitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var name: TextView = itemView.findViewById(R.id.departmentitemText)
//        var img: ImageView = itemView.findViewById(R.id.departmentitemImg)
    }
}
