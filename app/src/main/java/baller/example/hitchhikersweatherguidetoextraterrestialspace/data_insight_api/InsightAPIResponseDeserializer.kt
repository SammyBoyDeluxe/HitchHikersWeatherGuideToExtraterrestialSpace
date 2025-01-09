package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class InsightAPIResponseDeserializer : JsonDeserializer<InsightAPIResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): InsightAPIResponse {
        TODO("Not yet implemented")
    }
}