package dev.lstr.llevateclaro.data

import android.support.test.runner.AndroidJUnit4
import dev.lstr.llevateclaro.util.ResourceLoader.loadContentFromFile
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by lesternr on 7/2/18.
 */
@RunWith(AndroidJUnit4::class)
class LoginData {

    private val server: MockWebServer = MockWebServer()

    @Before
    fun setUp() {
        server.start()
    }

    @Test
    fun `validateUserData`() {
        enqueueMockResponse(200, "login_success.json")

        assertGetRequestSentTo("/webservicev/web/usweb.php")
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(responseCode: Int = 200, fileName: String? = null) {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode)
        val fileResponse = loadContentFromFile(fileName)
        mockResponse.setBody(fileResponse)
        server.enqueue(mockResponse)
    }

    private fun assertGetRequestSentTo(url: String) {
        val base_url = server.url(url)

        val bodyOfRequest = sendLoginRequest(OkHttpClient(), base_url)
        Assert.assertNotNull(bodyOfRequest.getJSONArray("data"))
        Assert.assertTrue(bodyOfRequest.isNull("mensaje"))
    }

    @Throws(IOException::class)
    private fun sendLoginRequest(okHttpClient: OkHttpClient, base: HttpUrl): JSONObject {
        val body: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "Hi there")
        val request = okhttp3.Request.Builder()
                .post(body)
                .url(base)
                .build()
        val response = okHttpClient.newCall(request).execute()
        val data = response.body()!!.string()
        return JSONObject(data)
    }
}