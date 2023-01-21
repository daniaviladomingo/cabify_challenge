package com.cabify.challenge.domain.interactors.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCaseFlow<IN, OUT>(
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(param: IN): Flow<Result<OUT>> = build(param)
        .flowOn(dispatcher)
        .catch { throwable ->
            emit(Result.failure(throwable))
        }

    protected abstract fun build(param: IN): Flow<Result<OUT>>
}