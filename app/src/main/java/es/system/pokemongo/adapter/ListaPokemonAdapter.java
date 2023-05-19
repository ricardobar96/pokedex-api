package es.system.pokemongo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Objects;

import es.system.pokemongo.R;
import es.system.pokemongo.api.Pokemon;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private final Context context;
    private  ArrayList<Pokemon> dataset;

    public ListaPokemonAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();

    }

    public void agregarPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

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
        String numeroStr = linkedTreeMap.get("url").toString();
        String[] NumeroPokemon = numeroStr.split("/");
        String numeroFinalStr = NumeroPokemon[6];
        System.out.println(numeroFinalStr);
        int numeroFinal = Integer.valueOf(numeroFinalStr);

        Glide.with(context)
                .load( "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                        + numeroFinalStr + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView){
            super(itemView);

            fotoImageView = (ImageView)  itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
        }
    }
}
