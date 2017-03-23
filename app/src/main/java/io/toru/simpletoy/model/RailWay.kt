package io.toru.simpletoy.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by wonyoung on 2017. 3. 19..
 */
data class RailWay (@SerializedName("@context") val context:String,
                    @SerializedName("@id") val id:String,
                    @SerializedName("@type") val type:String,
                    @SerializedName("owl:sameAs") val sameAs:String,
                    @SerializedName("dc:title") val title:String,
                    @SerializedName("odpt:stationOrder") val stationOrder:Array<StationOrder>,
                    @SerializedName("odpt:travelTime") val travelTime:Array<TravelTime>,
                    @SerializedName("odpt:lineCode") val lineCode:String,
                    @SerializedName("ug:region") val region:String,
                    @SerializedName("dc:date") val date:String,
                    @SerializedName("odpt:operator") val operator:String) {
    override fun hashCode(): Int {
        return stationOrder.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return stationOrder == other
    }

    override fun toString(): String {
        return "{ \n" +
                "context: $context, id: $id, type:$type, owl:sameAs:$sameAs, dc:title:$title" +
                "odpt:lineCode:$lineCode, ug:region:$region," +
                "dc:date:$date, odpt:operation:$operator"
    }
}

data class StationOrder(@SerializedName("odpt:station") val station:String,
                        @SerializedName("odpt:index") val index:Int) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<StationOrder> = object : Parcelable.Creator<StationOrder> {
            override fun createFromParcel(source: Parcel): StationOrder = StationOrder(source)
            override fun newArray(size: Int): Array<StationOrder?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(station)
        dest?.writeInt(index)
    }
}

data class TravelTime(@SerializedName("odpt:fromStation") val fromStation:String,
                      @SerializedName("odpt:toStation") val toStation:String,
                      @SerializedName("odpt:necessaryTime") val necessaryTime:Int,
                      @SerializedName("odpt:trainType") val trainType:String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<TravelTime> = object : Parcelable.Creator<TravelTime> {
            override fun createFromParcel(source: Parcel): TravelTime = TravelTime(source)
            override fun newArray(size: Int): Array<TravelTime?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readInt(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(fromStation)
        dest?.writeString(toStation)
        dest?.writeInt(necessaryTime)
        dest?.writeString(trainType)
    }
}

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
                   @SerializedName("geo:long") val lng:Float):Parcelable{
    override fun hashCode(): Int {
        return Arrays.hashCode(connectedRailway)
    }

    override fun equals(other: Any?): Boolean {
        return connectedRailway == other
    }

    override fun toString(): String {
        return "sameAs:$sameAs + title:$title"
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Station> = object : Parcelable.Creator<Station> {
            override fun createFromParcel(source: Parcel): Station = Station(source)
            override fun newArray(size: Int): Array<Station?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.createStringArray(), source.readString(), source.createStringArray(), source.readString(), source.createStringArray(), source.readString(), source.readFloat(), source.readFloat())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(type)
        dest?.writeString(sameAs)
        dest?.writeString(date)
        dest?.writeString(title)
        dest?.writeString(region)
        dest?.writeString(operator)
        dest?.writeString(railway)
        dest?.writeStringArray(connectedRailway)
        dest?.writeString(facility)
        dest?.writeStringArray(passengerSurvey)
        dest?.writeString(stationCode)
        dest?.writeStringArray(exit)
        dest?.writeString(context)
        dest?.writeFloat(lat)
        dest?.writeFloat(lng)
    }
}