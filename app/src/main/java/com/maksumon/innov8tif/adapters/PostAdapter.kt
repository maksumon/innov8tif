package com.maksumon.innov8tif.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.models.Post

class PostAdapter(private var listener: OnItemClickListener): RecyclerView.Adapter<PostAdapter.PostItemViewHolder>() {
    private var posts = mutableListOf<Post>()

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    fun setPostList(posts: List<Post>) {
        this.posts = posts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_post_recycler_view_item,parent,false)
        return PostItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val post = posts[position]
        holder.title?.text = post.title
        holder.body?.text = post.body

        holder.bindView(posts[position], listener)
    }

    class PostItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title: TextView? = null
        var body: TextView? = null

        init {
            title = view.findViewById(R.id.post_title)
            body = view.findViewById(R.id.post_body)
        }

        fun bindView(hospital: Post, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(hospital) }
        }
    }
}
