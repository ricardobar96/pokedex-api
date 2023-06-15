package net.atos.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.atos.pokedex.adapter.PokemonListAdapter

class PokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val btn_return = findViewById(R.id.btn_back) as Button
        val subjectTextView: TextView = findViewById(R.id.pokemon_subject) as TextView
        val subjectKey:String = intent.getStringExtra("subject").toString()
        subjectTextView.text = subjectKey

        btn_return.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}