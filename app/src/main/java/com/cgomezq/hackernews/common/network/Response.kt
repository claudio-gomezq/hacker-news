package com.cgomezq.hackernews.common.network

import retrofit2.Response

fun <T> Response<T>.getBodyOrError(message: String = ""): T {
    val body = body()
    if (!isSuccessful || body == null) {
        error(message)
    }
    return body
}