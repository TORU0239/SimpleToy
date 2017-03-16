package io.toru.simpletoy.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.toru.simpletoy.R
import io.toru.simpletoy.model.Person

class MainActivity : AppCompatActivity() {

    private val recyclerView : RecyclerView by lazy{
        findViewById(R.id.rcv_main) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    fun initUI(){
        val list  = ArrayList<Person>()
        list.add(Person("Pluu", "Yanolja"))
        list.add(Person("Kunny", "Lezhin"))
        list.add(Person("Steve", "GDE"))
        list.add(Person("Nurimaru", "Lezhin"))
        list.add(Person("Toru", "HeyBeauty"))
        list.add(Person("Word", "SoCar"))

        // 예를 다시 쓰면
        val adapter = BasicAdapter(list, { higherOrder(it) })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun higherOrder(s:String):Unit{
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("Name", s)
        startActivity(intent)
    }

    class BasicAdapter (val dataList:ArrayList<Person>, val itemClick:(String)->Unit) : RecyclerView.Adapter<MainVH>(){

        override fun onBindViewHolder(holder: MainVH?, position: Int) {
            holder?.updateView(dataList[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainVH {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_main, parent, false)
            return MainVH(itemView, itemClick)
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

    }

    class MainVH(itemView: View, val itemClick:(String)->Unit) : RecyclerView.ViewHolder(itemView) {
        val text:TextView by lazy{
            itemView.findViewById(R.id.txt_vh) as TextView
        }

        val occupation:TextView by lazy{
            itemView.findViewById(R.id.txt_occupation) as TextView
        }

        fun updateView(s:Person){
            text.text = s.name
            occupation.text = s.occupation

            itemView.setOnClickListener {
                itemClick(s.name)
            }
        }
    }
}