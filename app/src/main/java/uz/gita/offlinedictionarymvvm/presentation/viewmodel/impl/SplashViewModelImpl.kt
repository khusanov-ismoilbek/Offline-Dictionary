package uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.SplashViewModel

class SplashViewModelImpl : SplashViewModel, ViewModel() {
    override val openNextScreenLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            openNextScreenLiveData.postValue(Unit)
        }
    }
}