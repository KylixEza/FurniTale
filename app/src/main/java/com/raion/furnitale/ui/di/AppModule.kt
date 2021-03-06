package com.raion.furnitale.ui.di

import com.raion.furnitale.core.domain.usecase.ProductInteractor
import com.raion.furnitale.core.domain.usecase.ProductUseCase
import com.raion.furnitale.core.domain.usecase.categories.CategoriesInteractor
import com.raion.furnitale.core.domain.usecase.categories.CategoriesUseCase
import com.raion.furnitale.core.domain.usecase.detail.DetailInteractor
import com.raion.furnitale.core.domain.usecase.detail.DetailUseCase
import com.raion.furnitale.core.domain.usecase.home.HomeInteractor
import com.raion.furnitale.core.domain.usecase.home.HomeUseCase
import com.raion.furnitale.core.domain.usecase.search.SearchInteractor
import com.raion.furnitale.core.domain.usecase.search.SearchUseCase
import com.raion.furnitale.core.ui.*
import com.raion.furnitale.ui.cart.CartViewModel
import com.raion.furnitale.ui.categories.accessories.AccessoriesViewModel
import com.raion.furnitale.ui.categories.bathroom.BathroomViewModel
import com.raion.furnitale.ui.categories.bedroom.BedroomViewModel
import com.raion.furnitale.ui.categories.kitchen.KitchenViewModel
import com.raion.furnitale.ui.categories.livingroom.LivingRoomViewModel
import com.raion.furnitale.ui.categories.outdoor.OutdoorViewModel
import com.raion.furnitale.ui.detail.DetailViewModel
import com.raion.furnitale.ui.home.HomeViewModel
import com.raion.furnitale.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ProductUseCase> { ProductInteractor(get()) }
    factory<HomeUseCase> { HomeInteractor(get()) }
    single<CategoriesUseCase> { CategoriesInteractor(get()) }
    single<DetailUseCase> { DetailInteractor(get()) }
    single<SearchUseCase> { SearchInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { LivingRoomViewModel(get()) }
    viewModel { BedroomViewModel(get()) }
    viewModel { KitchenViewModel(get()) }
    viewModel { BathroomViewModel(get()) }
    viewModel { OutdoorViewModel(get()) }
    viewModel { AccessoriesViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

val adapterModule = module {
    single { CategoryAdapter(get()) }
    single { CartAdapter(get(), get()) }
    single { SelectionAdapter() }
    single { NewProductAdapter() }
    single { CartCheckoutAdapter() }
    single { DiscountAdapter() }
    single { HomeCategoryAdapter() }
    single { SearchAdapter() }
}