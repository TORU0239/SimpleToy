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
                    @SerializedName("dc:title") var title:String,
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

data class StationFacility(@SerializedName("@id")   val id:String,
                           @SerializedName("@type") val type:String,
                           @SerializedName("owl:sameAs") val sameAs:String,
                           @SerializedName("dc:date") val date:String,
                           @SerializedName("odpt:barrierfreeFacility") val barrierfreeFacility: Array<Facility>,
                           @SerializedName("odpt:platformInformation") val platformInformation: Array<PlatformInformation>) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<StationFacility> = object : Parcelable.Creator<StationFacility> {
            override fun createFromParcel(source: Parcel): StationFacility = StationFacility(source)
            override fun newArray(size: Int): Array<StationFacility?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readParcelableArray(Facility::class.java.classLoader) as Array<Facility>, source.readParcelableArray(PlatformInformation::class.java.classLoader) as Array<PlatformInformation>)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(type)
        dest?.writeString(sameAs)
        dest?.writeString(date)
        dest?.writeParcelableArray(barrierfreeFacility, 0)
        dest?.writeParcelableArray(platformInformation, 0)
    }

    override fun equals(other: Any?): Boolean {
        return platformInformation == other
    }

    override fun hashCode(): Int {
        return platformInformation.hashCode()
    }
}

data class Facility(@SerializedName("@id")   val id:String,
                    @SerializedName("@type") val type:String,
                    @SerializedName("owl:sameAs") val sameAs:String,
                    @SerializedName("ugsrv:categoryName") val categoryName:String,
                    @SerializedName("odpt:serviceDetail") val serviceDetail:Array<FacilityInfo>,
                    @SerializedName("odpt:placeName") val placeName:String,
                    @SerializedName("odpt:locatedAreaName") val locatedAreaName:String,
                    @SerializedName("ugsrv:remark") val remark:String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Facility> = object : Parcelable.Creator<Facility> {
            override fun createFromParcel(source: Parcel): Facility = Facility(source)
            override fun newArray(size: Int): Array<Facility?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readParcelableArray(FacilityInfo::class.java.classLoader) as Array<FacilityInfo>, source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(type)
        dest?.writeString(sameAs)
        dest?.writeString(categoryName)
        dest?.writeParcelableArray(serviceDetail, 0)
        dest?.writeString(placeName)
        dest?.writeString(locatedAreaName)
        dest?.writeString(remark)
    }

    override fun hashCode(): Int {
        return serviceDetail.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return serviceDetail == other
    }
}

data class FacilityInfo(@SerializedName("ugsrv:serviceStartTime") val serviceStartTime:String,
                        @SerializedName("ugsrv:serviceEndTime") val serviceEndTime:String,
                        @SerializedName("odpt:operationDay") val operationDay:String,
                        @SerializedName("ug:direction") val direction:String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<FacilityInfo> = object : Parcelable.Creator<FacilityInfo> {
            override fun createFromParcel(source: Parcel): FacilityInfo = FacilityInfo(source)
            override fun newArray(size: Int): Array<FacilityInfo?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(serviceStartTime)
        dest?.writeString(serviceEndTime)
        dest?.writeString(operationDay)
        dest?.writeString(direction)
    }
}

data class PlatformInformation(@SerializedName("odpt:railway") val railWay:String,
                               @SerializedName("odpt:carComposition") val carComposition:String,
                               @SerializedName("odpt:carNumber") val carNumber:String,
                               @SerializedName("odpt:railDirection") val railDirection:String,
                               @SerializedName("odpt:transferInformation") val transferInformation:Array<TransferInformation>,
                               @SerializedName("odpt:barrierfreeFacility") val barrierfreeFacility:Array<String>,
                               @SerializedName("odpt:surroundingArea") val surroundingArea:Array<String>) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<PlatformInformation> = object : Parcelable.Creator<PlatformInformation> {
            override fun createFromParcel(source: Parcel): PlatformInformation = PlatformInformation(source)
            override fun newArray(size: Int): Array<PlatformInformation?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readParcelableArray(TransferInformation::class.java.classLoader) as Array<TransferInformation>, source.createStringArray(), source.createStringArray())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(railWay)
        dest?.writeString(carComposition)
        dest?.writeString(carNumber)
        dest?.writeString(railDirection)
        dest?.writeParcelableArray(transferInformation, 0)
        dest?.writeStringArray(barrierfreeFacility)
        dest?.writeStringArray(surroundingArea)
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(transferInformation)
    }

    override fun equals(other: Any?): Boolean {
        return transferInformation == other
    }
}

data class TransferInformation(@SerializedName("odpt:railway") val railWay:String,
                               @SerializedName("odpt:railDirection") val railDirection:String,
                               @SerializedName("odpt:necessaryTime") val necessaryTime:String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<TransferInformation> = object : Parcelable.Creator<TransferInformation> {
            override fun createFromParcel(source: Parcel): TransferInformation = TransferInformation(source)
            override fun newArray(size: Int): Array<TransferInformation?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(railWay)
        dest?.writeString(railDirection)
        dest?.writeString(necessaryTime)
    }
}
