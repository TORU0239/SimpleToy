package io.toru.simpletoy.network

import io.toru.simpletoy.model.RailWay
import io.toru.simpletoy.model.Station
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wonyoung on 2017. 3. 19..
 */
interface TokyoMetro{
    @GET("datapoints/odpt.Station:{station}?acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f")
    fun getStationInformation(@Path("station")station:String):Call<List<Station>>

//    https://api.tokyometroapp.jp/api/v2/datapoints/odpt.Station:TokyoMetro.Marunouchi.Tokyo?acl:consumerKey=ACL_CONSUMERKEY

    @GET("datapoints?rdf:type=odpt:Railway&acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f")
    fun getAllRailwayInformation():Call<List<RailWay>>
//    https://api.tokyometroapp.jp/api/v2/datapoints?&acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f
}