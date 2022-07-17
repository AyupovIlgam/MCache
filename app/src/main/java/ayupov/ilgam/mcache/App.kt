package ayupov.ilgam.mcache

import android.app.Application
import ayupov.ilgam.mcache.data.RepositoryImpl
import ayupov.ilgam.mcache.domain.Repository

class App : Application() {

    companion object {
        lateinit var repository: Repository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        repository = RepositoryImpl(applicationContext)
    }
}
