package io.toru.simpletoy.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.StationOrder

class RailwayStationActivity : BaseActivity() {

    private var lineCode:String = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_detail
    }

    private val rcvAllLineInfo:RecyclerView by lazy{
        findViewById(R.id.rcv_line_info_all_station) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    fun initUI(){
        intent?.apply {
            val title = getStringExtra("title")
            val stationOrderArr = getParcelableArrayExtra("stationOrder")
            var travelTimeArr = getParcelableArrayExtra("travelTime")
            lineCode = getStringExtra("line_color")


            fun makeArr(arr:Array<Parcelable>):ArrayList<StationOrder>{
                val result = ArrayList<StationOrder>()
                for(item in arr){
                    val each = item as StationOrder
                    result.add(each)
                }
                return result
            }

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
                setTitle(title)
            }

            rcvAllLineInfo.addItemDecoration(DividerItemDecoration(this@RailwayStationActivity, DividerItemDecoration.VERTICAL))
            rcvAllLineInfo.layoutManager = LinearLayoutManager(this@RailwayStationActivity)
            rcvAllLineInfo.adapter = LineAdapter(makeArr(stationOrderArr), lineCode)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    class LineAdapter(var stationList:ArrayList<StationOrder>, var lineCode:String) : RecyclerView.Adapter<LineViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LineViewHolder {
            return LineViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_line_info, parent, false), lineCode)
        }

        override fun onBindViewHolder(holder: LineViewHolder?, position: Int) {
            holder?.updateView(stationList[position])
        }

        override fun getItemCount(): Int {
            return stationList.size
        }
    }

    class LineViewHolder(view: View, var lineCode:String) : RecyclerView.ViewHolder(view){
        val name: TextView by lazy {
            itemView.findViewById(R.id.txt_station_name) as TextView
        }
        fun updateView(obj:StationOrder){
            obj.station.apply {
                name.text = split(":")[1].split(".")[2]
            }
            itemView.setOnClickListener {
                itemView.context.startActivity(
                        Intent(itemView.context,  StationInfoActivity::class.java)
                                .putExtra("Station_Info", obj.station)
                                .putExtra("Line_Code", lineCode))


            }
        }
    }
}
