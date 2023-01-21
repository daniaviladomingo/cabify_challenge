package com.cabify.challenge.data.model.mapper

import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.domain.model.Product

class DomainMapper : Mapper<ProductData, Product>() {
    override fun map(model: ProductData): Product = model.run {
        Product(
            code = code,
            name = name,
            price = price
        )
    }

    override fun inverseMap(model: Product): ProductData {
        TODO("Not yet implemented")
    }
}