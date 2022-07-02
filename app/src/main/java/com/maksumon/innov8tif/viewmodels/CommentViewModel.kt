package com.maksumon.innov8tif.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maksumon.innov8tif.R
import com.maksumon.innov8tif.extensions.hideProgressBar
import com.maksumon.innov8tif.extensions.isInternetAvailable
import com.maksumon.innov8tif.extensions.showProgressBar
import com.maksumon.innov8tif.models.Comment
import com.maksumon.innov8tif.repositories.CommentRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel(private val repository: CommentRepository) : ViewModel() {
    val commentList = MutableLiveData<List<Comment>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllComments(context: Context, postId: Long) {
        if (context.isInternetAvailable()) {
            context.showProgressBar()
            val response = repository.getAllComments(postId)
            response.enqueue(object : Callback<List<Comment>> {
                override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                    context.hideProgressBar()
                    commentList.postValue(response.body()?.toList())
                }

                override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    context.hideProgressBar()
                    errorMessage.postValue(t.message)
                }
            })
        } else {
            errorMessage.postValue(context.getString(R.string.internet_connectivity_error))
        }
    }
}