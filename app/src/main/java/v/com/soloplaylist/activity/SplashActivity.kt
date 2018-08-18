package v.com.soloplaylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import v.com.soloplaylist.R

class SplashActivity : AppCompatActivity() {

    lateinit var anim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        var hd1 = Handler()
        var hd2 = Handler()
        startAnimations()
        hd1.postDelayed({
            endAnimation()
            hd2.postDelayed({

                splashLayout.visibility = View.INVISIBLE
                startActivity<MainActivity>()
                finish()

//                startActivity<LoginActivity>()
//                finish()

            }, 2500)
        }, 2000)
    }

    private fun startAnimations() {
        anim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fadein)
        anim.reset()
        splashLayout.clearAnimation()
        splashLayout.startAnimation(anim)
    }

    private fun endAnimation() {
        anim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fadeout)
        anim.reset()
        splashLayout.clearAnimation()
        splashLayout.startAnimation(anim)
    }

    override fun onBackPressed() {}
}
