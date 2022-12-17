package com.example.project4


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ViewModel : ViewModel() {
    private val apiKey: String = "ce9834b9d2msh35b73155c38ea24p17cb8ajsn01dd3df894a4"
    private val host: String = "dad-jokes.p.rapidapi.com"
    private var punchline: MutableLiveData<String> = MutableLiveData()
    private var setup: MutableLiveData<String> = MutableLiveData()
    private var image: MutableLiveData<String> = MutableLiveData()
    private val client = OkHttpClient()

    fun getSetup(): MutableLiveData<String> {
        return setup
    }

    fun getPunchline(): MutableLiveData<String> {
        return punchline
    }

//    fun getImage(): MutableLiveData<String> {
//        return image
//    }

    fun currentJoke(jokeType: String) {

        val urlBuilder: HttpUrl.Builder?
        if (jokeType == "none") {
            urlBuilder =
                "https://dad-jokes.p.rapidapi.com/random/joke".toHttpUrlOrNull()!!.newBuilder()
        } else {
            urlBuilder =
                "https://dad-jokes.p.rapidapi.com/joke/search?term=$jokeType".toHttpUrlOrNull()!!
                    .newBuilder()
        }


        val url: String = urlBuilder.build().toString()

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", host)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body!!.string()
                    try {
                        var json = JSONObject(responseData).getJSONArray("body")
                        var numJokes = (json.length()) - 1
                        var index = (0..numJokes).random()
                        setup.postValue(json.getJSONObject(index).getString("setup"))
                        punchline.postValue(json.getJSONObject(index).getString("punchline"))
                    } catch (e: JSONException) {
                        setup.postValue("no jokes")
                    }
                }
            }
        })
    }

//    fun jokeImage() {
//
//        val urlBuilder: HttpUrl.Builder =
//            "https://dad-jokes.p.rapidapi.com/random/joke/png".toHttpUrlOrNull()!!.newBuilder()
//
//        val url: String = urlBuilder.build().toString()
//        val request = Request.Builder()
//            .url(url)
//            .get()
//            .addHeader("X-RapidAPI-Key", apiKey)
//            .addHeader("X-RapidAPI-Host", host)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                call.cancel()
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    val responseData = response.body!!.string()
//                    try {
//                        var json = JSONObject(responseData).getJSONObject("body")
//                        var jstring = json.getString("image")
//                        var png = jstring.drop(22)
//                        println(png)
//                        image.postValue(png)
//
//                    } catch (e: JSONException) {
//                        println("this broke")
//                    }
//
//                }
//            }
//        })
//    }
}
