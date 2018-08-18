package v.com.soloplaylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import v.com.soloplaylist.R
import v.com.soloplaylist.fragment.MainFragment
import v.com.soloplaylist.fragment.MyFragment
import v.com.soloplaylist.fragment.NoticationFragment
import v.com.soloplaylist.fragment.SettingFragment

class MainActivity : AppCompatActivity() {
    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0
    lateinit var fragment : Fragment
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragment = MainFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainLayout, fragment).commit()

    }

    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_main -> {
                    fragment = MainFragment()
                }
                R.id.action_my -> {
                    fragment = MyFragment()
                }
                R.id.action_notication -> {
                    fragment = NoticationFragment()
                }
                R.id.action_setting ->{
                    fragment = SettingFragment()
                }
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.mainLayout, fragment).commit()
            return true
        }

    }
}
