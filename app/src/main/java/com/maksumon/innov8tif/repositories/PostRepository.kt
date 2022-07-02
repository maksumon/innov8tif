package com.maksumon.innov8tif.repositories

import com.maksumon.innov8tif.interfaces.RetrofitService

class PostRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllPosts() = retrofitService.getAllPosts()
}