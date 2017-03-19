package io.toru.simpletoy.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by wonyoung on 2017. 3. 19..
 */

data class Station(@SerializedName("@id")   val id:String,
                   @SerializedName("@type") val type:String,
                   @SerializedName("owl:sameAs") val sameAs:String,
                   @SerializedName("dc:date") val date:String,
                   @SerializedName("dc:title") val title:String,
                   @SerializedName("ug:region") val region:String,
                   @SerializedName("odpt:operator") val operator:String,
                   @SerializedName("odpt:railway") val railway:String,
                   @SerializedName("odpt:connectingRailway") val connectedRailway:Array<String>,
                   @SerializedName("odpt:facility") val facility:String,
                   @SerializedName("odpt:passengerSurvey") val passengerSurvey:Array<String>,
                   @SerializedName("odpt:stationCode") val stationCode:String,
                   @SerializedName("odpt:exit") val exit:Array<String>,
                   @SerializedName("@context") val context:String,
                   @SerializedName("geo:lat") val lat:Float,
                   @SerializedName("geo:long") val lng:Float){

    override fun hashCode(): Int {
        return Arrays.hashCode(connectedRailway)
    }

    override fun equals(other: Any?): Boolean {
        return connectedRailway == other
    }

    override fun toString(): String {
        return "sameAs:$sameAs + title:$title"
    }
}