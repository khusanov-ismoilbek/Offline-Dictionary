package uz.gita.offlinedictionarymvvm.presentation.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface CategoryViewModel {
    //
    val openDrawerLiveData: LiveData<Unit>//
    val closeDrawerLiveData: LiveData<Unit>//
    val allWordsCategoryLiveData: LiveData<Cursor>
    val openByCategoryLiveData: LiveData<Pair<Int, String>>
    val openPlayMarketLiveData: LiveData<Unit>
    val openAboutDialogLiveData: LiveData<Unit>

    //
    fun openDrawerLayout()
    fun closeDrawerLayout()
    fun onClickBurgerButton()
    fun loadCategory()
    fun openByCategory(id: Int, title: String)
    fun onClickRate()
    fun onClickAbout()
}