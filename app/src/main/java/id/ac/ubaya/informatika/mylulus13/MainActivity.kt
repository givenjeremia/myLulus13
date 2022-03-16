package id.ac.ubaya.informatika.mylulus13

import android.R.id.message
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.drawer_layout.*


class MainActivity : AppCompatActivity() {
    val fragments : ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        fragments.add(MataKuliahFragment())
        fragments.add(CekKelulusanFragment())
        fragments.add(ProfilFragment())
        viewPager.adapter = MyAdapter(this,fragments)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })
        bottomNav.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
                R.id.itemMataKuliah -> 0
                R.id.itemCekKelulusan -> 1
                R.id.itemProfil -> 2
                else -> 0
            }
            true
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Kuliah"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name, R.string.app_name).apply {
            isDrawerIndicatorEnabled = true
            syncState()
        }
        val nrp = Global.getUserNRP(this)
        navView.setNavigationItemSelectedListener {
            textHello.text = "Hello, $nrp"
            viewPager.currentItem= when(it.itemId){
                R.id.itemMatkul -> 0
                R.id.itemCekLulus-> 1
                R.id.itemProfile -> 2
                else -> 0
            }
            when(it.itemId) {
                R.id.itemHubungiKami -> {
                    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "mylulus@unit.ubaya.ac.id", null))
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bantuan")
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Disini Saya Mendapatkan Bug Yang Luar Biasa")
                    startActivity(Intent.createChooser(emailIntent, "Send email..."))

                }
                R.id.itemKeluar ->  {
                    getSharedPreferences(Global.SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit {
                        remove(Global.SHARED_PREF_KEY_NRP_USER)

                    }
                    this.finish()
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)

                }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

}