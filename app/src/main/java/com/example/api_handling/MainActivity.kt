package com.example.api_handling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    var call:Call<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bu_get = findViewById<Button>(R.id.bu_get)
        val bu_post = findViewById<Button>(R.id.bu_post)

        bu_get.setOnClickListener {
            get()
        }

        bu_post.setOnClickListener {
            post()
        }
    }

    fun get()
    {
        val view:View = View.inflate(this,R.layout.progress,null)
        val dialog = AlertDialog.Builder(this)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val c = dialog.setView(view).create()
        c.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        c.setCancelable(false)
        c.show()

        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(
            GsonConverterFactory.create()).build()

        var apiInterface:ApiInterface? = null

        apiInterface = retrofit.create(ApiInterface::class.java)

        call = apiInterface!!.get_data(Random.nextInt(0..50))

        val tv_on_get_title = findViewById<TextView>(R.id.tv_on_get_title)
        val tv_on_get_body = findViewById<TextView>(R.id.tv_on_get_body)
        call!!.clone().enqueue(object :Callback<User>
        {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                tv_on_get_title.text = response.body()?.title
                tv_on_get_body.text = response.body()?.body
                c.hide()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                tv_on_get_title.text = t.message
                tv_on_get_body.text = t.message
                c.hide()
            }

        })
    }

    fun post()
    {
        val title = findViewById<EditText>(R.id.edit_text_tite)

        val body = findViewById<EditText>(R.id.edit_text_body)

        val user_id = findViewById<EditText>(R.id.edit_user_id)

        if (title.text.isNotEmpty() && body.text.isNotEmpty() && user_id.text.isNotEmpty())
        {


            val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(
                GsonConverterFactory.create()).build()

            var apiInterface:ApiInterface? = null

            apiInterface = retrofit.create(ApiInterface::class.java)

            val view:View = View.inflate(this,R.layout.progress,null)
            val dialog = AlertDialog.Builder(this)
            val progress = findViewById<ProgressBar>(R.id.progress)
            val c = dialog.setView(view).create()
            c.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            c.setCancelable(false)
            c.show()


            call = apiInterface!!.post_data(User(user_id.text.toString().toInt(),title.text.toString(),body.text.toString()))

            call!!.clone().enqueue(object :Callback<User>
            {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    Toast.makeText(this@MainActivity,"Post Successful",Toast.LENGTH_LONG).show()
                    c.hide()

                }

                override fun onFailure(call: Call<User>, t: Throwable) {

                    Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_LONG).show()
                    c.hide()

                }

            })
        }
        else
        {
            Toast.makeText(this@MainActivity,"Enter all information",Toast.LENGTH_LONG).show()
        }


    }

}