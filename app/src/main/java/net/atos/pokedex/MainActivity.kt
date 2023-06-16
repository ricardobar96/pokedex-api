package net.atos.pokedex

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.atos.pokedex.adapter.PokemonListAdapter
import net.atos.pokedex.api.Pokemon
import net.atos.pokedex.api.PokemonFetchResults
import net.atos.pokedex.service.PokemonAPIService
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
        pokemonFetchResultsCall?.enqueue(object : Callback<PokemonFetchResults?> {
            override fun onResponse(
                call: Call<PokemonFetchResults?>,
                response: Response<PokemonFetchResults?>
            ) {
                if (response.isSuccessful) {
                    val pokemonFetchResults = response.body()!!
                    val pokemonList = pokemonFetchResults.results
                    for (i in pokemonList?.indices!!) {
                        val p: Any = pokemonList[i]
                        Log.i(TAG, " Pokemon: $p")
                    }
                    pokemonAdapterList!!.addPokemon(pokemonList as ArrayList<Pokemon>?)
                } else {
                    Log.e(TAG, " Error: " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<PokemonFetchResults?>, t: Throwable) {
                Log.e(TAG, " Error: " + t.message)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.top_menu, menu)
        return true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    companion object {
        private const val TAG = "POKEDEX"
    }
}