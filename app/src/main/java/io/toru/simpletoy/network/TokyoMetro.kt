package io.toru.simpletoy.network

import io.toru.simpletoy.model.RailWay
import io.toru.simpletoy.model.Station
import io.toru.simpletoy.model.StationFacility
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by wonyoung on 2017. 3. 19..
 */
interface TokyoMetro{
//    @GET("datapoints/odpt.Station:{station}?acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f")
//    fun getStationInformation(@Path("station")station:String):Call<List<Station>>

    // 모든 노선 정보 가져오기
    @GET("datapoints?rdf:type=odpt:Railway&acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f")
    fun getAllRailwayInformation():Call<List<RailWay>>
//    https://api.tokyometroapp.jp/api/v2/datapoints?rdf:type=odpt:Railway&acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f


    // 역의 상세 정보 가져오기
    @GET("datapoints?rdf:type=odpt:Station")
    fun getSpecificStationInformation(@Query("owl:sameAs")
                                      station:String,
                                      @Query("acl:consumerKey")
                                      key:String):Call<List<Station>>

    // 역의 시설물 정보 가져오기
    @GET("datapoints?rdf:type=odpt:StationFacility")
    fun getStationFacilityInformation(@Query("owl:sameAs")
                                      station:String,
                                      @Query("acl:consumerKey")
                                      key:String):Call<List<StationFacility>>

//    https://api.tokyometroapp.jp/api/v2/datapoints?rdf:type=odpt:StationFacility&owl:sameAs=odpt.StationFacility:TokyoMetro.Ogikubo&acl:consumerKey=f443bac1adecf08c1cdaa735e85182e704e5786bbdac4ebf94777c6d36e22b0f
}