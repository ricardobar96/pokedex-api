package cat.teknos.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import cat.teknos.pokedex.R;
import cat.teknos.pokedex.api.Pokemon;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {

    private static Context context;
    private final ArrayList<Pokemon> dataset;

    public PokemonListAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();

    }

    public void agregarPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object p = dataset.get(position);
        LinkedTreeMap<Pokemon, Object> linkedTreeMap = (LinkedTreeMap)p;

        holder.nombreTextView.setText(Objects.requireNonNull(linkedTreeMap.get("name")).toString());
        String numeroStr = Objects.requireNonNull(linkedTreeMap.get("url")).toString();
        String[] NumeroPokemon = numeroStr.split("/");
        String numeroFinalStr = NumeroPokemon[6];
        System.out.println(numeroFinalStr);

        Glide.with(context)
                .load( "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                        + numeroFinalStr + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView fotoImageView;
        private final TextView nombreTextView;

        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = itemView.findViewById(R.id.fotoImageView);
            nombreTextView = itemView.findViewById(R.id.tvName);

            fotoImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Here goes new intent pokemon view
                    Toast toast = new Toast(context);
                    toast.makeText(context, nombreTextView.getText().toString().toUpperCase(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}