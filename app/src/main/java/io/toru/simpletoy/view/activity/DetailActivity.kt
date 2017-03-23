package io.toru.simpletoy.view.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.toru.simpletoy.R
import io.toru.simpletoy.framework.activity.BaseActivity
import io.toru.simpletoy.model.StationOrder

class DetailActivity : BaseActivity() {
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

            setTitle(title)
//            rcvAllLineInfo.addItemDecoration(DividerItemDecoration(this@DetailActivity, DividerItemDecoration.VERTICAL))
//            rcvAllLineInfo.layoutManager = LinearLayoutManager(this@DetailActivity)
//            rcvAllLineInfo.adapter = LineAdapter(stationOrderArr)코
        }
    }


    class LineAdapter(var stationList:Array<StationOrder>) : RecyclerView.Adapter<LineViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LineViewHolder {
            return LineViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.adapter_line_info, parent, false))
        }

        override fun onBindViewHolder(holder: LineViewHolder?, position: Int) {
            holder?.updateView(stationList[position])
        }

        override fun getItemCount(): Int {
            return stationList.size
        }
    }

    class LineViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView by lazy {
            itemView.findViewById(R.id.txt_station_name) as TextView
        }
        fun updateView(obj:StationOrder){
            name.text = obj.station
        }
    }
}
