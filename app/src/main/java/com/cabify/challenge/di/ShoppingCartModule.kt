package com.cabify.challenge.di

import com.cabify.challenge.domain.repository.ICheckout
import com.cabify.challenge.domain.repository.IShoppingCart
import com.cabify.challenge.shopping_cart.CheckoutImp
import com.cabify.challenge.shopping_cart.ShoppingCartImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShoppingCartModule {
    @Provides
    @Singleton
    fun shoppingCartProvider(): IShoppingCart = ShoppingCartImp()

    @Provides
    @Singleton
    fun checkoutProvider(): ICheckout = CheckoutImp()
}