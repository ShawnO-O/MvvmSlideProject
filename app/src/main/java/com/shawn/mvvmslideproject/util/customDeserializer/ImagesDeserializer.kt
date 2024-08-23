package com.shawn.mvvmslideproject.util.customDeserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.shawn.mvvmslideproject.model.data.home.Image
import com.shawn.mvvmslideproject.model.data.home.Images

class ImagesDeserializer : JsonDeserializer<Images> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: java.lang.reflect.Type,
        context: JsonDeserializationContext
    ): Images {
        return when {
            json.isJsonObject -> {
                val jsonObject = json.asJsonObject
                if (jsonObject.has("Image")) {
                    try {
                        val imagesArray = jsonObject.getAsJsonArray("Image")
                        val images = imagesArray.map { element ->
                            val imageObject = element.asJsonObject
                            Image(
                                ext = imageObject.get("Ext")?.asString,
                                src = imageObject.get("Src")?.asString,
                                subject = imageObject.get("Subject")?.asString
                            )
                        }
                        Images.ImagesData(images = images)
                    } catch (e: ClassCastException) {
                        println(e)
                        val image = jsonObject.getAsJsonObject("Image")
                        val newImage = Image(
                            ext = image.get("Ext")?.asString,
                            src = image.get("Src")?.asString,
                            subject = image.get("Subject")?.asString
                        )
                        Images.ImageData(image = newImage)
                    }
                } else {
                    Images.None
                }
            }

            json.isJsonPrimitive && json.asJsonPrimitive.isString -> {
                // Handle when the JSON is a string
                Images.ImageString(json.asString)
            }

            else -> {
                // Handle null or unexpected types
                Images.None
            }
        }
    }
}