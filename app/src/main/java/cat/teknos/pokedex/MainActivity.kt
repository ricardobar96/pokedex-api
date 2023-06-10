package cat.teknos.pokedex

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.teknos.pokedex.adapter.PokemonListAdapter
import cat.teknos.pokedex.api.PokemonFetchResults
import cat.teknos.pokedex.service.PokemonAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var retrofit: Retrofit? = null
    private var pokemonAdapterList: PokemonListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<View>(R.id.recyclerViewPokemon) as RecyclerView
        pokemonAdapterList = PokemonListAdapter(this.baseContext)
        recyclerView.adapter = pokemonAdapterList
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = gridLayoutManager
        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getData()
    }

    private fun getData() {
        val service = retrofit!!.create(PokemonAPIService::class.java)
        val pokemonFetchResultsCall = service.pokemons
        pokemonFetchResultsCall.enqueue(object : Callback<PokemonFetchResults?> {
            override fun onResponse(
                call: Call<PokemonFetchResults?>,
                response: Response<PokemonFetchResults?>
            ) {
                if (response.isSuccessful) {
                    val pokemonFetchResults = response.body()!!
                    val pokemonList = pokemonFetchResults.results
                    for (i in pokemonList.indices) {
                        val p: Any = pokemonList[i]
                        Log.i(TAG, " Pokemon: $p")
                    }
                    pokemonAdapterList!!.addPokemon(pokemonList)
                } else {
                    Log.e(TAG, " Error: " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<PokemonFetchResults?>, t: Throwable) {
                Log.e(TAG, " Error: " + t.message)
            }
        })
    }

    companion object {
        private const val TAG = "POKEDEX"
    }
}