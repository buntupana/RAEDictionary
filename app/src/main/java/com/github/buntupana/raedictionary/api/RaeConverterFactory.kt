package com.github.buntupana.raedictionary.api

import com.github.buntupana.raedictionary.vo.Word
import com.github.buntupana.raedictionary.vo.WordOfTheDay
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RaeConverterFactory : Converter.Factory() {

    companion object {
        fun create() = RaeConverterFactory()
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {

        if (type == Word::class.java || type == WordOfTheDay::class.java) {
            return RaeRequestBodyConverter.INSTANCE
        }

        return null
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {

        if (type == Word::class.java) {
            return RaeResponseBodyConverter.WordResponseBodyConverter.INSTANCE
        }

        if (type == WordOfTheDay::class.java){
            return RaeResponseBodyConverter.WordOfTheDayResponseBodyConverter.INSTANCE
        }

        return null
    }
}