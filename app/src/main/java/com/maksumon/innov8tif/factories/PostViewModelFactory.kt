package com.maksumon.innov8tif.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksumon.innov8tif.repositories.PostRepository
import com.maksumon.innov8tif.viewmodels.PostViewModel

class PostViewModelFactory constructor(private val repository: PostRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            PostViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}