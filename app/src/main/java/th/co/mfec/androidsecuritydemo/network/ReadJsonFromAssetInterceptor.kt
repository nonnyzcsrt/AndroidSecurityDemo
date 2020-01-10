package com.example.natandroid.dev.http

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class ReadJsonFromAssetInterceptor constructor(private val mContext: Context) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val path = url.encodedPath.let { t -> t.substring(t.lastIndexOf("/")) }
        println("ReadJsonFromAssetInterceptor :$path")
        val responseString = loadAssetTextAsString(mContext, "stubs$path.json")

        return Response.Builder()
            .code(200)
            .message(responseString!!)
            .request(chain.request())
            .protocol(Protocol.HTTP_2)
            .body(ResponseBody.create("application/json".toMediaTypeOrNull(), responseString))
            .addHeader("content-type", "application/json")
            .build()

    }

    fun loadAssetTextAsString(context: Context, name: String): String? {
        return context.assets.open(name).bufferedReader().use {
            it.readText()
        }
    }


}