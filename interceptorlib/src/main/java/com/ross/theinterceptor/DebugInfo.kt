package com.ross.theinterceptor

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class DebugInfo(
    /**
     * Url of the request
     */
    @Json(name = "url") val url: String,
    /**
     * Params of the url
     */
    @Json(name = "params") val params: String,
    /**
     * response code of the request
     */
    @Json(name = "responseCode") val responseCode: Int,
    /**
     * Http method used
     */
    @Json(name = "method") val method: String,
    /**
     * the way that the argument is passed here is how
     * will be displayed, so give power to your creativity or
     * if you are using retrofit just use
     * chain.request().headers.toString(), it will be
     * formatted
     */
    @Json(name = "headers") val headers: String,
    /**
     * Just the request body being sent in this request
     * an idea of usage with retrofit is
     * val buffer = Buffer()
     * requestBody.writeTo(buffer)
     * buffer.readUtf8() // json here
     */
    @Json(name = "requestBody") var requestBody: String,
    /**
     * Json response
     */
    @Json(name = "responseBody") var responseBody: String,
    /**
     * Authentication used
     */
    @Json(name = "authentication") val authentication: String,
    /**
     * Formatted date of this info
     */
    @Json(name = "date") val date: String
) : Parcelable