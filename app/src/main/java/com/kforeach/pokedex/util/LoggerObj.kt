package com.kforeach.pokedex.util

import android.util.Log

object LoggerObj {
    private const val DEFAULT_TAG = "Logger_Obj"
    private var isDebug: Boolean = true

    private fun buildLogMessage(methodName: String? = null, message: String): String {
        val stackTrace = Throwable().stackTrace
        val caller = stackTrace.firstOrNull { it.className != LoggerObj::class.java.name }
        val className = caller?.fileName ?: "UnknownFile"
        val rawMethod = caller?.methodName ?: "UnknownMethod"
        val method = methodName ?: if (rawMethod.contains("lambda")) "lambda" else rawMethod
        val lineNumber = caller?.lineNumber ?: -1
        return "($className:$lineNumber) $method: $message"
    }

    fun d(message: String, tag: String = DEFAULT_TAG, methodName: String? = null) {
        if (isDebug) Log.d(tag, buildLogMessage(methodName, message))
    }

    fun i(message: String, tag: String = DEFAULT_TAG, methodName: String? = null) {
        if (isDebug) Log.i(tag, buildLogMessage(methodName, message))
    }

    fun w(message: String, tag: String = DEFAULT_TAG, methodName: String? = null) {
        if (isDebug) Log.w(tag, buildLogMessage(methodName, message))
    }

    fun e(message: String, throwable: Throwable? = null, tag: String = DEFAULT_TAG, methodName: String? = null) {
        if (isDebug) Log.e(tag, buildLogMessage(methodName, message), throwable)
    }

    fun e(throwable: Throwable, tag: String = DEFAULT_TAG, methodName: String? = null) {
        if (isDebug) Log.e(tag, buildLogMessage(methodName, throwable.message ?: "Unknown Error"), throwable)
    }
}
