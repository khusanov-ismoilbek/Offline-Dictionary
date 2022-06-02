package uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.offlinedictionarymvvm.domain.repository.AppRepository
import uz.gita.offlinedictionarymvvm.domain.repository.impl.AppRepositoryImpl
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.DictionaryViewModel

class DictionaryViewModelImpl : DictionaryViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override val backButtonLiveData = MutableLiveData<Unit>()
    override val searchQueryLiveData = MutableLiveData<Cursor>()
    override val allWordsByIdLiveData = MutableLiveData<Cursor>()
    override val favouriteLiveData = MutableLiveData<Cursor>()
    override val noResultLiveData = MutableLiveData<Unit>()
    override val haveResultLiveData = MutableLiveData<Unit>()

    override fun onClickSearch(id: Int, st: String) {
        if (st == "") {
            searchQueryLiveData.value = repository.getAllWordsDataOfItemsByQuery(id)
        } else{
            if (repository.getAllWordsOfItemsBtSearchQueryRepo(id, st).count == 0){
                noResultLiveData.value = Unit
            }else{
                haveResultLiveData.value = Unit
            }
            searchQueryLiveData.value = repository.getAllWordsOfItemsBtSearchQueryRepo(id, st)
        }
    }

    override fun setOnClickFavourite(state: Int, id: Int, id_cat: Int, query: String?) { //query
        if (state == 0) {
            repository.setFavorite(id)
        } else {
            repository.removeFavorite(id)
        }
        if (query == null){
            favouriteLiveData.value = repository.getAllWordsDataOfItemsByQuery(id_cat)
        }else{
            favouriteLiveData.value = repository.getAllWordsOfItemsBtSearchQueryRepo(id_cat, query)
        }
    }

    override fun onClickBackButton() {
        backButtonLiveData.value = Unit
    }

    override fun loadWordsById(id: Int) {
        haveResultLiveData.value = Unit
        allWordsByIdLiveData.value = repository.getAllWordsDataOfItemsByQuery(id)
    }
}