package com.shawn.mvvmslideproject.model.data.home

import com.google.gson.annotations.SerializedName


data class AttractionsResponse(
    @SerializedName("Infos")
    val infos: Infos = Infos()
)

data class Infos(
    @SerializedName("Declaration")
    val declaration: Declaration = Declaration(),
    @SerializedName("Info")
    val info: List<Info> = listOf()
)

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
)

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
    val facilities: Facilities = Facilities(),
    @SerializedName("Fax")
    val fax: String? = "",
    @SerializedName("Id")
    val id: String? = "",
    @SerializedName("Images")
    val images: Images = Images(),
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
)

data class Facilities(
    @SerializedName("Facility")
    val facility: List<String> = listOf()
)

data class Images(
    @SerializedName("Image")
    val image: List<Image> = listOf()
)

data class Image(
    @SerializedName("Ext")
    val ext: String? = "",
    @SerializedName("Src")
    val src: String? = "",
    @SerializedName("Subject")
    val subject: String? = ""
)

data class Link(
    @SerializedName("Src")
    val src: String? = "",
    @SerializedName("Subject")
    val subject: String? = ""
)