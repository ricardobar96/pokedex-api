package net.atos.pokedex.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonFetchResults {
    @SerializedName("results")
    @Expose
    val results: ArrayList<*>? = null
}