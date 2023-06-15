package net.atos.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        val subjectTextView: TextView = findViewById(R.id.pokemon_subject) as TextView
        val subjectKey:String = intent.getStringExtra("subject").toString()
        subjectTextView.text = subjectKey
    }
}