package net.atos.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.pokemon_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.btn_home -> {
                startActivity(Intent( this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
