package com.cabify.challenge.di

import com.cabify.challenge.di.annotations.UseCaseAddProductToShoppingCart
import com.cabify.challenge.di.annotations.UseCaseRemoveProductFromShoppingCart
import com.cabify.challenge.domain.interactors.AddProductToShoppingCartUseCase
import com.cabify.challenge.domain.interactors.ClearShoppingCartUseCase
import com.cabify.challenge.domain.interactors.GetCheckOutUseCase
import com.cabify.challenge.domain.interactors.GetProductsUseCase
import com.cabify.challenge.domain.interactors.RemoveProductFromShoppingCartUseCase
import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.interactors.base.BaseUseCaseFlow
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.model.ProductCheckout
import com.cabify.challenge.domain.repository.ICheckout
import com.cabify.challenge.domain.repository.IRepository
import com.cabify.challenge.domain.repository.IShoppingCart
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @Provides
    fun getGetProductListUseCaseProvider(
        repository: IRepository,
        dispatcher: CoroutineDispatcher,
    ): BaseUseCase<Unit, List<Product>> =
        GetProductsUseCase(
            repository = repository,
            dispatcher = dispatcher
        )

    @Provides
    @UseCaseAddProductToShoppingCart
    fun getAddProductToShoppingCartUseCaseProvider(
        shoppingCart: IShoppingCart,
        dispatcher: CoroutineDispatcher,
    ): BaseUseCase<Product, Unit> =
        AddProductToShoppingCartUseCase(
            shoppingCart = shoppingCart,
            dispatcher = dispatcher
        )

    @Provides
    @UseCaseRemoveProductFromShoppingCart
    fun removeProductFromShoppingCartUseCaseProvider(
        shoppingCart: IShoppingCart,
        dispatcher: CoroutineDispatcher,
    ): BaseUseCase<Product, Unit> =
        RemoveProductFromShoppingCartUseCase(
            shoppingCart = shoppingCart,
            dispatcher = dispatcher
        )

    @Provides
    fun getCheckOutUseCaseUseCaseProvider(
        shoppingCart: IShoppingCart,
        checkout: ICheckout,
        dispatcher: CoroutineDispatcher,
    ): BaseUseCaseFlow<Unit, List<ProductCheckout>> =
        GetCheckOutUseCase(
            shoppingCart = shoppingCart,
            checkout = checkout,
            dispatcher = dispatcher
        )

    @Provides
    fun clearShoppingCartUseCaseProvider(
        shoppingCart: IShoppingCart,
        dispatcher: CoroutineDispatcher,
    ): BaseUseCase<Unit, Unit> =
        ClearShoppingCartUseCase(
            shoppingCart = shoppingCart,
            dispatcher = dispatcher
        )

    @Provides
    fun dispatcherProvider(): CoroutineDispatcher = Dispatchers.IO
}