package com.jaiberyepes.mercadolibre.data.remote.model

import com.squareup.moshi.Json

/**
 * Data class for Characters data entity (network DTO).
 *
 * @author jaiber.yepes
 */
class CharacterResponse(

    @field:Json(name = "char_id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "occupation") val occupation: List<String>,
    @field:Json(name = "img") val image: String,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "nickname") val nickName: String,
    @field:Json(name = "portrayed") val portrayed: String
)