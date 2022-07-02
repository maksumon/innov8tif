package com.maksumon.innov8tif.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.adapters.PostAdapter
import com.maksumon.innov8tif.extensions.showErrorToast
import com.maksumon.innov8tif.factories.PostViewModelFactory
import com.maksumon.innov8tif.interfaces.RetrofitService
import com.maksumon.innov8tif.models.Post
import com.maksumon.innov8tif.repositories.PostRepository
import com.maksumon.innov8tif.viewmodels.PostViewModel

class PostListActivity : BaseActivity() {
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var adapter: PostAdapter
    private lateinit var viewModel: PostViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        setupAdapter()
        setupUIAndBind()
        setupViewModel()
        loadData()
    }

    private fun setupAdapter() {
        val listener = object : PostAdapter.OnItemClickListener {
            override fun onItemClick(post: Post) {
                Intent(this@PostListActivity, CommentListActivity::class.java).apply {
                    putExtra("postId", post.id)
                    startActivity(this)
                }
            }
        }

        adapter = PostAdapter(listener)
    }

    private fun setupUIAndBind() {
        swipeRefreshLayout = findViewById(R.id.post_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.post_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this,
                PostViewModelFactory(PostRepository(retrofitService))
            ).get(
                PostViewModel::class.java
            )

        viewModel.postList.observe(this) {
            Log.d("PostListActivity", "postList: $it")
            adapter.setPostList(it)
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.errorMessage.observe(this) {
            Log.d("PostListActivity", "errorMessage: $it")
            this.showErrorToast(it)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun loadData() {
        viewModel.getAllPosts(this)
    }
}