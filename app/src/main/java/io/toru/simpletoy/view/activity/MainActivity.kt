package io.toru.simpletoy.view.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.Station
import io.toru.simpletoy.network.TokyoMetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    private val recyclerView : RecyclerView by lazy{
        findViewById(R.id.rcv_main) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
    }


    fun test(){
        val retrofit = Retrofit.Builder().baseUrl(URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(TokyoMetro::class.java)
        val call : Call<List<Station>> = service.getStationInformation("TokyoMetro.Marunouchi.Tokyo")
        call.enqueue(object:Callback<List<Station>>{
            override fun onResponse(call: Call<List<Station>>?, response: Response<List<Station>>?) {
                response?.run {
                    for(s in body()){
                        Log.w("TORU", s.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}