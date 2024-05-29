package com.cgomezq.hackernews.common.ui.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T, R> Flow<T>.transformFold(
    initial: R,
    transform: suspend (R, T, suspend (R) -> Unit) -> Unit
): Flow<R> = flow {
    var accumulator: R = initial
    emit(accumulator)
    collect { value ->
        transform(accumulator, value) {
            accumulator = it
            emit(accumulator)
        }
    }
}