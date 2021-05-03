package com.ross.sample

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ross.sample.databinding.ActivityMainBinding
import com.ross.theinterceptor.DebugInfo
import com.ross.theinterceptor.InterceptorDebugger
import com.ross.theinterceptor.InterceptorDebuggerPreferencesProvider
import com.ross.theinterceptor.createInterceptorDebugger
import com.ross.theinterceptor.ui.interceptorDebugList.InterceptorDebugListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * [WARNING]
     * Do not use this, use Secure Preferences to have better security, you are saving important data!
     */
    private val sharedPreferences by lazy {
        applicationContext.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    private val preferencesProvider = object : InterceptorDebuggerPreferencesProvider {
        override fun provide(): SharedPreferences {
            return sharedPreferences
        }
    }

    private lateinit var interceptorDebugger: InterceptorDebugger

    private val datePattern by lazy { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        interceptorDebugger = createInterceptorDebugger(preferencesProvider) {
            setCacheLimit(10) // default is 100
        }

        // this interceptor should be the last
        val okHttp = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("SomeHeader", "SomeHeaderValue")
                    .build()

                val response = it.proceed(request)

                val requestJson = request.body().rawJson
                val responseJson = response.body()?.string().toString()

                val params = request.url().queryParameterNames().associateWithTo(mutableMapOf(),
                    { current -> request.url().queryParameterValues(current).first() })
                    .toString()

                val debugInfo = DebugInfo(
                    request.url().toString(),
                    params,
                    response.code(),
                    request.method(),
                    request.headers().toString(),
                    requestJson,
                    responseJson,
                    "Authorization your are using...",
                    datePattern.format(Date())
                )

                interceptorDebugger.interceptRequest(debugInfo)

                response
            }

        val service = Retrofit.Builder()
            .baseUrl("https://5d26a82ceeb36400145c5c1a.mockapi.io/")
            .client(okHttp.build())
            .build()
            .create(MyService::class.java)

        binding.makeRequest.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                service.getRepository()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Request Done", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.openHistory.setOnClickListener {
            InterceptorDebugListActivity.start(this)
        }
    }
}

interface MyService {
    @GET("api/v1/repository?queryTest=hello&anotherQuery=hey&lastQuery=bye")
    suspend fun getRepository()
}

val RequestBody?.rawJson: String
    get() = Buffer().let { buffer ->
        try {
            this?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: Exception) {
            ""
        }
    }

