package ru.kraz.hotels.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kraz.common.presentation.ToUiMapper
import ru.kraz.common.core.ResourceProvider
import ru.kraz.feature_hotel.presentation.HotelRouter
import ru.kraz.feature_hotel.data.HotelCloudDataSource
import ru.kraz.feature_hotel.data.HotelCloudDataSourceImpl
import ru.kraz.feature_hotel.data.HotelRepositoryImpl
import ru.kraz.feature_hotel.data.HotelService
import ru.kraz.feature_hotel.domain.FetchHotelUseCase
import ru.kraz.feature_hotel.domain.HotelDomain
import ru.kraz.feature_hotel.domain.HotelRepository
import ru.kraz.feature_hotel.presentation.BaseToHotelUiMapper
import ru.kraz.feature_hotel.presentation.HotelUi
import ru.kraz.feature_hotel.presentation.HotelViewModel
import ru.kraz.feature_paid.presentation.PaidRouter
import ru.kraz.feature_paid.presentation.PaidViewModel
import ru.kraz.feature_reservation.data.ReservationCloudDataSource
import ru.kraz.feature_reservation.data.ReservationCloudDataSourceImpl
import ru.kraz.feature_reservation.data.ReservationRepositoryImpl
import ru.kraz.feature_reservation.data.ReservationService
import ru.kraz.feature_reservation.domain.FetchInfoHotelUseCase
import ru.kraz.feature_reservation.domain.ReservationRepository
import ru.kraz.feature_reservation.presentation.BaseToInfoHotelUiMapper
import ru.kraz.feature_reservation.presentation.ReservationRouter
import ru.kraz.feature_reservation.presentation.ReservationViewModel
import ru.kraz.feature_rooms.data.RoomsCloudDataSource
import ru.kraz.feature_rooms.data.RoomsCloudDataSourceImpl
import ru.kraz.feature_rooms.data.RoomsRepositoryImpl
import ru.kraz.feature_rooms.data.RoomsService
import ru.kraz.feature_rooms.domain.FetchRoomsUseCase
import ru.kraz.feature_rooms.domain.RoomsRepository
import ru.kraz.feature_rooms.presentation.BaseToRoomUiMapper
import ru.kraz.feature_rooms.presentation.RoomsRouter
import ru.kraz.feature_rooms.presentation.RoomsViewModel
import ru.kraz.hotels.MainViewModel
import ru.kraz.hotels.Navigation
import ru.kraz.hotels.Screen

val coreModule = module {
    val navigation = Navigation.Base()

    single<Navigation<Screen>> {
        navigation
    }

    single<HotelRouter> {
        navigation
    }

    single<RoomsRouter> {
        navigation
    }

    single<ReservationRouter> {
        navigation
    }

    single<PaidRouter> {
        navigation
    }

    factory<ResourceProvider> {
        ResourceProvider.Base(get())
    }
}

val viewModelsModule = module {
    viewModel<MainViewModel> {
        MainViewModel(get())
    }

    viewModel<HotelViewModel> {
        HotelViewModel(get(), get(), get(), get())
    }

    viewModel<RoomsViewModel> {
        RoomsViewModel(get(), get(), get(), get())
    }

    viewModel<ReservationViewModel> {
        ReservationViewModel(get(), get(), get(), get())
    }

    viewModel<PaidViewModel> {
        PaidViewModel(get())
    }
}

val useCasesModule = module {
    factory<FetchHotelUseCase> {
        FetchHotelUseCase(get())
    }

    factory<FetchRoomsUseCase> {
        FetchRoomsUseCase(get())
    }

    factory<FetchInfoHotelUseCase> {
        FetchInfoHotelUseCase(get())
    }
}

val repositoriesModule = module {
    factory<HotelRepository> {
        HotelRepositoryImpl(get())
    }

    factory<RoomsRepository> {
        RoomsRepositoryImpl(get())
    }

    factory<ReservationRepository> {
        ReservationRepositoryImpl(get())
    }
}

val cloudDataSourcesModule = module {
    factory<HotelCloudDataSource> {
        HotelCloudDataSourceImpl(get())
    }

    factory<RoomsCloudDataSource> {
        RoomsCloudDataSourceImpl(get())
    }

    factory<ReservationCloudDataSource> {
        ReservationCloudDataSourceImpl(get())
    }
}

val retrofitModule = module {
    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<HotelService> {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(get())
            .build()
            .create(HotelService::class.java)
    }

    single<RoomsService> {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(get())
            .build()
            .create(RoomsService::class.java)
    }

    single<ReservationService> {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(get())
            .build()
            .create(ReservationService::class.java)
    }
}

val mappersModule = module {
    factory<ToUiMapper<HotelDomain, HotelUi.Success>> {
        BaseToHotelUiMapper()
    }

    factory<BaseToRoomUiMapper> {
        BaseToRoomUiMapper()
    }

    factory<BaseToInfoHotelUiMapper> {
        BaseToInfoHotelUiMapper()
    }
}