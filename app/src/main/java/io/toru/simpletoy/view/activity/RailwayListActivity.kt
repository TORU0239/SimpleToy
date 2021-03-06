package io.toru.simpletoy.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.RailWay
import io.toru.simpletoy.network.TokyoMetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RailwayListActivity : BaseActivity() {
    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    private val recyclerView : RecyclerView by lazy{
        findViewById(R.id.rcv_main) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        testAllRailWay()
    }

    fun initUI(){
        recyclerView.addItemDecoration(DividerItemDecoration(this@RailwayListActivity, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this@RailwayListActivity)
    }


    fun testAllRailWay(){
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val railway = retrofit.create(TokyoMetro::class.java).getAllRailwayInformation()
        railway.enqueue(object:Callback<List<RailWay>>{
            override fun onFailure(call: Call<List<RailWay>>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<List<RailWay>>?, response: Response<List<RailWay>>?) {
                response?.apply {
                    when(code()){
                        200 -> recyclerView.adapter = MainAdapter(body())
                        else-> Toast.makeText(this@RailwayListActivity, "Server Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    class MainAdapter(var info:List<RailWay>) : RecyclerView.Adapter<RailWayViewHolder>(){

        override fun onBindViewHolder(holder: RailWayViewHolder?, position: Int) {
            holder?.updateView(info[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RailWayViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_main, parent, false)
            return RailWayViewHolder(view)
        }

        override fun getItemCount(): Int {
            return info.size
        }
    }

    class RailWayViewHolder(view: View):RecyclerView.ViewHolder(view){
        lateinit var img: ImageView
        lateinit var lineName: TextView
        lateinit var lineOperator : TextView

        fun updateView(obj:RailWay){
            img = itemView.findViewById(R.id.img_line) as ImageView
            img.tag = obj.lineCode

            when (obj.lineCode){
                "C" -> {
                    img.setImageResource(R.drawable.icon_chiyoda)
                }
                "F" -> {
                    img.setImageResource(R.drawable.icon_fukutoshin)
                }
                "G" -> {
                    img.setImageResource(R.drawable.icon_ginza)
                }
                "H" -> {
                    img.setImageResource(R.drawable.icon_hibiya)
                }
                "M" -> {
                    img.setImageResource(R.drawable.icon_marunouchi)
                }
                "m" -> {
                    img.setImageResource(R.drawable.icon_marunouchi)
                }
                "N" -> {
                    img.setImageResource(R.drawable.icon_namboku)
                }
                "T" -> {
                    img.setImageResource(R.drawable.icon_tozai)
                }
                "Y" -> {
                    img.setImageResource(R.drawable.icon_yurakucho)
                }
                "Z" -> {
                    img.setImageResource(R.drawable.icon_hanzomon)
                }
            }

            lineName = itemView.findViewById(R.id.txt_line_name) as TextView
            lineOperator = itemView.findViewById(R.id.txt_line_operator) as TextView

            if(obj.stationOrder.size <= 5){
                if(obj.lineCode == "m"){
                    obj.title += itemView.context.getString(R.string.branch_chinse)
                }
            }
            else{
                obj.title += itemView.context.getString(R.string.line_chinese)
            }

            lineName.text = obj.title

            lineOperator.text = obj.operator.removeColon()[1]
            itemView.tag = obj
            itemView.setOnClickListener {
                val intent = Intent(it.context, RailwayStationActivity::class.java).apply {
                    putExtra("title", obj.title)
                    putExtra("stationOrder", obj.stationOrder)
                    putExtra("travelTime", obj.travelTime)
                    putExtra("line_color", img.tag as String)

                    Log.w("TORU", "line color :: " + img.tag)
                }
                it.context?.startActivity(intent)
            }
        }

        fun String.removeColon():List<String>{
            return this.split(":")
        }
    }
}