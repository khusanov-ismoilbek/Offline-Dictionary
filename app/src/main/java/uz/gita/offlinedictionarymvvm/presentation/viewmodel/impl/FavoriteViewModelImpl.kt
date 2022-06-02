package uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.offlinedictionarymvvm.domain.repository.AppRepository
import uz.gita.offlinedictionarymvvm.domain.repository.impl.AppRepositoryImpl
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.FavoriteViewModel

class FavoriteViewModelImpl : ViewModel(), FavoriteViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override val backButtonLiveData = MutableLiveData<Unit>()
    override val searchQueryLiveData = MutableLiveData<Cursor>()
    override val allFavouriteWordsByIdLiveData = MutableLiveData<Cursor>()
    override val favouriteLiveData = MutableLiveData<Cursor>()
    override val noResultLiveData = MutableLiveData<Unit>()
    override val haveResultLiveData = MutableLiveData<Unit>()

    override fun onClickSearch(st: String) {
        if (st == "") {
            searchQueryLiveData.value = repository.getAllFavoriteWords()
        } else {
            searchQueryLiveData.value = repository.getAllFavoriteWordsBySearchQuery(st)
            if (repository.getAllFavoriteWordsBySearchQuery(st).count == 0){
                noResultLiveData.value = Unit
            }else{
                haveResultLiveData.value = Unit
            }
        }
    }

    override fun setOnClickFavourite(_id: Int, query: String?) {
        repository.removeFavorite(_id)

        if(repository.getAllFavoriteWords().count == 0){
            noResultLiveData.value = Unit
        }else{
            haveResultLiveData.value = Unit
        }
        if (query == null){
            favouriteLiveData.value = repository.getAllFavoriteWords()
        }else{
            favouriteLiveData.value = repository.getAllFavoriteWordsBySearchQuery(query)
        }
    }

    init {
        loadWordsById()
    }

    override fun onClickBackButton() {
        backButtonLiveData.value = Unit
    }

    override fun loadWordsById() {
        allFavouriteWordsByIdLiveData.value = repository.getAllFavoriteWords()
        if (repository.getAllFavoriteWords().count == 0){
            noResultLiveData.value = Unit
        }else{
            haveResultLiveData.value = Unit
        }
    }

}