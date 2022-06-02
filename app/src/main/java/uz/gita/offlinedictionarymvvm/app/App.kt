package uz.gita.offlinedictionarymvvm.app

import android.app.Application
import android.content.Context
import uz.gita.offlinedictionarymvvm.data.source.local.AppDatabase
import uz.gita.offlinedictionarymvvm.domain.repository.impl.AppRepositoryImpl


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
        AppRepositoryImpl.init()
        instanse = this
    }

    companion object{
        lateinit var instanse: Context
        private set
    }
}