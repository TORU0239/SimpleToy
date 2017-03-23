package io.toru.simpletoy.view.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.Station
import io.toru.simpletoy.network.TokyoMetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wonyoung on 2017. 3. 23..
 */
class StationInfoActivity : BaseActivity(){
    val txtStationCode:TextView by lazy {
        findViewById(R.id.txt_station_code) as TextView
    }

    val txtStationNameJPN:TextView by lazy {
        findViewById(R.id.txt_station_jpn_name) as TextView
    }

    val txtStationNameENG:TextView by lazy {
        findViewById(R.id.txt_station_eng_name) as TextView
    }

    val llConnectingLine:LinearLayout by lazy {
        findViewById(R.id.ll_connecting_railroad) as LinearLayout
    }

    override fun getLayoutID(): Int = R.layout.activity_station_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        val stationNameParam = intent.getStringExtra("Station_Info")

        val retrofit = Retrofit.Builder().baseUrl(URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(TokyoMetro::class.java).getSpecificStationInformation(stationNameParam, API_KEY)
                .enqueue(object:Callback<List<Station>>{
                    override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                        t?.printStackTrace()
                    }

                    override fun onResponse(call: Call<List<Station>>?, response: Response<List<Station>>?) {
                        response?.apply {
                            when(code()){
                                200 -> bindInfoToUI(body()[0])

                                else->{
                                    Log.w("TORU", "failed!!!!")
                                }
                            }
                        }

                    }
                })
    }

    fun bindInfoToUI(params:Station){
        txtStationCode.text =  params.stationCode
        txtStationNameJPN.text = (params.title + getString(R.string.station_chinese))
        txtStationNameENG.text = params.sameAs.makeStationName()

        if(params.connectedRailway.isNotEmpty()){
            for(item in 0 until params.connectedRailway.size){
                val transferLine = LayoutInflater.from(this@StationInfoActivity).inflate(R.layout.row_connection_rail, llConnectingLine, false)
                val lineCount = transferLine.findViewById(R.id.txt_line_count) as TextView
                val lineName = transferLine.findViewById(R.id.txt_line_name) as TextView
                lineCount.text = ("#" + (item+1))
                lineName.text = params.connectedRailway[item].makeRailwayLine()
                llConnectingLine.addView(transferLine)
            }

            llConnectingLine.visibility = View.VISIBLE
        }
    }

    // extension function
    fun String.makeStationName():String{
        return this.split(":")[1].split(".")[2] + " " + getString(R.string.station_english)
    }

    fun String.makeRailwayLine():String =
            this.split(":")[1]
}