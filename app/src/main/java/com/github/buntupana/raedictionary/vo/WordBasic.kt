package com.github.buntupana.raedictionary.vo

import com.google.gson.annotations.SerializedName


open class WordBasic(val id: String, word: String, @SerializedName("grp") val index: Int) {

    constructor(id: String, word: String) : this(id, word, 0)

    @SerializedName("header")
    val word = word
        get() = field.removeSuffix(".")


}
