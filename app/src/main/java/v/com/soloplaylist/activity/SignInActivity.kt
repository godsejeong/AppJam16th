package v.com.soloplaylist.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import v.com.soloplaylist.R
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.Gravity
import android.widget.DatePicker
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import v.com.soloplaylist.data.EmptyData
import v.com.soloplaylist.utils.CameraUtils
import v.com.soloplaylist.utils.RetrofitUtils
import java.io.File
import java.net.URI
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    var file: File? = null
    var ddate = ""
    var name = ""
    var id = ""
    lateinit var camerauri: Uri
    var passwd = ""
    lateinit var img: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signinProfile.setBackgroundResource(R.drawable.profileimg)

        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
        } else {
            EasyPermissions.requestPermissions(this, "사진을 찍으려면 권한이 필요합니다", 200, android.Manifest.permission.CAMERA)
        }

        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            EasyPermissions.requestPermissions(this, "파일을 읽으려면 권한이 필요합니다", 300, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        signinProfile.onClick {
            var intent = Intent(this@SignInActivity, CameraDialogActivity::class.java)
            startActivityForResult(intent, 50)
        }

        signinBtn.onClick {
            name = signinName.text.toString()
            id = signinId.text.toString()
            passwd = signinPasswd.text.toString()

            if (name.isEmpty()) {
                signinName.error = "이름을 입력해주세요"
                signinName.requestFocus()
            }

            if (id.isEmpty()) {
                signinId.error = "아이디를 입력해주세요"
                signinId.requestFocus()
            }

            if (passwd.isEmpty()) {
                signinPasswd.error = "비밀번호를 입력해주세요"
                signinPasswd.requestFocus()
            }
            if(ddate == ""){
                toast("헤어진 날짜를 등록해 주세요")
            }

            if (file == null) {
                toast("프로필 사진을 선택해 주세요")
            }

            if (name.isNotEmpty() && id.isNotEmpty() && passwd.isNotEmpty() && ddate != "" && file != null) {
                signin()
            }
        }

        signinDateSelectLayout.onClick {
            var date = Date()
            var year = SimpleDateFormat("yyyy").format(date)
            var month = SimpleDateFormat("MM").format(date)
            var d = SimpleDateFormat("dd").format(date)

            Log.e("year", (Integer.parseInt(year)).toString())
            Log.e("d", (Integer.parseInt(month)).toString())
            Log.e("month", (Integer.parseInt(d)).toString())
            val dialog = DatePickerDialog(this@SignInActivity, listener(),
                    Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(d))
            dialog.show()
        }
    }

    fun listener(): DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
            signinDateMonth.gravity = Gravity.CENTER
            signinDateDay.gravity = Gravity.CENTER
            signinDateMonth.text = (p2 + 1).toString() + "월"
            signinDateDay.text = p3.toString() + "일"
            ddate = p1.toString() + "-" + (p2 + 1).toString() + "-" + p3.toString()
            Log.e("ddate", ddate)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 50) {
            if (resultCode == 1) {
                camera()
            }
            if (resultCode == 2) {
                gallery()
            }
        }

        if (requestCode == 200 && resultCode === Activity.RESULT_OK) {
            var uri = data!!.data
            Glide.with(this).load(uri).into(signinProfile)
            file = File(CameraUtils().getRealPathFromURIPath(uri!!, this))
            Log.e("uripath", uri.toString())
            //ImageCrop(false)
        }

        if (requestCode == 100 && resultCode === Activity.RESULT_OK) {
            //RESULT_OK -> 카메라를 실제로 찍었는지, 취소로 나갔는지
            Log.e("camera", "camera")
            Glide.with(this).load(camerauri).into(signinProfile)
        }
    }

    fun signin() {
        var res = RetrofitUtils.postService.User(
                signinId.text.toString(),
                signinName.text.toString(),
                signinPasswd.text.toString(),
                ddate,
                RetrofitUtils.createMultipartBody(file!!, "thumbnail")
        )
        res.enqueue(object : Callback<EmptyData>{
            override fun onResponse(call: Call<EmptyData>?, response: Response<EmptyData>?) {
                when(response!!.code()){
                    200->{
                        toast("회원가입이 정상적으로 완료되었습니다.")
                        startActivity<LoginActivity>()
                        finish()
                        finish()
                    }
                    else->{
                        toast("아이디나 비밀번호가 일치합니다.")
//                        toast(response.body()!!.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<EmptyData>?, t: Throwable?) {
                Log.e("signinError",t!!.message)
                toast("ServerError")
            }

        })
    }

    fun camera() {
        var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = CameraUtils().getOutputMediaFileUri()
        camerauri = FileProvider.getUriForFile(this, "v.com.soloplaylist.provider", file!!)
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, camerauri)
        startActivityForResult(cameraIntent, 100)
    }

    fun gallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, 200)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        toast("권한이 없습니다.")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        if (requestCode == 300) {
            gallery()
        }

        if (requestCode == 200) {
            camera()
        }
    }
}
