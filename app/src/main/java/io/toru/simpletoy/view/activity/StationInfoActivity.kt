package io.toru.simpletoy.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.ImageView
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

//    val lineImage: ImageView by lazy {
//        findViewById(R.id.img_line_color) as ImageView
//    }

    val toolbar:Toolbar by lazy{
        findViewById(R.id.toolbar_station) as Toolbar
    }

    override fun getLayoutID(): Int = R.layout.activity_station_info_2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun initToolbar(stationName:String){
        toolbar.title = stationName
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    fun init(){
        intent.apply {
            val lineCodeColor = intent.getStringExtra("Line_Code")
            fun makeLineImage(lineCode:String){
                Log.w("TORU", "line code color : " + lineCodeColor)
                val lineImage = findViewById(R.id.img_line_color)
                when(lineCode){
                    "C" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_chiyoda_line))
                        setStatusBarColor(R.color.tokyo_chiyoda_line)
                    }
                    "F" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_fukutoshin_line))
                        setStatusBarColor(R.color.tokyo_fukutoshin_line)
                    }
                    "G" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_ginza_line))
                        setStatusBarColor(R.color.tokyo_ginza_line)
                    }
                    "H" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_hibiya_line))
                        setStatusBarColor(R.color.tokyo_hibiya_line)
                    }
                    "M", "m" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity,R.color.tokyo_marunouchi_line))
                        setStatusBarColor(R.color.tokyo_marunouchi_line)
                    }
                    "N" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_nanboku_line))
                        setStatusBarColor(R.color.tokyo_nanboku_line)
                    }
                    "T" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_tozai_line))
                        setStatusBarColor(R.color.tokyo_tozai_line)
                    }
                    "Y" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_yurakucho_line))
                        setStatusBarColor(R.color.tokyo_yurakucho_line)
                    }
                    "Z" -> {
                        lineImage.setBackgroundColor(ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_hanzomon_line))
                        setStatusBarColor(R.color.tokyo_hanzomon_line)
                    }
                }
            }
            makeLineImage(lineCodeColor)

            val stationNameParam = getStringExtra("Station_Info")
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
    }

    fun bindInfoToUI(params:Station){
        txtStationCode.text =  params.stationCode
        txtStationNameJPN.text = (params.title + getString(R.string.station_chinese))
        txtStationNameENG.text = params.sameAs.makeStationName()

        initToolbar(txtStationNameJPN.text.toString())


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

    fun setStatusBarColor(colorCode:Int){
        window.statusBarColor = ContextCompat.getColor(this@StationInfoActivity, R.color.tokyo_ginza_line)
    }

    // extension function
    fun String.makeStationName():String{
        return this.split(":")[1].split(".")[2] + " " + getString(R.string.station_english)
    }

    fun String.makeRailwayLine():String =
            this.split(":")[1]

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}