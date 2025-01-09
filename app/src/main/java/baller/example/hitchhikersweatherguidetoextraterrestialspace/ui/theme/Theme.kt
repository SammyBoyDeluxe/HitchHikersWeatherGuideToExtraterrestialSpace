import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF030303),  // Dark base
    secondary = Color(0xFF74B8CB),  // Light blue
    tertiary = Color(0xFF5AA7B8),  // Slightly darker blue

    background = Color(0xFF000000),  // Pure black for space theme
    surface = Color(0xFF030303),  // Matches primary dark
    onPrimary = Color(0xFFFFFFFF),  // White for text on primary
    onSecondary = Color(0xFFFFFFFF),  // White for text on secondary
    onTertiary = Color(0xFFFFFFFF),  // White for text on tertiary
    onBackground = Color(0xFFFFFFFF),  // White for text on dark background
    onSurface = Color(0xFFFFFFFF)  // White for text on surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF030303),  // Dark base
    secondary = Color(0xFF74B8CB),  // Light blue
    tertiary = Color(0xFF5AA7B8),  // Slightly darker blue

    background = Color(0xFFFFFBFE),  // Default light background
    surface = Color(0xFFFFFBFE),  // Default light surface
    onPrimary = Color(0xFFFFFFFF),  // White for text on primary
    onSecondary = Color(0xFFFFFFFF),  // White for text on secondary
    onTertiary = Color(0xFFFFFFFF),  // White for text on tertiary
    onBackground = Color(0xFF1C1B1F),  // Dark text for light background
    onSurface = Color(0xFF1C1B1F)  // Dark text for light surface
)

@Composable
fun HitchhikersWeatherGuideToExtraterrestialSpaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
