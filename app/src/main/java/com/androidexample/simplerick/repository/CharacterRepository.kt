package com.androidexample.simplerick.repository

import com.androidexample.network.ApiOperation
import com.androidexample.network.KtorClient
import com.androidexample.network.models.domain.Character
import com.androidexample.network.models.domain.CharacterPage
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {

    suspend fun fetchCharacterPage(page: Int): ApiOperation<CharacterPage>{
        return ktorClient.getCharacterByPage(page)
    }

    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character>{
        return ktorClient.getCharacter(characterId)
    }
}