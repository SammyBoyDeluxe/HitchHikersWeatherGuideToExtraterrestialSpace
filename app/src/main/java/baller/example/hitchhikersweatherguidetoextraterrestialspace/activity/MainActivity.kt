package baller.example.hitchhikersweatherguidetoextraterrestialspace.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R

/**Main Activity of the HHWGTETS, responsible for hosting our different fragments
 *
 *
 */

/*AppCompatActivity - Makes sure there´s backwards-compability with earlier API-levels*/
class MainActivity : AppCompatActivity() {
    /*Exposes the navhostfragment for navigation between fragments via actions and nav-graph
      can then be selected via getNavController in the fragment */
    var navHostFragment: NavHostFragment? = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment_container) as? NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(androidx.navigation.fragment.R.id.nav_host_fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}