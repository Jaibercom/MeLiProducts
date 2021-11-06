package com.jaiberyepes.mercadolibre.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jaiberyepes.mercadolibre.domain.usescases.CharactersUseCases
import com.jaiberyepes.mercadolibre.presentation.model.CharacterDetailsUI
import com.jaiberyepes.mercadolibre.presentation.model.CharacterUI
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
 * Test class for [CharactersViewModel].
 *
 * @author jaiber.yepes
 */
@RunWith(JUnit4::class)
class CharactersViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var charactersUseCases: CharactersUseCases

    private lateinit var charactersViewModel: CharactersViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        charactersViewModel = CharactersViewModel(charactersUseCases)
        charactersViewModel.currentUIStateLiveData.observeForever {}
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Success with UiState when getCharacters`() {
        // Given
        val listOfCharacters = listOf(

            CharacterUI(1, CHARACTER_1_NAME, CHARACTER_1_NICKNAME, ""),
            CharacterUI(2, CHARACTER_2_NAME, CHARACTER_2_NICKNAME, ""),
            CharacterUI(3, CHARACTER_3_NAME, CHARACTER_3_NICKNAME, "")
        )

        coEvery { charactersUseCases.getCharacters() } returns Output.success(listOfCharacters)

        // When
        charactersViewModel.getCharacters()

        // Then
        assert(charactersViewModel.currentUIStateLiveData.value != null)

        when (val uiState = charactersViewModel.currentUIStateLiveData.value) {
            is UIState.Data -> {
                assert(uiState.data == UIState.Data(CharactersViewModel.CharactersDataType.CharactersData(listOfCharacters)).data)
            }
        }
    }

    @Test
    fun `Success with UiState when getCharacterDetails`() {
        // Given
        val character = CharacterDetailsUI(1, CHARACTER_1_NAME, CHARACTER_1_NICKNAME, "", false,
            listOf(CHARACTER_1_OCCUPATION1, CHARACTER_1_OCCUPATION2), CHARACTER_1_STATUS, "")

        coEvery { charactersUseCases.getCharacterDetails(1) } returns Output.success(character)

        // When
        charactersViewModel.getCharacterDetails(1)

        // Then
        assert(charactersViewModel.currentUIStateLiveData.value != null)

        when (val uiState = charactersViewModel.currentUIStateLiveData.value) {
            is UIState.Data -> {
                assert(uiState.data == UIState.Data(CharactersViewModel.CharactersDataType.CharacterDetailsData(character)).data)
            }
        }
    }

    companion object {

        // Characters constants
        private const val CHARACTER_1_NAME = "Walter White"
        private const val CHARACTER_1_NICKNAME = "Heisenberg"
        private const val CHARACTER_1_OCCUPATION1 = "High School Chemistry Teacher"
        private const val CHARACTER_1_OCCUPATION2 = "Meth King Pin"
        private const val CHARACTER_1_STATUS = "Presumed dead"

        private const val CHARACTER_2_NAME = "Jesse Pinkman"
        private const val CHARACTER_2_NICKNAME = "Cap n' Cook"
        private const val CHARACTER_3_NAME = "Henry Schrader"
        private const val CHARACTER_3_NICKNAME = "Hank"

    }
}
