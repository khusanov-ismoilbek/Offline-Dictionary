package uz.gita.offlinedictionarymvvm.domain.repository

import android.database.Cursor

interface AppRepository {

    fun getAllWordsOfCategoryRepo(): Cursor

    fun getAllWordsDataOfItemsByQuery(id: Int): Cursor
    fun getAllWordsOfItemsBtSearchQueryRepo(_id: Int, st: String): Cursor

    fun setFavorite(id: Int)
    fun removeFavorite(id: Int)

    fun getAllFavoriteWords(): Cursor
    fun getAllFavoriteWordsBySearchQuery(st: String): Cursor

    fun setImage(image: String, id: Int)

}