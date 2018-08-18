package v.com.soloplaylist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_camera_dialog.*
import v.com.soloplaylist.R

class CameraDialogActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_camera_dialog)

        val popupView = layoutInflater.inflate(R.layout.activity_camera_dialog, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true
        //팝업 외의 화면 선택시 나가짐

        popupCamera.setOnClickListener { camera() }
        popupGallery.setOnClickListener { gallery() }
    }

    fun camera(){
        var intent = Intent(this,SignInActivity::class.java)
        setResult(1,intent)
        finish()
    }

    fun gallery(){
        var intent = Intent(this,SignInActivity::class.java)
        setResult(2,intent)
        finish()
    }
}
