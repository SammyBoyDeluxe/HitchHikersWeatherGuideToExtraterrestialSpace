package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R

class ShallowThoughtFragment : Fragment() {

    companion object {
        fun newInstance() = ShallowThoughtFragment()
    }

    private val viewModel: ShallowThoughtViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_shallow_thought, container, false)
    }
}