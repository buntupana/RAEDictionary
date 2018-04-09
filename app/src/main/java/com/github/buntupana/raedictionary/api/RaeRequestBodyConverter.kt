package com.github.buntupana.raedictionary.api

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter
import java.io.IOException

internal class RaeRequestBodyConverter<T> private constructor() : Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, value.toString())
    }

    companion object {
        val INSTANCE = RaeRequestBodyConverter<Any>()
        private val MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8")
    }
}