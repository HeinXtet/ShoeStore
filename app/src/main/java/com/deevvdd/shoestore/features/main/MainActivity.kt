package com.deevvdd.shoestore.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.ActivityMainBinding
import com.deevvdd.shoestore.utils.hide
import com.deevvdd.shoestore.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.splashFragment,
                R.id.shoeListingFragment,
                R.id.loginFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.toolbar.hide()
                }
                else -> binding.toolbar.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}