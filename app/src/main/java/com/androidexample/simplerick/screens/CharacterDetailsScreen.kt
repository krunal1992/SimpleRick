package com.androidexample.simplerick.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androidexample.network.models.domain.Character
import com.androidexample.simplerick.components.character.CharacterDetailsNamePlateComponent
import com.androidexample.simplerick.components.common.CharacterImage
import com.androidexample.simplerick.components.common.DataPoint
import com.androidexample.simplerick.components.common.DataPointComponent
import com.androidexample.simplerick.components.common.LoadingState
import com.androidexample.simplerick.ui.theme.RickAction
import com.androidexample.simplerick.viewmodels.CharacterDetailsViewModel

sealed interface CharacterDetailsViewState {
    object Loading : CharacterDetailsViewState
    data class Error(val message: String) : CharacterDetailsViewState
    data class Success(
        val character: Character,
        val characterDataPoint: List<DataPoint>
    ) : CharacterDetailsViewState
}

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClick: (Int) -> Unit
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.fetchCharacter(characterId)
    }

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        when (val viewState = state) {
            is CharacterDetailsViewState.Error -> item { LoadingState() }
            CharacterDetailsViewState.Loading -> item { LoadingState() }
            is CharacterDetailsViewState.Success -> {
//                item {
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        CharacterGridItem(
//                            modifier = Modifier.weight(1f),
//                            character = viewState.character
//                        ) {
//                        }
//                        Spacer(modifier = Modifier.width(16.dp))
//                        CharacterGridItem(
//                            modifier = Modifier.weight(1f),
//                            character = viewState.character
//                        ) {
//                        }
//                    }
//                }
//
//                repeat(10) {
//                    item { Spacer(modifier = Modifier.height(16.dp)) }
//                    item {
//                        CharacterListItem(
//                            character = viewState.character,
//                            characterDataPoints = viewState.characterDataPoint
//                        ) {
//
//                        }
//                    }
//                }
                item {
                    CharacterDetailsNamePlateComponent(
                        name = viewState.character.name,
                        status = viewState.character.status
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    CharacterImage(imageUrl = viewState.character.imageUrl)
                }

                //Data Point
                items(viewState.characterDataPoint) {
                    Spacer(modifier = Modifier.height(32.dp))
                    DataPointComponent(dataPoint = it)
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Button
                item {
                    Text(
                        text = "View all episodes",
                        color = RickAction,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .border(
                                width = 1.dp,
                                color = RickAction,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onEpisodeClick(characterId)
                            }
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )
                }

                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}