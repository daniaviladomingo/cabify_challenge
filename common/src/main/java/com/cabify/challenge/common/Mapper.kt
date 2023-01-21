package com.cabify.challenge.common

abstract class Mapper<IN, OUT> {
    abstract fun map(model: IN): OUT
    abstract fun inverseMap(model: OUT): IN

    fun map(values: List<IN>): List<OUT> = values.map { map(it) }
    fun inverseMap(values: List<OUT>): List<IN> = values.map { inverseMap(it) }
}