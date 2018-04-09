package com.github.buntupana.raedictionary.api

import android.arch.lifecycle.LiveData
import com.github.buntupana.raedictionary.vo.Word
import com.github.buntupana.raedictionary.vo.WordBasic
import com.github.buntupana.raedictionary.vo.WordOfTheDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface RAEService {

    @GET("/fetch/")
    fun fetchWord(@Query("id") id: String): LiveData<ApiResponse<Word>>

    @GET("/fetch/")
    fun fetchWordAux(@Query("id") id: String): Call<String>

    @GET("/search")
    fun searchWord(@Query("w") word : String) : LiveData<ApiResponse<List<WordBasic>>>

    @GET("/search")
    fun searchWordByType(@Query("w") word : String, @Query("m") type : Int) : LiveData<ApiResponse<List<WordBasic>>>

    @GET("/anagram")
    fun searchAnagram(@Query("w") word : String) : LiveData<ApiResponse<List<WordBasic>>>

    @GET("/random")
    fun fetchRandomWord() : LiveData<ApiResponse<String>>

    @GET("/wotd?callback=json")
    fun fetchWordOfDay() : LiveData<ApiResponse<WordOfTheDay>>
}