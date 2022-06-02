package uz.gita.offlinedictionarymvvm.presentation.viewmodel.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.offlinedictionarymvvm.R
import uz.gita.offlinedictionarymvvm.domain.repository.AppRepository
import uz.gita.offlinedictionarymvvm.domain.repository.impl.AppRepositoryImpl
import uz.gita.offlinedictionarymvvm.presentation.viewmodel.CategoryViewModel

class CategoryViewModelImpl : CategoryViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override val openDrawerLiveData = MutableLiveData<Unit>()
    override val closeDrawerLiveData = MutableLiveData<Unit>()
    override val allWordsCategoryLiveData = MutableLiveData<Cursor>()
    override val openByCategoryLiveData = MutableLiveData<Pair<Int, String>>()
    override val openPlayMarketLiveData = MutableLiveData<Unit>()
    override val openAboutDialogLiveData = MutableLiveData<Unit>()


    init {
        loadCategory()

        repository.setImage(R.drawable.a34.toString(), 1)
        repository.setImage(R.drawable.a8.toString(), 2)
        repository.setImage(R.drawable.a33.toString(), 3)
        repository.setImage(R.drawable.a13.toString(), 4)
        repository.setImage(R.drawable.a20.toString(), 5)
        repository.setImage(R.drawable.a18.toString(), 6)
        repository.setImage(R.drawable.a24.toString(), 7)
        repository.setImage(R.drawable.a22.toString(), 8)
        repository.setImage(R.drawable.a23.toString(), 9)
        repository.setImage(R.drawable.a17.toString(), 10)
        repository.setImage(R.drawable.a21.toString(), 11)
        repository.setImage(R.drawable.a9.toString(), 12)
        repository.setImage(R.drawable.a7.toString(), 13)
        repository.setImage(R.drawable.a1.toString(), 14)
        repository.setImage(R.drawable.a28.toString(), 15)
        repository.setImage(R.drawable.a10.toString(), 16)
        repository.setImage(R.drawable.a32.toString(), 17)
        repository.setImage(R.drawable.a12.toString(), 18)
        repository.setImage(R.drawable.a16.toString(), 19)
        repository.setImage(R.drawable.a15.toString(), 20)
        repository.setImage(R.drawable.a14.toString(), 21)
        repository.setImage(R.drawable.a2.toString(), 22)
        repository.setImage(R.drawable.a4.toString(), 23)
        repository.setImage(R.drawable.a29.toString(), 24)
        repository.setImage(R.drawable.a6.toString(), 25)
        repository.setImage(R.drawable.a30.toString(), 26)
        repository.setImage(R.drawable.a5.toString(), 27)
        repository.setImage(R.drawable.a31.toString(), 28)
        repository.setImage(R.drawable.a3.toString(), 29)
        repository.setImage(R.drawable.a11.toString(), 30)
        repository.setImage(R.drawable.a27.toString(), 31)
        repository.setImage(R.drawable.a26.toString(), 32)
        repository.setImage(R.drawable.a19.toString(), 33)
        repository.setImage(R.drawable.a3.toString(), 34)
    }

    override fun openDrawerLayout() {
        openDrawerLiveData.value = Unit
    }

    override fun closeDrawerLayout() {
        closeDrawerLiveData.value = Unit
    }

    override fun onClickBurgerButton() {
        openDrawerLiveData.value = Unit
    }

    override fun loadCategory() {
        allWordsCategoryLiveData.value = repository.getAllWordsOfCategoryRepo()
    }

    override fun openByCategory(id: Int, title: String) {
        openByCategoryLiveData.value = id to title
    }

    override fun onClickRate() {
        openPlayMarketLiveData.value = Unit
    }

    override fun onClickAbout() {
        openAboutDialogLiveData.value = Unit
    }
}