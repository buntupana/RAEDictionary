package com.github.buntupana.raedictionary.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.buntupana.raedictionary.util.LiveDataCallAdapterFactory
import com.github.buntupana.raedictionary.util.LiveDataTestUtil
import com.github.buntupana.raedictionary.vo.Word
import com.github.buntupana.raedictionary.vo.WordBasic
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets


class RAEServiceTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    private lateinit var service: RAEService
    private var mockWebServer = MockWebServer()

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()

        val gson = GsonBuilder()
                .registerTypeAdapter(List::class.java, RaeJsonDeserializer())
                .create()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(RaeConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create<RAEService>(RAEService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun searchWord() {

        val result = WordBasic("KYwHQ7M", "holanda", 1)

        enqueueResponse("search-result-hola.json")
        var wordBasicList : List<WordBasic> = LiveDataTestUtil.getValue(service.searchWordByType("hola", 31)).body!!
        Assert.assertNotNull(wordBasicList)
        assertEquals(wordBasicList[1], result)

        val request = mockWebServer.takeRequest()
        assertThat<String>(request.path, `is`<String>("/search?w=hola&m=31"))

    }

    @Test
    fun fetchWord() {

        enqueueResponse("fetch-word-guardia.json")
        var response = LiveDataTestUtil.getValue(service.fetchWord("guardia")).body as Word

        assertEquals(response.html, "guardia.")

        val request = mockWebServer.takeRequest()
    }

    @Test
    fun fetchWordOfTheDay() {
        enqueueResponse("word-of-the-day.json")

        var response = LiveDataTestUtil.getValue(service.fetchWordOfDay()).body as WordBasic

        Assert.assertNotNull(response)
        assertEquals(response.word, "copla")
    }


    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap<String, String>())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)))
    }
}