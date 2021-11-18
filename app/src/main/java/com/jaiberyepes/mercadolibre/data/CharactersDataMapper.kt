package com.jaiberyepes.mercadolibre.data

import com.jaiberyepes.mercadolibre.data.cache.database.entities.CharacterEntity
import com.jaiberyepes.mercadolibre.data.remote.model.CharacterDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.CharacterResponse
import com.jaiberyepes.mercadolibre.data.remote.model.detail.ProductDetailResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.ProductResponse
import com.jaiberyepes.mercadolibre.data.remote.model.search.SearchResponse
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.BaseMapper

/**
 * Auth Mapper for mapping between data & presentation layers.
 *
 * @author jaiber.yepes
 */
object CharactersDataMapper {

    object ProductsResponseToProductUI : BaseMapper<List<ProductResponse>, List<ProductUI>> {

        override fun map(type: List<ProductResponse>): List<ProductUI> {
            return type.map {
                ProductUI(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    thumbnail = it.thumbnail,
                    imgId = it.imgId
                )
            }
        }
    }

    object ProductDetailResponseToProductDetailUI : BaseMapper<ProductDetailResponse, ProductDetailUI> {

        override fun map(type: ProductDetailResponse): ProductDetailUI {
            return with(type) {
                ProductDetailUI(
                    title = title,
                    price = price
                )
            }
        }
    }


    object CharactersListRemoteToUI : BaseMapper<List<CharacterResponse>, List<CharacterUI>> {

        override fun map(type: List<CharacterResponse>): List<CharacterUI> {
            return type.map {
                CharacterUI(
                    id = it.id,
                    name = it.name,
                    nickName = it.nickName,
                    image = it.image
                )
            }
        }

    }

    object CharacterDetailsListRemoteToUI :
        BaseMapper<List<CharacterDetailResponse>, CharacterDetailsUI> {

        override fun map(type: List<CharacterDetailResponse>): CharacterDetailsUI {
            return with(type[0]) {
                CharacterDetailsUI(
                    id = char_id,
                    name = name,
                    image = photo,
                    nickName = nickname,
                    occupation = occupation,
                    status = status,
                    portrayed = portrayed,
                    isFavorite = false
                )
            }
        }

    }

    object CharacterDetailsCacheToUI :
        BaseMapper<CharacterEntity, CharacterDetailsUI> {

        override fun map(type: CharacterEntity): CharacterDetailsUI {
            return with(type) {
                CharacterDetailsUI(
                    id = id,
                    name = name,
                    image = image,
                    nickName = nickName,
                    occupation = listOf(occupation),
                    status = status,
                    portrayed = portrayed,
                    isFavorite = isFavorite
                )
            }
        }

    }

    object CharactersListCacheToUI : BaseMapper<List<CharacterEntity>, List<CharacterUI>> {

        override fun map(type: List<CharacterEntity>): List<CharacterUI> {
            return type.map {
                CharacterUI(
                    id = it.id,
                    name = it.name,
                    nickName = it.nickName,
                    image = it.image,
                    isFavorite = it.isFavorite
                )
            }
        }

    }

//    object CharactersListUIToCache : BaseMapper<List<CharacterUI>, List<CharacterEntity>> {
//
//        override fun map(type: List<CharacterUI>): List<CharacterEntity> {
//            return type.map {
//                CharacterEntity(
//                    id = it.id,
//                    name = it.name,
//                    nickName = it.nickName,
//                    image = it.image,
//                    isFavorite = it.isFavorite
//                )
//            }
//        }
//
//    }

    object CharactersResponseToCache : BaseMapper<List<CharacterResponse>, List<CharacterEntity>> {

        override fun map(type: List<CharacterResponse>): List<CharacterEntity> {
            return type.map {
                CharacterEntity(
                    id = it.id,
                    name = it.name,
                    nickName = it.nickName,
                    image = it.image,
                    occupation = it.occupation[0],
                    status = it.status,
                    portrayed = it.portrayed,
                    isFavorite = false
                )
            }
        }

    }

    object CharacterDetailsUIToCache : BaseMapper<CharacterDetailsUI, CharacterEntity> {

        override fun map(type: CharacterDetailsUI): CharacterEntity {
            return with(type) {
                CharacterEntity(
                    id = id,
                    name = name,
                    nickName = nickName,
                    image = image,
                    occupation = occupation[0],
                    status = status,
                    portrayed = portrayed,
                    isFavorite = isFavorite
                )
            }
        }

    }

}
