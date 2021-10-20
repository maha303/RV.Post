package com.example.rvpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var edName :EditText
    private lateinit var btnAdd : Button

    private lateinit var btnGet : Button

    private lateinit var tv : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edName=findViewById(R.id.edName)
        btnAdd=findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {

            var f=NamesItem(edName.text.toString(),0)
            addUser(f,onResult = {
                edName.setText("")
                Toast.makeText(this@MainActivity,"Saved &&",Toast.LENGTH_LONG).show()

            })
        }

        tv=findViewById(R.id.tv)
        btnGet=findViewById(R.id.btnGet)
        btnGet.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.getData()?.enqueue(object :Callback<List<NamesItem>>{
                override fun onResponse(
                    call: Call<List<NamesItem>>,
                    response: Response<List<NamesItem>>
                ) {
                    var res = response.body()!!
                    var my=StringBuilder()

                    for (i in res){
                        my.append(i.name)
                        my.append("\n")
                        my.append(i.pk)
                        my.append("\n")

                    }
                    tv.text=my
                }

                override fun onFailure(call: Call<List<NamesItem>>, t: Throwable) {


                }
            })

        }
    }

    private fun addUser(f:NamesItem?,onResult:()->Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        if (f != null) {
            apiInterface?.addUser(f)?.enqueue(object :Callback<List<NamesItem>>{
                override fun onResponse(call: Call<List<NamesItem>>, response: Response<List<NamesItem>>) {
                    onResult()
                }
                override fun onFailure(call: Call<List<NamesItem>>, t: Throwable) {
                    onResult()
                     Toast.makeText(this@MainActivity,"Not Saved &&",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}