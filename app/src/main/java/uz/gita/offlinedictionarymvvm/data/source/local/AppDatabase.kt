package uz.gita.offlinedictionarymvvm.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class AppDatabase(context: Context) : DBHelper(context, "data.db", 1) {
    companion object {
        private lateinit var instance: AppDatabase

        fun init(context: Context) {
            if (::instance.isInitialized) return
            instance = AppDatabase(context)
        }

        fun getDatabase(): AppDatabase = instance
    }


    fun getAllWordsDataOfCategory(): Cursor {
        val query = "SELECT * from category"
        return database().rawQuery(query, null)
    }

    fun getAllWordsDataOfItemsByQuery(id: Int): Cursor {
        val query = "SELECT * from items WHERE items.id_cat = $id"
        return database().rawQuery(query, null)
    }

    fun getAllWordsDataOfItemsBySearchQuery(st: String, _id: Int): Cursor {
        val query =
            "SELECT * from items WHERE items.id_cat == $_id AND (items.en like \"%$st%\" Or items.fr like \"%$st%\")"
        return database().rawQuery(query, null)
    }

    fun setFavorite(id: Int) {
        val query = "UPDATE items SET fav = 1 WHERE items._id == $id"
        val cv = ContentValues()
        cv.put("fav", "1")
        database().update("items", cv, "items._id == $id", null)
    }

    fun removeFavorite(id: Int) {
        val query = "UPDATE items SET fav = 0 WHERE items._id == $id"
        val cv = ContentValues()
        cv.put("fav", "0")
        database().update("items", cv, "items._id == $id", null)
    }

    fun getAllFavoriteWords(): Cursor{
        val query = "SELECT * FROM items WHERE items.fav == 1"
        return database().rawQuery(query, null)
    }

    fun getAllFavouriteWordsDataBySearchQuery(st: String): Cursor {
        val query =
            "SELECT * from items WHERE items.fav == 1 AND (items.en like \"%$st%\" Or items.fr like \"%$st%\")"
        return database().rawQuery(query, null)
    }

    fun setImage(image: String, id: Int){
        val cv = ContentValues()
        cv.put("ces", image)
        database().update("category", cv, "category._id == $id",null)
    }
}