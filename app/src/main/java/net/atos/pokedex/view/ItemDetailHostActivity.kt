package net.atos.pokedex.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import net.atos.pokedex.R
import net.atos.pokedex.databinding.ActivityItemDetailBinding

class ItemDetailHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityItemDetailBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment?)!!
        val navController = navHostFragment.navController
        val appBarConfiguration: AppBarConfiguration = Builder(navController.graph).build()
        setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_item_detail)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}