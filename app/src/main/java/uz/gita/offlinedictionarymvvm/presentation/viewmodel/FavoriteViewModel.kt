package uz.gita.offlinedictionarymvvm.presentation.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData

interface FavoriteViewModel {

    val backButtonLiveData: LiveData<Unit>
    val searchQueryLiveData: LiveData<Cursor>
    val allFavouriteWordsByIdLiveData: LiveData<Cursor>
    val favouriteLiveData: LiveData<Cursor>
    val noResultLiveData: LiveData<Unit>
    val haveResultLiveData: LiveData<Unit>

    //Action
    fun onClickSearch(st: String)
    fun setOnClickFavourite(id: Int, query: String?)
    fun onClickBackButton()
    fun loadWordsById()
}