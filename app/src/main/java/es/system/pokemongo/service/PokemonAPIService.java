package es.system.pokemongo.service;

import es.system.pokemongo.api.PokemonFetchResults;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonAPIService {

    /**
     * Metodo que obtiene una lista de elementos
     * @return conjunto de elementos pockemon
     */
    @GET("pokemon?limit=100&offset=200")
    Call<PokemonFetchResults> getPokemons();

}
