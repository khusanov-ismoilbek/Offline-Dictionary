package uz.gita.offlinedictionarymvvm.presentation.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface DictionaryViewModel {
    //Events
    val backButtonLiveData: LiveData<Unit>
    val searchQueryLiveData: LiveData<Cursor>
    val allWordsByIdLiveData: LiveData<Cursor>
    val favouriteLiveData: LiveData<Cursor>
    val noResultLiveData: LiveData<Unit>
    val haveResultLiveData: LiveData<Unit>

    //Action
    fun onClickSearch(id: Int, st: String)
    fun setOnClickFavourite(state: Int, id: Int, id_cat: Int, query: String?)
    fun onClickBackButton()
    fun loadWordsById(id: Int)

}