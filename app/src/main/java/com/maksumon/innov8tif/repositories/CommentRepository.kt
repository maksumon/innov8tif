package com.maksumon.innov8tif.repositories

import com.maksumon.innov8tif.interfaces.RetrofitService

class CommentRepository  constructor(private val retrofitService: RetrofitService) {
    fun getAllComments(id: Long) = retrofitService.getAllComments(id)
}