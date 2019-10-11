package com.netflix.spinnaker.testing.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookieInterceptor : Interceptor {
    private val REQUEST_HEADER_COOKIE = "Cookie"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val session_id = "SESSION=%s".format(System.getenv("SPINNAKER_SESSION_ID"))
        val original = chain.request()
        var request = original

        // Only adds if there's a cookie
        if (!session_id.isEmpty()) {
            request = original.newBuilder()
                    .addHeader(REQUEST_HEADER_COOKIE, session_id)
                    .method(original.method(), original.body())
                    .build()
        }
        return chain.proceed(request)
    }
}