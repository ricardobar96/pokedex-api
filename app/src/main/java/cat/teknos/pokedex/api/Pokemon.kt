package cat.teknos.pokedex.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pokemon {
    @SerializedName("name")
    @Expose
    var name: String? = null
}