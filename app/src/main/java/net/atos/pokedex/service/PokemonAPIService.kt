package net.atos.pokedex.service

import net.atos.pokedex.api.PokemonFetchResults
import retrofit2.Call
import retrofit2.http.GET

interface PokemonAPIService {
    @get:GET("pokemon?limit=151&offset=0")
    val pokemons: Call<PokemonFetchResults?>?
}