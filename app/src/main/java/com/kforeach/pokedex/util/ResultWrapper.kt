package com.kforeach.pokedex.util

data class ResultWrapper<T>(
    val data: T? = null,
    val isSuccess: Boolean,
    val message: String? = null,
    val error: Throwable? = null
): ResultHandler<T> {

    override fun onSuccess(action: (T) -> Unit): ResultHandler<T> {
        if (isSuccess && data != null) action(data)
        return this
    }

    override fun onError(action: (Throwable?) -> Unit): ResultHandler<T> {
        if (!isSuccess) action(error)
        return this
    }

    override fun onFinish(action: (ResultWrapper<T>) -> Unit): ResultHandler<T> {
        action(this)
        return this
    }

    companion object {
        fun <T> success(data: T, message: String? = null): ResultWrapper<T> {
            return ResultWrapper(data = data, isSuccess = true, message = message)
        }

        fun <T> error(
            data: T? = null,
            error: Throwable? = null,
            message: String?
        ): ResultWrapper<T> {
            return ResultWrapper(data = data, isSuccess = false, message = message, error = error)
        }
    }
}

interface ResultHandler<T> {
    fun onSuccess(action: (T) -> Unit): ResultHandler<T>
    fun onError(action: (err:Throwable?) -> Unit): ResultHandler<T>
    fun onFinish(action: (ResultWrapper<T>) -> Unit): ResultHandler<T>
}
