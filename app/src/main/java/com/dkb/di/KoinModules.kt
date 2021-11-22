package com.dkb.di

import com.dkb.data.repository.PhotosRepository
import com.dkb.data.server.ServerApiConfiguration
import com.dkb.domain.repository.IPhotosRepository
import com.dkb.domain.usecase.photos.GetPhotosUseCase
import com.dkb.presentation.ui.home.HomeViewModel
import com.dkb.presentation.ui.home.item.PhotosViewHolderProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCasesModule = module {
    single { GetPhotosUseCase(get()) }
}

val repositoryModule = module {
    single { ServerApiConfiguration().configureServerApi(get()) }
    single<IPhotosRepository> { PhotosRepository() }
}

val viewModelsModule = module {
    viewModel { HomeViewModel(get()) }
}
val holderProviders = module {
    factory { PhotosViewHolderProvider() }
}

val koinModules = useCasesModule + repositoryModule + viewModelsModule + holderProviders
