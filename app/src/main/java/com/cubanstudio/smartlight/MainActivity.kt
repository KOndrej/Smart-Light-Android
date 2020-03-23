package com.cubanstudio.smartlight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_screen_slide.*

private const val NUM_PAGES = 3

class MainActivity : FragmentActivity() {
    public var x = ""
    private lateinit var mPager: ViewPager
    val navigationlistener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.navigation_home -> {
                mPager.currentItem = 1
                true
            }
            R.id.navigation_routines -> {
                mPager.currentItem = 0
                true
            }
            R.id.navigation_settings -> {
                mPager.currentItem = 2
                true
            }

            else -> false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_slide)
        // or add <item name="android:windowTranslucentStatus">true</item> in the theme
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val attrib = window.attributes

        attrib.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager)


        // The pager adapter, which provides the pages to the view pager widget.

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter
        mPager.setPageTransformer(true,ZoomOutTransformer())
        mPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> navbar.menu.findItem(R.id.navigation_routines).isChecked = true
                    1 -> navbar.menu.findItem(R.id.navigation_home).isChecked = true
                    2 -> navbar.menu.findItem(R.id.navigation_settings).isChecked = true

                }
            }
        })
        navbar.setOnNavigationItemSelectedListener(navigationlistener)
        navbar.menu.findItem(R.id.navigation_home).isChecked = true
        mPager.currentItem = 1

    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment{

            when(position){
                1 -> {

                    return HomeFragment() }
                0 -> {return PresetsFragment()}


                2 -> {return EffectsFragment()}

            }

            return HomeFragment()
        }
    }



}

