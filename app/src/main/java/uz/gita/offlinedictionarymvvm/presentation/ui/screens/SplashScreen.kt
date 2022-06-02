package uz.gita.offlinedictionarymvvm.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.offlinedictionarymvvm.R
import uz.gita.offlinedictionarymvvm.databinding.ScreenSplashBinding
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.SplashViewModel
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl.SplashViewModelImpl

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openNextScreenLiveData.observe(viewLifecycleOwner, openNextScreenObserver)
    }

    private val openNextScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_categoryScreen)
    }
}