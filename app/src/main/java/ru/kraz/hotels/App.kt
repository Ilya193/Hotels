package ru.kraz.hotels

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kraz.hotels.di.cloudDataSourcesModule
import ru.kraz.hotels.di.coreModule
import ru.kraz.hotels.di.mappersModule
import ru.kraz.hotels.di.repositoriesModule
import ru.kraz.hotels.di.retrofitModule
import ru.kraz.hotels.di.useCasesModule
import ru.kraz.hotels.di.viewModelsModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                coreModule,
                viewModelsModule,
                useCasesModule,
                repositoriesModule,
                cloudDataSourcesModule,
                retrofitModule,
                mappersModule
            )
        }
    }
}