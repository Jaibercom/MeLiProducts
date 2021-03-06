package com.jaiberyepes.mercadolibre.util

/**
 * Base Mapper interface for data mapping between project layers.
 *
 * @author jaiber.yepes
 */
interface BaseMapper<in A, out B> {

    fun map(type: A): B
}
