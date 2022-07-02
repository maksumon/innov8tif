package com.maksumon.innov8tif.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksumon.innov8tif.repositories.CommentRepository
import com.maksumon.innov8tif.viewmodels.CommentViewModel

class CommentViewModelFactory constructor(private val repository: CommentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            CommentViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}