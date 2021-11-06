package com.jaiberyepes.mercadolibre.util.base

import androidx.annotation.StringRes
import com.jaiberyepes.mercadolibre.R

/**
 * Class that holds UI common states.
 *
 * @author jaiber.yepes
 */
sealed class UIState<out T : Any> {
    data class Loading(@StringRes val message: Int = R.string.empty_loading_message) : UIState<Nothing>()
    data class Data<T : Any>(val data: T) : UIState<T>()
    data class Error(@StringRes val message: Int = R.string.general_error_message) : UIState<Nothing>()
}
