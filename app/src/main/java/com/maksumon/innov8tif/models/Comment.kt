package com.maksumon.innov8tif.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(val postId: Long,
                   val id: Long,
                   val name: String,
                   val email: String,
                   val body: String)
