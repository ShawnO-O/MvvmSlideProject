package com.shawn.mvvmslideproject.model.data.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionsResponse(
    @SerializedName("Infos")
    val infos: Infos = Infos()
) : Parcelable

@Parcelize
data class Infos(
    @SerializedName("Declaration")
    val declaration: Declaration = Declaration(),
    @SerializedName("Info")
    val info: MutableList<Info> = mutableListOf()
) : Parcelable

@Parcelize
data class Declaration(
    @SerializedName("Official-WebSite")
    val officialWebSite: String? = "",
    @SerializedName("Orgname")
    val orgname: String? = "",
    @SerializedName("SiteName")
    val siteName: String? = "",
    @SerializedName("Total")
    val total: String? = "",
    @SerializedName("Updated")
    val updated: String? = ""
) : Parcelable

@Parcelize
data class Info(
    @SerializedName("Address")
    val address: String? = "",
    @SerializedName("Description")
    val description: String? = "",
    @SerializedName("District")
    val district: String? = "",
    @SerializedName("East-Longitude")
    val eastLongitude: String? = "",
    @SerializedName("Email")
    val email: String? = "",
    @SerializedName("Facilities")
    val facilities: Facilities? = null ,
    @SerializedName("Fax")
    val fax: String? = "",
    @SerializedName("Id")
    val id: String? = "",
    @SerializedName("Images")
    val images: Images?=null ,
    @SerializedName("Modified")
    val modified: String? = "",
    @SerializedName("Name")
    val name: String? = "",
    @SerializedName("North-Latitude")
    val northLatitude: String? = "",
    @SerializedName("Open-Time")
    val openTime: String? = "",
    @SerializedName("Parking")
    val parking: String? = "",
    @SerializedName("Phone")
    val phone: String? = "",
    @SerializedName("Posted")
    val posted: String? = "",
    @SerializedName("Eemind")
    val eemind: String? = "",
    @SerializedName("TYWebsite")
    val tYWebsite: String? = "",
    @SerializedName("Ticket")
    val ticket: String? = "",
    @SerializedName("Zipcode")
    val zipcode: String? = ""
) : Parcelable

sealed class Facilities : Parcelable {
    @Parcelize
    data class FacilitiesData(
        @SerializedName("Facility")
        val facility: List<String> = listOf()
    ) : Facilities()

    @Parcelize
    data class FacilitiesString(val value: String) : Facilities()

    @Parcelize
    data object None : Facilities()  // Represents null or absence of value
}

sealed class Images : Parcelable {
    @Parcelize
    data class ImagesData(
        @SerializedName("Image")
        val images: List<Image> = listOf()
    ) : Images()

    @Parcelize
    data class ImageData(
        @SerializedName("Image")
        val image: Image = Image()
    ) : Images()

    @Parcelize
    data class ImageString(
        @SerializedName("Image")
        val value: String? = null
    ) : Images()

    @Parcelize
    data object None : Images()  // Represents null or absence of value
}


@Parcelize
data class Image(
    @SerializedName("Ext")
    val ext: String?  = null,
    @SerializedName("Src")
    val src: String?  = null,
    @SerializedName("Subject")
    val subject: String?  = null
) : Parcelable


@Parcelize
data class Link(
    @SerializedName("Src")
    val src: String? = "",
    @SerializedName("Subject")
    val subject: String? = ""
) : Parcelable