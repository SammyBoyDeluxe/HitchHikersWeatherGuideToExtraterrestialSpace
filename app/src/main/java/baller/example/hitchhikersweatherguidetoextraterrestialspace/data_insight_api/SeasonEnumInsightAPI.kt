package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

enum class SeasonEnumInsightAPI(val season : String) {
    WINTER("winter"),
    SPRING("spring"),
    SUMMER("summer"),
    FALL("fall");

    companion object {
        /**Returns the value of the entered string as a SeasonEnumInsightAPI
         * -> Returns a object on matching values (toLowerCase), otherwise null
         */
        public fun fromString(input : String) : SeasonEnumInsightAPI?{
            try {
                return valueOf(input.trim().toLowerCase(Locale.current))
            }
            catch (exception : Exception){
                Log.d("ballerapplicationException", exception.cause.toString())
            }
            return null


        }

        /**Returns the enum as a string with a capitalized first letter, for display purposes
         *
         */
        public fun describe(season: SeasonEnumInsightAPI): String{
            if (season == FALL) return "Fall"
            else if(season == SPRING) return "Spring"
            else if(season == SUMMER) return "Summer"
            else return "Winter"

        }

    }

}