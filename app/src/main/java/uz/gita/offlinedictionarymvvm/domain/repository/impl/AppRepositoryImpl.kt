package uz.gita.offlinedictionarymvvm.domain.repository.impl

import android.database.Cursor
import uz.gita.offlinedictionarymvvm.data.source.local.AppDatabase
import uz.gita.offlinedictionarymvvm.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {
    companion object {
        private lateinit var instance: AppRepositoryImpl

        fun init() {
            if (::instance.isInitialized)
                return
            instance = AppRepositoryImpl()
        }

        fun getAppRepository(): AppRepositoryImpl = instance
    }

    private val database: AppDatabase = AppDatabase.getDatabase()

    override fun getAllWordsDataOfItemsByQuery(id: Int): Cursor {
        return database.getAllWordsDataOfItemsByQuery(id)
    }

    override fun getAllWordsOfCategoryRepo(): Cursor {
        return database.getAllWordsDataOfCategory()
    }

    override fun getAllWordsOfItemsBtSearchQueryRepo(_id: Int, st: String): Cursor {
        return database.getAllWordsDataOfItemsBySearchQuery(st, _id)
    }

    override fun setFavorite(id: Int) {
        return database.setFavorite(id)
    }

    override fun removeFavorite(id: Int) {
        return database.removeFavorite(id)
    }

    override fun getAllFavoriteWords(): Cursor {
        return database.getAllFavoriteWords()
    }

    override fun getAllFavoriteWordsBySearchQuery(st: String): Cursor {
        return database.getAllFavouriteWordsDataBySearchQuery(st)
    }

    override fun setImage(image: String, id: Int) {
        database.setImage(image, id)
    }

}