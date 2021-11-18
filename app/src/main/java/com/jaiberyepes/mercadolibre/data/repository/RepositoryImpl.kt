package com.jaiberyepes.mercadolibre.data.repository

import com.jaiberyepes.mercadolibre.data.CharactersDataMapper
import com.jaiberyepes.mercadolibre.data.cache.CacheDataSource
import com.jaiberyepes.mercadolibre.data.remote.RemoteDataSource
import com.jaiberyepes.mercadolibre.domain.repository.Repository
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDescriptionUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductDetailUI
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.util.Output
import timber.log.Timber
import javax.inject.Inject

/**
 * Characters related Repository implementation.
 *
 * @author jaiber.yepes
 */
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
) : Repository {

    override suspend fun getProductsFromSearch(keyword: String, offset: Int): Output<List<ProductUI>> {
        Timber.d("getProductsFromSearch")
        val productsRemote =  remoteDataSource.getProductsFromSearch("MCO", keyword, offset)

        return if (productsRemote is Output.Success) {
            Output.success(CharactersDataMapper.ProductsResponseToProductUI.map(productsRemote.data))
        } else {
            Output.success(emptyList())
        }
    }

    override suspend fun getProductDetail(id: String): Output<ProductDetailUI> {
        Timber.d("getProductDetail")
        val productDetail =  remoteDataSource.getProductDetail(id)

        return if (productDetail is Output.Success) {
            Output.success(CharactersDataMapper.ProductDetailResponseToProductDetailUI.map(productDetail.data))
        } else {
            Output.success(ProductDetailUI())
        }
    }


    override suspend fun getCharacters(): Output<List<CharacterUI>> {
        Timber.d("getCharacters")

        val charactersCache = cacheDataSource.getCharactersWithFavorites()
        if (charactersCache is Output.Success) {
            Timber.d("get Characters from local database")
            if (charactersCache.data.isNotEmpty()){
                return charactersCache
            }
        }

        val charactersRemote = remoteDataSource.getCharacters()
        if (charactersRemote is Output.Success) {
            Timber.d("Save Characters to local database")
            cacheDataSource.insertCharacters(CharactersDataMapper.CharactersResponseToCache.map(charactersRemote.data))
        }

        return cacheDataSource.getCharactersWithFavorites()
    }

    override suspend fun updateCharacter(characterUI: CharacterDetailsUI) {
        return cacheDataSource.updateCharacter(characterUI)
    }

    override suspend fun getCharacterDetails(id: Int): Output<CharacterDetailsUI> {
        return cacheDataSource.getCharacterId(id)
    }
}
