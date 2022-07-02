package com.maksumon.innov8tif.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.adapters.CommentAdapter
import com.maksumon.innov8tif.extensions.closeFocus
import com.maksumon.innov8tif.extensions.setCloseButtonAction
import com.maksumon.innov8tif.extensions.showErrorToast
import com.maksumon.innov8tif.factories.CommentViewModelFactory
import com.maksumon.innov8tif.interfaces.RetrofitService
import com.maksumon.innov8tif.repositories.CommentRepository
import com.maksumon.innov8tif.viewmodels.CommentViewModel

class CommentListActivity : AppCompatActivity() {
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = CommentAdapter()
    private var postId:Long = 0
    private lateinit var viewModel: CommentViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupIntentData()
        setupUIAndBind()
        setupViewModel()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupIntentData() {
        val extras = intent.extras
        if (null != extras) {
            postId = extras.getLong("postId")
        }
    }

    private fun setupUIAndBind() {
        swipeRefreshLayout = findViewById(R.id.comment_swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.comment_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                CommentViewModelFactory(CommentRepository(retrofitService))
            ).get(
                CommentViewModel::class.java
            )

        viewModel.commentList.observe(this) {
            Log.d("CommentListActivity", "commentList: $it")
            adapter.setCommentList(it)
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.errorMessage.observe(this) {
            Log.d("CommentListActivity", "errorMessage: $it")
            this.showErrorToast(it)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun loadData() {
        viewModel.getAllComments(this, postId)
    }

    private fun setupSearchView(menu: Menu) {
        val searchItem: MenuItem = menu.findItem(R.id.search_comment)
        val searchView = searchItem.actionView as SearchView
        searchView.setCloseButtonAction(searchItem)
        searchView.setOnCloseListener { true }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.closeFocus(searchItem)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isIconified = false
    }
}