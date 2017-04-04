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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.Station
import io.toru.simpletoy.model.StationFacility
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
class StationInfoActivity : BaseActivity() {

    private var stationInfo:String = ""

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
            stationInfo = stationNameParam

            val retrofit = Retrofit.Builder().baseUrl(URL_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val request = retrofit.create(TokyoMetro::class.java)
//            odpt.Station:TokyoMetro.Ginza.OmoteSando

            request.getSpecificStationInformation(stationNameParam, API_KEY)
                    .enqueue(object:Callback<List<Station>>{
                        override fun onFailure(call: Call<List<Station>>?, t: Throwable?) {
                            t?.printStackTrace()
                        }

                        override fun onResponse(call: Call<List<Station>>?, response: Response<List<Station>>?) {
                            response?.apply {
                                when(code()){
                                    200 -> {
                                        bindInfoToUI(body()[0])

                                        // test code for facility
                                        val station = stationNameParam.split(":")[1]
                                        val tokyoMetro = station.split(".")[0]
                                        val stationName = station.split(".")[2]
                                        val stationInfoParam = "odpt.StationFacility:".plus(tokyoMetro).plus(".").plus(stationName)
                                        request.getStationFacilityInformation(stationInfoParam, API_KEY)
                                                .enqueue(object:Callback<List<StationFacility>>{
                                                    override fun onResponse(call: Call<List<StationFacility>>?, response: Response<List<StationFacility>>?) {
                                                        response?.apply {
                                                            when(code()){
                                                                200-> bindFacilityInfoToUI(body())
                                                                else->Log.w("TORU", "failed facility!!!!")
                                                            }
                                                        }
                                                    }

                                                    override fun onFailure(call: Call<List<StationFacility>>?, t: Throwable?) {
                                                        t?.printStackTrace()
                                                    }
                                                })
                                    }
                                    else->{
                                        Log.w("TORU", "failed!!!!")
                                    }
                                }
                            }

                        }
                    })
        }
    }

    fun bindFacilityInfoToUI(params:List<StationFacility>){
        if(params.isNotEmpty()){
            val railInfo = stationInfo.split(":")[1]
            railInfo.split(".").let{
                val railWayName = it[0].insertDot(it[1])  // 현재의 stationInfo 에서 노선 이름을 구한다.
                var direction : String = ""

                params[0].platformInformation.filter{ it.railWay.contains(railWayName)}.forEachIndexed { _, _ ->

//                    Log.w("TORU", info.railWay)
//                    Log.w("TORU", info.railDirection)
//                    Log.w("TORU", info.carComposition)
//                    Log.w("TORU", info.carNumber + "호차")

                }
            }

            for(item in params[0].platformInformation){
                Log.w("TORU", "======================================")
                Log.w("TORU", "car composition : " + item.carComposition)
                Log.w("TORU", "car number :: " + item.carNumber)
                Log.w("TORU", "railway :: " + item.railWay)
                Log.w("TORU", "rail direction :: " + item.railDirection)

                if(item.barrierfreeFacility != null && item.barrierfreeFacility.isNotEmpty()){
                    for(item2 in item.barrierfreeFacility){
                        Log.w("TORU", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                        Log.w("TORU", "barrierfreeFacility :: " + item2)
                    }
                    Log.w("TORU", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                }

                item.transferInformation?.let{
                    if(it.isNotEmpty()){
                        for(item2 in it){
                            Log.w("TORU", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                            Log.w("TORU", "transfer necessary time :: " + item2.necessaryTime)
                            Log.w("TORU", "transfer rail direction :: " + item2.railDirection)
                            Log.w("TORU", "transfer rail way :: " + item2.railWay)

                        }
                        Log.w("TORU", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                    }
                }
            }
            Log.w("TORU", "======================================")
        }
    }

    fun bindInfoToUI(params:Station){
        txt_station_code.text =  params.stationCode
        txt_station_jpn_name.text = (params.title + getString(R.string.station_chinese))
        txt_station_eng_name.text = params.sameAs.makeStationName()

        initToolbar(txt_station_jpn_name.text.toString())

        if(params.connectedRailway.isNotEmpty()){
            rcv_transfer_line.visibility = View.VISIBLE
            rcv_transfer_line.layoutManager = LinearLayoutManager(this@StationInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            rcv_transfer_line.adapter = TransferAdapter(params.connectedRailway)
            rcv_transfer_line.isNestedScrollingEnabled = false
        }
        else{
            rcv_transfer_line.visibility = View.GONE
        }

        for(item in params.exit){
            Log.w("TORU", "exit item: " + item)
        }

        Log.w("TORU", "facility item: " + params.facility)


        for(item in params.passengerSurvey){
            Log.w("TORU", "passenger item: " + item)
        }

        (supportFragmentManager.findFragmentById(R.id.map_simple) as SupportMapFragment).getMapAsync {
            it.apply{
                isMyLocationEnabled = false
                with(uiSettings){
                    isScrollGesturesEnabled = false
                    isZoomControlsEnabled = false
                }
                val latlng = LatLng(params.lat.toDouble(), params.lng.toDouble())
                moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f))
                addMarker(MarkerOptions().draggable(false).position(latlng))
            }
        }
    }

    fun setStatusBarColor(colorCode:Int){
        window.statusBarColor = ContextCompat.getColor(this@StationInfoActivity, colorCode)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // extension function
    fun String.makeStationName():String{
        return this.split(":")[1].split(".")[2] + " " + getString(R.string.station_english)
    }

    fun String.makeRailwayLine():String =
            this.split(":")[1]

    fun String.insertDot(str1:String):String{
        return "$this.$str1"
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
                "Seibu"->return R.drawable.seibu_ikebukuro
                "Rinkai" -> return R.drawable.rinkai_logo
                "Yurikamome" -> return R.drawable.yurikamome_logo

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
                "Toei"-> {
                    when(line.getLineName().toLowerCase()){
                        "asakusa" -> return R.drawable.toei_asakusa
                        "mita" -> return R.drawable.toei_mita
                        "shinjuku" -> return R.drawable.toei_shinjuku
                        "oedo" -> return R.drawable.toei_oedo
                    }
                }

                else -> {
                    Log.w("TORU", line.getLineOperator());
                    return R.drawable.toei_logo
                }
            }
            return R.drawable.toei_logo
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