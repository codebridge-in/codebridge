package com.codebridge.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.codebridge.R
import com.codebridge.databinding.ActivityMainBinding
import com.codebridge.fragments.AssignmentFragment
import com.codebridge.fragments.HomeFragment
import com.codebridge.fragments.LecturesFragment
import com.codebridge.fragments.NotesFragment
import com.codebridge.fragments.TestFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        replaceFragment(HomeFragment())
        setupToolbar()
        updateStatusBarColor()
        setupBottomNavigation()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateStatusBarColor() {
        val color = if (isDarkMode()) {
            ContextCompat.getColor(this, R.color.primaryVariant) // Dark mode color
        } else {
            ContextCompat.getColor(this, R.color.primary) // Light mode color
        }
        window.statusBarColor = color
    }

    private fun isDarkMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { toggleDrawer() }
        binding.drawerBtn.setOnClickListener { toggleDrawer() }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_notes -> replaceFragment(NotesFragment())
                R.id.bottom_lecture -> replaceFragment(LecturesFragment())
                R.id.bottom_assignment -> replaceFragment(AssignmentFragment())  // Corrected
                R.id.bottom_test -> replaceFragment(TestFragment())  // Corrected
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}