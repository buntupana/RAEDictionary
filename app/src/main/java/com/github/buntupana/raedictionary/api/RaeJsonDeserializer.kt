package com.github.buntupana.raedictionary.api

import com.github.buntupana.raedictionary.vo.WordBasic
import com.google.gson.*
import java.lang.reflect.Type


class RaeJsonDeserializer : JsonDeserializer<List<WordBasic>> {
    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type, jdc: JsonDeserializationContext): List<WordBasic> {
        return Gson().fromJson<SearchResult>(je, SearchResult::class.java).res
    }

    class SearchResult(val approx: Int, val res: List<WordBasic>)
}