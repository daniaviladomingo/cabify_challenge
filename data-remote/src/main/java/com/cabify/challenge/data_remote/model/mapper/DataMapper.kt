package com.cabify.challenge.data_remote.model.mapper

import com.cabify.challenge.common.Mapper
import com.cabify.challenge.data.model.ProductData
import com.cabify.challenge.data_remote.model.ProductApi

class DataMapper : Mapper<ProductApi, ProductData>() {
    override fun map(model: ProductApi): ProductData = model.run {
        ProductData(
            code = code,
            name = name,
            price = price
        )
    }

    override fun inverseMap(model: ProductData): ProductApi {
        TODO("Not yet implemented")
    }
}