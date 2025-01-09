package baller.example.hitchhikersweatherguidetoextraterrestialspace.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val _sharedData = MutableLiveData<HashMap<String,Any?>>()
    val sharedData: LiveData<HashMap<String,Any?>> = _sharedData

    // Function to update the shared data
    fun updateSharedData() {
        _sharedData.value = newValue
    }

}
