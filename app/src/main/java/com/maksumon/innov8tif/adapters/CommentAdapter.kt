package com.maksumon.innov8tif.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.models.Comment
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class CommentAdapter: RecyclerView.Adapter<CommentAdapter.CommentItemViewHolder>() {
    private var comments = mutableListOf<Comment>()
    private var commentsFilter = mutableListOf<Comment>()

    fun setCommentList(comments: List<Comment>) {
        this.comments = comments.toMutableList()
        this.commentsFilter = this.comments
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint?.toString() ?: ""
                commentsFilter = if (charSearch.isEmpty()) comments else {
                    val resultList = mutableListOf<Comment>()
                    comments
                        .filter {
                            (it.body.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)))
                        }
                        .forEach { resultList.add(it) }
                    resultList
                }

                commentsFilter = if (charSearch.isEmpty()) {
                    comments
                } else {
                    val resultList = mutableListOf<Comment>()
                    for (comment in comments) {
                        if (comment.body.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(comment)
                        }
                    }
                    resultList
                }

                return FilterResults().apply { values = commentsFilter }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                commentsFilter = if (results?.values == null)
                    mutableListOf()
                else
                    (results.values as List<Comment>).toMutableList()
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_comment_recycler_view_item,parent,false)
        return CommentItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentsFilter.size
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val comment = commentsFilter[position]
        Log.d("CommentAdapter", comment.toString())
        holder.name?.text = comment.name
        holder.email?.text = comment.email
        holder.body?.text = comment.body
    }

    class CommentItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var name: TextView? = null
        var email: TextView? = null
        var body: TextView? = null

        init {
            name = view.findViewById(R.id.comment_name)
            email = view.findViewById(R.id.comment_email)
            body = view.findViewById(R.id.comment_body)
        }
    }
}