package es.system.pokemongo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import es.system.pokemongo.adapter.ListaPokemonAdapter;
import es.system.pokemongo.api.Pokemon;
import es.system.pokemongo.api.PokemonFetchResults;
import es.system.pokemongo.service.PokemonAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPokemon);
        listaPokemonAdapter = new ListaPokemonAdapter(this.getBaseContext());
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();
    }

    private void obtenerDatos(){
        PokemonAPIService service = retrofit.create(PokemonAPIService.class);
        Call<PokemonFetchResults> pokemonFetchResultsCall = service.getPokemons();

        pokemonFetchResultsCall.enqueue(new Callback<PokemonFetchResults>() {
            @Override
            public void onResponse(Call<PokemonFetchResults> call, Response<PokemonFetchResults> response) {
                if(response.isSuccessful()){
                    PokemonFetchResults pokemonFetchResults = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonFetchResults.getResults();

                    for (int i = 0; i <listaPokemon.size(); i++) {
                        Object p = listaPokemon.get(i);
                        Log.i(TAG, " Pokemon: " + p.toString());
                    }


                    listaPokemonAdapter.agregarPokemon(listaPokemon);
                }
                else{
                    Log.e(TAG, " Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonFetchResults> call, Throwable t) {
                Log.e(TAG, " Error: " + t.getMessage());
            }
        });
    }
}