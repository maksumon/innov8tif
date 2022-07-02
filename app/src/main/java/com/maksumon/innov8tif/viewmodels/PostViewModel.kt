package com.maksumon.innov8tif.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.extensions.hideProgressBar
import com.maksumon.innov8tif.extensions.isInternetAvailable
import com.maksumon.innov8tif.extensions.showProgressBar
import com.maksumon.innov8tif.models.Post
import com.maksumon.innov8tif.repositories.PostRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    val postList = MutableLiveData<List<Post>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllPosts(context: Context) {
        if (context.isInternetAvailable()) {
            context.showProgressBar()
            val response = repository.getAllPosts()
            response.enqueue(object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    context.hideProgressBar()
                    postList.postValue(response.body()?.toList())
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    context.hideProgressBar()
                    errorMessage.postValue(t.message)
                }
            })
        } else {
            errorMessage.postValue(context.getString(R.string.internet_connectivity_error))
        }
    }
}