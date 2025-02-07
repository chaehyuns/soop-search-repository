package com.soop.repository.di.module

import com.soop.repository.CACHED_1_MINUTES
import com.soop.repository.CACHED_1_SECOND
import com.soop.repository.CACHE_SIZE_10MB
import com.soop.repository.CACHE_SIZE_5KB
import com.soop.repository.EXPECTED_BODY
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import java.io.File

class NetworkModuleTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var client: OkHttpClient
    private lateinit var cache: Cache
    private lateinit var cacheDir: File

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        cacheDir = File("build/tmp/cache").apply { mkdirs() }
        cache = Cache(cacheDir, CACHE_SIZE_10MB)

        client = OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        cache.delete()
    }

    private fun createRequest(path: String): Request {
        return Request.Builder()
            .url(mockWebServer.url(path))
            .build()
    }

    private fun enqueueResponse200(
        body: String,
        responseCode: Int = 200,
        cacheControl: String? = null
    ) {
        val response = MockResponse()
            .setResponseCode(responseCode)
            .setBody(body)

        cacheControl?.let {
            response.setHeader("Cache-Control", it)
        }

        mockWebServer.enqueue(response)
    }

    @Test
    @DisplayName("캐시 헤더가 있는 경우 정상적으로 캐시 저장된다")
    fun testResponseIsCachedWithHeader() {
        // Given
        val expectBody = EXPECTED_BODY
        enqueueResponse200(expectBody, cacheControl = "public, max-age=$CACHED_1_MINUTES")

        // When
        val request = createRequest("/cached")
        val response1 = client.newCall(request).execute()
        assertEquals(expectBody, response1.body?.string())

        // Then
        val response2 = client.newCall(request).execute()
        assertNotNull(response2.cacheResponse)
        assertEquals(expectBody, response2.body?.string())
    }

    @Test
    @DisplayName("지정한 캐시 maxSize보다 크기가 작으면 정상적으로 캐시가 저장된다")
    fun testCacheWithSmallSize() {
        // Given
        val smallCache = Cache(File("build/tmp/small_cache"), CACHE_SIZE_5KB)

        val smallClient = OkHttpClient.Builder()
            .cache(smallCache)
            .build()

        val expectBody = EXPECTED_BODY
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(expectBody)
            .setHeader("Cache-Control", "public, max-age=60")

        mockWebServer.enqueue(mockResponse)

        val request = Request.Builder()
            .url(mockWebServer.url("/small-cache"))
            .build()

        val response1 = smallClient.newCall(request).execute()
        assertEquals(expectBody, response1.body?.string())

        val response2 = smallClient.newCall(request).execute()
        assertNotNull(response2.cacheResponse)
        assertEquals(expectBody, response2.body?.string())
    }

    @Test
    @DisplayName("캐시 만료 시간이 지나면 cacheResponse는 null이다")
    fun testCacheExpiration() {
        // Given
        val initialResponseBody = "Cached Data"
        val expiredResponseBody = "Updated Data"

        enqueueResponse200(initialResponseBody, cacheControl = "public, max-age=$CACHED_1_SECOND")

        // When
        val request = createRequest("/cache-expiration")
        val response1 = client.newCall(request).execute()
        assertEquals(initialResponseBody, response1.body?.string())

        Thread.sleep(2000)

        enqueueResponse200(expiredResponseBody)

        // Then
        val response2 = client.newCall(request).execute()
        assertNull(response2.cacheResponse)
        assertEquals(expiredResponseBody, response2.body?.string())
    }

    @Test
    @DisplayName("캐시 만료 시간이 지나지 않으면 정상적으로 캐시가 저장된다")
    fun testCacheNotExpiration() {
        // Given
        val initialResponseBody = "Cached Data"

        enqueueResponse200(initialResponseBody, cacheControl = "public, max-age=$CACHED_1_MINUTES")

        // When
        val request = createRequest("/cache-expiration")
        val response1 = client.newCall(request).execute()
        assertEquals(initialResponseBody, response1.body?.string())

        Thread.sleep(2000)

        // Then
        val response2 = client.newCall(request).execute()
        assertNotNull(response2.cacheResponse)
        assertEquals(initialResponseBody, response2.body?.string())
    }
}
