package net.atos.pokedex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import net.atos.pokedex.api.PokemonFetchResults
import net.atos.pokedex.databinding.FragmentItemListBinding
import net.atos.pokedex.service.PokemonAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemListFragment : Fragment() {

    private var binding: FragmentItemListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()

        val pokemonApiService = retrofit.create(PokemonAPIService::class.java)

        val call = pokemonApiService.pokemons
        call?.enqueue(object : Callback<PokemonFetchResults?> {
            override fun onResponse(
                call: Call<PokemonFetchResults?>,
                response: Response<PokemonFetchResults?>
            ) {
                if (response.isSuccessful) {
                    assert(response.body() != null)
                    response.body()!!.results
                } else {
                    Log.d("Error:", "Couldn't get list")
                }
            }
            override fun onFailure(call: Call<PokemonFetchResults?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}