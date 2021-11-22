package com.jaiberyepes.mercadolibre.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaiberyepes.mercadolibre.domain.usescases.UseCases
import com.jaiberyepes.mercadolibre.presentation.model.ProductUI
import com.jaiberyepes.mercadolibre.presentation.ui.search.SearchViewModel
import com.jaiberyepes.mercadolibre.util.Output
import com.jaiberyepes.mercadolibre.util.base.UIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Test class for [SearchViewModel].
 *
 * @author jaiber.yepes
 */
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var charactersUseCases: UseCases

    private lateinit var viewModel: SearchViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        viewModel = SearchViewModel(charactersUseCases)
        viewModel.currentUIStateLiveData.observeForever {}
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Success with UiState when getProducts`() {
        // Given
        val listOfProducts = listOf(
            ProductUI("1", PRODUCT_1_NAME, PRODUCT_1_PRICE, "", ""),
            ProductUI("2", PRODUCT_2_NAME, PRODUCT_2_PRICE, "", ""),
        )

        coEvery { charactersUseCases.getProductsFromSearch(keywords = "keyword", offset = 0) } returns Output.success(listOfProducts)

        // When
        viewModel.performSearch("keyword")

        // Then
        assert(viewModel.currentUIStateLiveData.value != null)

        when (val uiState = viewModel.currentUIStateLiveData.value) {
            is UIState.Data -> {
                assert(uiState.data == UIState.Data(SearchViewModel.ProductsDataType.Products(listOfProducts)).data)
            }
        }
    }

    companion object {
        private const val PRODUCT_1_NAME = "Product 1"
        private const val PRODUCT_1_PRICE = 100.0

        private const val PRODUCT_2_NAME = "Product 1"
        private const val PRODUCT_2_PRICE = 200.0
    }
}
