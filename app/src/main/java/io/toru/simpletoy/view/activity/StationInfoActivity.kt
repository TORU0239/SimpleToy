package io.toru.simpletoy.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.Station
import io.toru.simpletoy.network.TokyoMetro
import kotlinx.android.synthetic.main.activity_station_info_2.*
import kotlinx.android.synthetic.main.adapter_line_info.*
import kotlinx.android.synthetic.main.adapter_transfer.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wonyoung on 2017. 3. 23..
 */
class StationInfoActivity : BaseActivity(){
//    val txtStationCode:TextView by lazy {
//        findViewById(R.id.txt_station_code) as TextView
//    }
//
//    val txtStationNameJPN:TextView by lazy {
//        findViewById(R.id.txt_station_jpn_name) as TextView
//    }
//
//    val txtStationNameENG:TextView by lazy {
//        findViewById(R.id.txt_station_eng_name) as TextView
//    }
//
//    val llConnectingLine:LinearLayout by lazy {
//        findViewById(R.id.ll_connecting_railroad) as LinearLayout
//    }
//
//    val txtToolbarStationEngName : TextView by lazy {
//        findViewById(R.id.txt_toolbar_station_eng_name) as TextView
//    }
//
//    val toolbar:Toolbar by lazy{
//        findViewById(R.id.toolbar_station) as Toolbar
//    }

    override fun getLayoutID(): Int = R.layout.activity_station_info_2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun initToolbar(stationName:String){
        txt_toolbar_station_eng_name.text = txt_station_eng_name.text.toString()
        toolbar_station.title = stationName
        setSupportActionBar(toolbar_station)
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
        txt_station_code.text =  params.stationCode
        txt_station_jpn_name.text = (params.title + getString(R.string.station_chinese))
        txt_station_eng_name.text = params.sameAs.makeStationName()

        initToolbar(txt_station_jpn_name.text.toString())

        if(params.connectedRailway.isNotEmpty()){
            rcv_transfer_line.layoutManager = LinearLayoutManager(this@StationInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            rcv_transfer_line.adapter = TransferAdapter(params.connectedRailway)
            rcv_transfer_line.isNestedScrollingEnabled = false
        }
    }

    fun setStatusBarColor(colorCode:Int){
        window.statusBarColor = ContextCompat.getColor(this@StationInfoActivity, colorCode)
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

    class TransferAdapter(var transferLineList:Array<String>): RecyclerView.Adapter<TransferViewHolder>(){
        fun String.makeRailwayLine():String = this.split(":")[1]

        fun String.getLineOperator():String = this.split(".")[0]
        fun String.getLineName():String = this.split(".")[1]

        fun getOperatorLogo(line:String):Int {

            when(line.getLineOperator()){
                "JR-East"-> return R.drawable.jr_east
                "Keio"-> return R.drawable.keio_logo
                "Keisei"-> return R.drawable.keisei_logo

                "Tokyu"-> {
                    when(line.getLineName().toLowerCase()){
                        "toyoko"-> return R.drawable.tokyu_toyoko_logo
                        "denentoshi"-> return R.drawable.tokyu_denentoshi_logo
                        "meguro"-> return R.drawable.tokyu_denentoshi_logo
                    }
                }

                "Tobu"->{
                    when(line.getLineName().toLowerCase()){
                        "tojo"->return R.drawable.tobu_tojo_logo
                        "isesaki"->return R.drawable.tobu_isesaki_logo
                    }
                }

                "TokyoMetro"->{
                    when(line.getLineName().toLowerCase()){
                        "chiyoda"-> return R.drawable.icon_chiyoda
                        "hanzomon"-> return R.drawable.icon_hanzomon
                        "ginza"-> return R.drawable.icon_ginza
                        "marunouchi"-> return R.drawable.icon_marunouchi
                        "fukutoshin"-> return R.drawable.icon_fukutoshin
                        "yurakucho"-> return R.drawable.icon_yurakucho
                        "hibiya"-> return R.drawable.icon_hibiya
                        "tozai"-> return R.drawable.icon_tozai
                        "namboku"-> return R.drawable.icon_namboku
                    }
                }
                "Toei"-> return R.drawable.toei_logo
                else -> return R.drawable.icon_chiyoda
            }
            return 0
        }

        override fun onBindViewHolder(holder: TransferViewHolder?, position: Int) {
            holder?.transferLineName?.text = transferLineList[position].makeRailwayLine().getLineName()
            holder?.transferLineOperator?.setImageResource(getOperatorLogo(transferLineList[position].makeRailwayLine()))
        }

        override fun getItemCount(): Int {
            return transferLineList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransferViewHolder {
            return TransferViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_transfer, parent, false))
        }

    }

    class TransferViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val transferLineName = itemView.txt_transfer_station_name
        val transferLineOperator = itemView.img_transfer_line
    }
}