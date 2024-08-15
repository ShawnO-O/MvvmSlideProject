package com.shawn.mvvmslideproject.util

import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.Exception


internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.nanoTime()
        val method = request.method
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body is FormBody) {
                val body = request.body as FormBody
                for (i in 0 until body.size) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                sb.delete(sb.length - 1, sb.length)
                Log.d("shawnTest","發送資料${request.url} ,${chain.connection()}, ${request.headers} ,$sb")
            }
        } else Log.d("shawnTest","${request.url} ,${chain.connection()}, ${request.headers}")

        val response = chain.proceed(request)
        val endTime = System.nanoTime()
        val responseBody = response.peekBody(1024 * 1024)
        try {
            Log.d("shawnTest","接收資料${response.request.url} ${(endTime - startTime)}")
            Log.d("shawnTest","接收資料${responseBody.string().toString()}")
        }catch (e:Exception){
            e.printStackTrace()
        }
        return response
    }
}