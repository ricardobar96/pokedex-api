package cat.teknos.pokedex.service;

import cat.teknos.pokedex.api.PokemonFetchResults;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonAPIService {

    @GET("pokemon?limit=100&offset=200")
    Call<PokemonFetchResults> getPokemons();
}