package com.github.buntupana.raedictionary.api

import com.github.buntupana.raedictionary.vo.Word
import com.github.buntupana.raedictionary.vo.WordOfTheDay
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter

class RaeResponseBodyConverter {

    class WordOfTheDayResponseBodyConverter : Converter<ResponseBody, WordOfTheDay> {

        companion object {
            val INSTANCE = WordOfTheDayResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): WordOfTheDay {

            var result = value.string()?.removePrefix("json(")?.removeSuffix(")")

            return Gson().fromJson(result, WordOfTheDay::class.java)
        }
    }

    class WordResponseBodyConverter : Converter<ResponseBody, Word> {

        companion object {
            val INSTANCE = WordResponseBodyConverter()
        }

        override fun convert(value: ResponseBody): Word {

            val html = value.string()

            val word = Word(html)

            val doc = Jsoup.parse(html)

            val name = doc.getElementsByTag("header").first().text()


            return word
        }
    }
}