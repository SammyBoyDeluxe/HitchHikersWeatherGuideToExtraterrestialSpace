package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    /**Only used internally for handling configuration changhes, so that they are saved upon exit
     *
     */
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to [Activity.onStart] of the containing
     * Activity's lifecycle.
     */
    override fun onStart() {
        super.onStart()
        view?.findViewById<Button>(R.id.martianWeatherAPIButtonMenuFragment)
            ?.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_martianWeatherFragment) }
        view?.findViewById<Button>(R.id.shallowThoughtAPIButtonMenuFragment)
            ?.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_shallowThoughtFragment) }
        view?.findViewById<Button>(R.id.backToWelcomeFragmentButtonMenuFragment)
            ?.setOnClickListener { findNavController().navigate(R.id.action_menuFragment_to_welcomeFragment) }

    }
}