package v.com.soloplaylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import v.com.soloplaylist.R
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.utils.ORMUtil

class SplashActivity : AppCompatActivity() {

    lateinit var anim: Animation
    var token: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        try {
            var list: List<Any> = ORMUtil(this).tokenORM.find(EmptyData())
            var sign = list[list.size - 1] as EmptyData
            token = sign.token
            Log.e("splashtoken",token)
        } catch (e: ArrayIndexOutOfBoundsException) {
            token = null
        }

        var hd1 = Handler()
        var hd2 = Handler()
        startAnimations()
        hd1.postDelayed({
            endAnimation()
            hd2.postDelayed({

                splashLayout.visibility = View.INVISIBLE
                if (token != null) {
                    startActivity<MainActivity>()
                    finish()
                    toast("자동로그인 되었습니다.")
                } else {
                    startActivity<LoginActivity>()
                    finish()
                }
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
