package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api

import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.APIResponse
import com.google.gson.annotations.SerializedName
import java.time.Instant

data class InsightAPIResponse(val solCollection: Collection<Sol>, val validityCheckCollection : Collection<ValidityCheckInsightAPI>,val solKeyCollection : Array<String>) :
    APIResponse() {
    /**er-Sol weather data object (key = <SOL>)
    ● N.B. a Sol will be present at the top level, and in the sol_keys object, if there is at least one sensor with data for that Sol that meet the validity criterion
    ● N.B. only data from sensors that meet the validity criterion will be present under each Sol
    ○ Data from all sensors (AT; HWS; PRE; WD) may not be present under each Sol at the top level
    ○ Data from at least one sensor will be present under each Sol at the top level
    Key Description
    AT Object; per-Sol atmospheric temperature sensor summary data
    HWS Object; per-Sol horizontal wind speed sensor summary data
    PRE Object; per-Sol atmospheric pressure sensor summary data
    WD Object; per-Sol wind direction sensor summary data
    Season String; per-Sol season on Mars; one of [“winter”, “spring”, “summer”, “fall”]
    First_UTC Time of first datum, of any sensor, for the Sol (UTC; YYYY-MM-DDTHH:MM:SSZ)
    Last_UTC Time of last datum, of any sensor, for the Sol (UTC; YYYY-MM-DDTHH:MM:SSZ)
     *
     *
     * Key {
     *
     *          Sol = One martian day = One rotation around its own axis = 24 hours, 39 minutes and 35.244 seconds
     *
     *          Martian season = 1/4 of a Martian full solar orbit rotation period time
     *
     *          UTC = Earth time for first and last sensor reading - Instant in java
     *
     *      }
     */
    class Sol(
        @SerializedName("AT") var temperatureReading: TemperatureReading?,
        @SerializedName("HWS") var horizontalWindSpeedReading: HorizontalWindSpeedReading?,
        @SerializedName("PRE") var atmosphericPressureReading: AtmosphericPressureReading?,
        @SerializedName("WD") var windDirectionReading: WindDirectionReading?,
        @SerializedName("Season") var season: SeasonEnumInsightAPI?,
        @SerializedName("First_UTC") var firstUTC: Instant?,
        @SerializedName("Last_UTC") var lastUTC: Instant?
    ) {


        /**     TemperatureReading extends the InsightScalarSensor(Holds values for average, maxval, minval
         *      and sampleCount(of sensor at soldate)
         *
         *      TemperatureReading has extension functions (<instantiated object variable>.asCelsius/Fahrenheit) which can offer a converted version of the
         *      sensor measurements, in (degrees) Celsius or Fahrenheit
         *
         *      The object will start with fahrenheit-values and each instance (of a TempereatureReading object)
         *      will hold its latest assigned value upon conversion
         */
         class TemperatureReading(private var isFahrenheit : Boolean = true,  average : Double?, count: Int?, minValue: Double?, maxValue: Double?) :
            InsightScalarSensorReading(average, count, minValue, maxValue) {

            /** Conversion Formula : value (Celsius) = (value (Fahrenheit) - 32) / (9/5)
             *
             *     The object acted on is guaranteed to have units in celsius.
             *     Note : Instance of returned TemperatureReading will have values according to the
             *     result of its last conversion
             *
             */

             fun asCelsius() : TemperatureReading {
                /*If the unit is in fahrenheit, we want to convert */
                return when {
                    this.isFahrenheit == true -> {
                            val tempTemperatureReading = TemperatureReading(average = toCelsius(this.average),
                                minValue = toCelsius(this.minValue),
                                maxValue = toCelsius(this.maxValue),
                                count = this.count)

                            tempTemperatureReading.isFahrenheit = false

                        return tempTemperatureReading
                        }
                        /*Implicit isFahrenheit != true, already in celsius*/
                        else  -> this


                }
            }
            /** Performs the algebraic operation on the element and returns the result,
             *  if element is null -> null is returned
             */
            private fun toCelsius(temperatureReading: Double?) : Double? {

                        if (temperatureReading != null) {
                            return (temperatureReading.minus(32.0)).div((9.0 / 5.0))
                        }
                        else return null

            }

            /** Conversion Formula: value (Fahrenheit) = value (Celsius) × (9/5) + 32
             *
             * The object returned is guaranteed to have units in Fahrenheit.
             * Note: Instance of TemperatureReading will have values according to the
             * result of its last conversion.
             */
            public fun asFahrenheit() : TemperatureReading {
                /* If the unit is in Celsius, we want to convert */

              when {


                    !this.isFahrenheit -> {
                        /* Convert all Celsius values to Fahrenheit, Set isFahrenheit to true, since it's now Fahrenheit  */
                        val tempTemperatureReading: TemperatureReading = TemperatureReading(
                            average = toFahrenheit(this.average),
                            minValue = toFahrenheit(this.minValue),
                            maxValue = toFahrenheit(this.maxValue),
                            count = this.count,
                            isFahrenheit = true
                        )





                        return tempTemperatureReading


                    }


                 else -> {return this}
                 }
            }

            /** Performs the algebraic operation on the element and returns the result,
             *  if element is null -> null is returned.
             */
            private fun toFahrenheit(temperatureReading: Double?): Double? {
                if (temperatureReading != null) {
                    return temperatureReading.times(9.0 / 5.0).plus(32.0)
                } else {
                    return null
                }
            }






        }


        /**                     Key Description

        av Average of samples over the Sol (°F for AT; m/s for HWS; Pa for PRE)
        ct Total number of recorded samples over the Sol
        mn Minimum data sample over the sol (same units as av)
        mx Maximum data sample over the sol (same units as av)


         *
         */
        class HorizontalWindSpeedReading(
            average: Double,
            count: Int,
            minValue: Double,
            maxValue: Double
        ) : InsightScalarSensorReading(average, count, minValue, maxValue) {

        }


        /**                         Key Description
        av Average of samples over the Sol (°F for AT; m/s for HWS; Pa for PRE)
        ct Total number of recorded samples over the Sol
        mn Minimum data sample over the sol (same units as av)
        mx Maximum data sample over the sol (same units as av)
         *
         */
        class AtmosphericPressureReading(
            average: Double,
            count: Int,
            minValue: Double,
            maxValue: Double
        ) : InsightScalarSensorReading(average, count, minValue, maxValue) {
            private val earthAtmosphericPressure : Double = 101400.0

            /**Returns the reading (average, minValue or maxValue) as a fraction relative to
             *  Earth´s atmospheric pressure
             */
            public fun AtmosphericPressureReading.asFractionOfEarthAtmosphere(pressureReadingOnMars : Double) : Double{
                return pressureReadingOnMars / earthAtmosphericPressure

            }

            /**Returns the reading (average, minValue or maxValue) in kPa (kilo-pascal)
             *
             */
            public fun AtmosphericPressureReading.asKiloPa(pressureReadingOnMars : Double) : Double{
                return pressureReadingOnMars / 1000.0

            }



        }


        /**Per-Sol, per-sensor data object for wind direction (top-level key = WD)
        Key Description
        <compass_pt_no> Object; key is a string of one or two decimal digits indicating the ordinal of a 16-wind compass rose from North clockwise, e.g.
        “1”, “2”, …, “16”
        N.B. These data could be used to create a wind rose histogram
        most_common Object or null; usually duplicates whichever of the <compass_pt_no> objects has the highest count (ct)
        The most_common key will always be present in any Sol, but it may have the value null if the wind direction data for that Sol do
        not pass the validity checks
         *
         */
        data class WindDirectionReading(    @SerializedName("compass_points") val compassPointarray: Array<CompassPointObject>)   {

            /**Sol wind direction compass point object (key <compass_pt_no> or most_common in WD)
                                            Key Description
            compass_degrees Number; the compass direction of the center of the compass point; degrees
            ● N.B. the wind is blowing from this direction ±11.25°
            compass_point String; the name of the compass point e.g. “N” for North, or “ESE” for East-SouthEast
            compass_right Number; the positive-right (positive-east), horizontal component of a unit vector indicating the direction of the compass point
            compass_up Number; the positive--up (positive-north), vertical component of a unit vector indicating the direction of the compass point
            ct Number; the number of samples for the Sol in the 11.25° around this compass point
             *
             */
            data class CompassPointObject( @SerializedName("compass_point") val compassPoint: CardinalDirectionEnumInsightAPI, // JSON key: compass_point
                                           @SerializedName("compass_right") val compassRight: Double,                          // JSON key: compass_right
                                           @SerializedName("compass_up") val compassUp: Double,                                // JSON key: compass_up
                                           @SerializedName("count") val count: Int) {




            }


        }




    }

    /**                                 Key Description
    <SOL> Object; key is a string of one or more decimal digits
    There will typically be more Sol keys here than in the top-level <SOL> keys
    sol_hours_required Number; typically 18; number of hours with at least one sensor datum required for that Sol’s sensor data to be considered valid
    sols_checked Array of strings e.g. [“258”,”260”,”...,”265”]
    Sols, as strings, that were checked against the validity criterion. This array indicates which <SOL> keys are present in the
    validity_checks object.



    Per-Sol validity checks (key <SOL> in top-level object “validity_checks”; see Caveats below)
                                        Key Description
    AT Object; per-Sol validity check for atmospheric temperature sensor
    HWS Object; per-Sol validity check for horizontal wind speed sensor
    PRE Object; per-Sol validity check for atmospheric pressure sensor
    WD Object; per-Sol validity check for wind direction sensor
    Per-Sol, per-sensor validity check (key AT or HWS or PRE or WD in previous table; see Caveats below)
                                        Key Description

    sol_hours_with_data Array of numbers; number indicating which hours have at least one datum recorded for this Sol and sensor; values are 0
    through 23.

    valid Boolean value; true if there are at least <sol_hours_required> hours (typically 18) with at least one datum recorded for this Sol
    and sensor.


     Example (From APIDocumentation : See (baller.example.<app-name>.apiclients.)inSight Weather API Document.pdf):

    "validity_checks": {
    ### Start of validity check data
    "sol_hours_required": 18, ### Input parameter to validity check algorithm: sensor
    ### data need to be present in at least 18 of 24 (martian)
    ### hours of a Sol for summary data to be present for that
    ### sensor in that Sol above
    "sols_checked": ["258","259","260","261","262","263","264","265"] ### N.B. to get seven Sols of data, eight days are
    ### checked, because the most recent Sol is
    ### usually incomplete. As it turns out in this
    ### example, there are data available for at least
    ### one sensor for all Sols from 265 back to 259,
    ### so summary data for Sol 258 are not included
    ### above.
    "258": { ### Validity check result data for Sol 258 are present,
    ### even though its summary data are not used
    "AT": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "HWS": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "PRE": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "WD": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true }
    },
    "259": { ### Start of validity check result data for Sol 259; data are
    ###present for all 24 hours for all sensors, so all sensor
    ### keys here (AT, HWS, PRE, WD) have valid = true
    "AT": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "HWS": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "PRE": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true },
    "WD": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],
    "valid": true }
    },
    "260": { ### Start of validity check result data for Sol 259
    "AT": { "sol_hours_with_data": [0,1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,18,19,20,21,22,23], ### AT data are present in 22 of 24 hours, which is
    "valid": true }, ### greater than 18 (sol_hours_required) above, so
    ### valid = true, and AT summary data are present for
    ### Sol 260 above
    "HWS": { "sol_hours_with_data": [ 0,1,2,3,4,5,6,7,15,16,17,18,19,20,21,22,23], ### HWS data are present in 17 of 24 hours, which is
    "valid": false }, ### les than 18 (sol_hours_required) above, so
    ### valid = false, and HWS summary data are not present
    ### for Sol 260 above
    "PRE": { "sol_hours_with_data": [ 0,1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,18,19,20,21,22,23], ### PRE data are present in 22 of 24 hours,
    "valid": true }, ### so valid = true
    "WD": { "sol_hours_with_data": [ 0,1,2,3,4,5,6,7,15,16,17,18,19,20,21,22,23], ### WE data are present in 17 of 24 hours,
    "valid": false } ### so valid = false
    },
    "261": {...}, ### Validity check result data for Sols 261 and later are
    "262": {...}, ### excluded for this example
    "263": {...},
    "264": {...},
    "265": {...}
    }
     *
     */
    class ValidityCheckInsightAPI(
        @SerializedName("sol_hours_required") val solHoursRequired: Int,   // JSON key: sol_hours_required
        @SerializedName("sols_checked") val solsChecked: Array<String>,   // JSON key: sols_checked
        @SerializedName("sols_validity") val solsValidity: Map<String, SolValidity> // JSON key: per-Sol validity (dynamic keys like "258", "259", etc.)
    ) {

        class SolValidity(
            @SerializedName("AT") val AT: SensorValidity?,   // JSON key: AT
            @SerializedName("HWS") val HWS: SensorValidity?, // JSON key: HWS
            @SerializedName("PRE") val PRE: SensorValidity?, // JSON key: PRE
            @SerializedName("WD") val WD: SensorValidity?    // JSON key: WD
        )

        /**Represents the data available in relation to the valid-criterion set for the API-Response,
         *  contains a list of integers x = [0,24[ (0 inclusive, 24 exluded), representing the martian hours
         *  that we have data from the sensor during that particular sol.
         *  The valid Boolean-value represents its comparison to the valid criterion ;
         *
         *                              Example :
         *
         *            val vCheck : ValidityCheckInsightAPI =  ValidityCheckInsightAPI(
         *         @SerializedName("sol_hours_required") val solHoursRequired: Int,   // JSON key: sol_hours_required
         *         @SerializedName("sols_checked") val solsChecked: Array<String>,   // JSON key: sols_checked
         *         @SerializedName("sols_validity") val solsValidity: Map<String, SolValidity> // JSON key: per-Sol validity (dynamic keys like "258", "259", etc.)
         *         )
         *
         *         Where sol_hours_required = 18, means that a SensorValidityObject for a certain Sensor and Martian day only will have
         *         valid = true if and only if sol_hours_with_data >= 18
         */
        class SensorValidity(
            @SerializedName("sol_hours_with_data") val solHoursWithData: List<Int>, // JSON key: sol_hours_with_data
            @SerializedName("valid") val valid: Boolean                             // JSON key: valid
        )
    }


}
