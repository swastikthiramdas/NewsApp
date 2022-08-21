package com.swastik.newsapp


import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            fetchdata()
        }

    }

    fun fetchdata() {


        val url =
            "https://newsdata.io/api/1/news?apikey=pub_104474890b918216aeff0172c5f22ce2d3485&country=in&language=en&category=business"

        val JsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                val newsarrey = response.getJSONArray("results")
                val newsdata = ArrayList<Data>()

                for (i in 0 until newsarrey.length()) {
                    val newslist = newsarrey.getJSONObject(i)
                    val news = Data(
                        newslist.getString("title"),
                        newslist.getString("description"),
                        newslist.getString("link"),
                        newslist.getString("image_url")
                    )
                    newsdata.add(news)
                }

                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.adapter = Adapter(newsdata)

            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(JsonRequest)

    }
}