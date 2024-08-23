package com.shawn.mvvmslideproject.util.customDeserializer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.shawn.mvvmslideproject.model.data.home.Facilities

class FacilitiesDeserializer : JsonDeserializer<Facilities> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: java.lang.reflect.Type,
        context: JsonDeserializationContext
    ): Facilities {
        return when {
            // If the JSON is an object and has a field "Facility" which is an array
            json.isJsonObject -> {
                val jsonObject = json.asJsonObject
                try {
                    if (jsonObject.has("Facility")) {
                        val facilityArray = jsonObject.getAsJsonArray("Facility")
                        // If "Facility" is an array, map its elements to a list of strings
                        val facilities = facilityArray.map { it.asString }
                        Facilities.FacilitiesData(facility = facilities)
                    } else {
                        Facilities.None
                    }
                } catch (e: Exception) {
                    Log.d("shawnTestError","facil jsonObject:$jsonObject")
                    Facilities.None
                }
            }
            // If the JSON is a primitive and is a string
            json.isJsonPrimitive && json.asJsonPrimitive.isString -> {
                Facilities.FacilitiesString(json.asString)
            }
            // For any other case, represent it as `None`
            else -> {
                Facilities.None
            }
        }
    }
}