package com.cabify.challenge.domain.interactors

import com.cabify.challenge.domain.interactors.base.BaseUseCase
import com.cabify.challenge.domain.model.Product
import com.cabify.challenge.domain.repository.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetProductsUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, List<Product>>(dispatcher) {
    override suspend fun block(param: Unit): List<Product> = repository.getProducts()
}