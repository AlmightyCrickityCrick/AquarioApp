package com.example.aquario.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.aquario.R
import com.example.aquario.activities.fragments.FeedingFragment
import com.example.aquario.activities.fragments.SensorsFragment
import com.example.aquario.activities.fragments.SettingsFragment
import com.example.aquario.data.model.LoggedInUser
import com.example.aquario.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navDrawer: NavigationView
    lateinit var mDrawer:DrawerLayout
//    private val USER_CONST = "USER"
//    lateinit var user : LoggedInUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        user = intent.getSerializableExtra("USER") as LoggedInUser
        mDrawer =  binding.drawerLayout
        navDrawer = binding.nvDrawer
        setUpDrawerContent(navDrawer)
        navDrawer.checkedItem?.let { selectDrawerItem(it) }
    }

    private fun setUpDrawerContent(navigationView: NavigationView ){
        navigationView.setNavigationItemSelectedListener {
            selectDrawerItem(it)
            true
        }
    }

    fun selectDrawerItem(menuItem: MenuItem){
        var fragment: Fragment? = null
        when(menuItem.itemId){
            R.id.nav_sensors -> {fragment = SensorsFragment() }
//            R.id.nav_analytics -> {fragment = AnalyticsFragment() }
            R.id.nav_feeding ->{fragment = FeedingFragment() }
//            R.id.nav_video->{fragment = VideoFragment()}
            R.id.nav_settings->{fragment = SettingsFragment()
            }
        }
        val fragmentM = supportFragmentManager
        if (fragment != null) {
            fragmentM.beginTransaction().replace(R.id.activity_content, fragment).commit()
        }
        menuItem.isChecked = true
        mDrawer.closeDrawers()
    }
}