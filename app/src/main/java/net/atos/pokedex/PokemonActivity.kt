package net.atos.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.atos.pokedex.adapter.PokemonListAdapter

class PokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val btn_return = findViewById(R.id.btn_back) as Button
        val subjectTextView: TextView = findViewById(R.id.pokemon_subject) as TextView
        val subjectKey:String = intent.getStringExtra("subject").toString()
        val numberKey: String = intent.getStringExtra("number").toString()
        subjectTextView.text = subjectKey
        val getImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
        numberKey + ".png"

        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(image);

        btn_return.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}
