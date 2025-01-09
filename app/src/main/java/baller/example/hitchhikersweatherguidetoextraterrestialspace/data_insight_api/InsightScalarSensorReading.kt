package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api

import com.google.gson.annotations.SerializedName

/**Per-Sol, per-sensor data object (top-level key = AT or HWS or PRE)
Key Description
av Average of samples over the Sol (°F for AT; m/s for HWS; Pa for PRE)
ct Total number of recorded samples over the Sol
mn Minimum data sample over the sol (same units as av)
mx Maximum data sample over the sol (same units as av)


 *
 */
open abstract class InsightScalarSensorReading(@SerializedName("av") var average: Double?,   // JSON key: av (average)
                                               @SerializedName("ct") var count: Int?,       // JSON key: ct (count)
                                               @SerializedName("mn") var minValue: Double?, // JSON key: mn (minimum value)
                                               @SerializedName("mx") var maxValue: Double?) {





}