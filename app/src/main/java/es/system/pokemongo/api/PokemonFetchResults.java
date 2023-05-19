package es.system.pokemongo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonFetchResults {
    @SerializedName("results")
    @Expose
    private ArrayList results;

    public ArrayList<Pokemon> getResults() {

        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
