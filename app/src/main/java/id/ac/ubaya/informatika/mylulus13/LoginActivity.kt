package id.ac.ubaya.informatika.mylulus13

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Global.getUserNRP(this) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)
        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnLogin.setOnClickListener {
            val nrp = EditTextNRP.text.toString()
            val pin = EditTextPIN.text.toString()
            //Cek Apakah Ada Yang Kosong , Jika Ada Yang Kosong Maka Error
            if (nrp.isNotEmpty() && pin.isNotEmpty()) {
                val queue = Volley.newRequestQueue(this)
                val url = "http://10.0.2.2/myLulus13/login.php"
                val stringRequest = object : StringRequest(
                        Request.Method.POST,
                        url,
                        Response.Listener {
                            Log.e("LoginActivity", it)
                            if (it.contains("Berhasil")) {
                                //Login Berhasil
                                AlertDialog.Builder(this).apply {
                                    getSharedPreferences(Global.SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit {
                                        putString(Global.SHARED_PREF_KEY_NRP_USER, nrp)
                                    }
                                    setMessage("Login Berhasil $nrp")
                                    setPositiveButton("Ok") { _, _ ->
                                        finish()
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    create().show()
                                }
                            } else {
                                //Login Gagal
                                AlertDialog.Builder(this).apply {
                                    //Center
                                    val message = SpannableString("Login Gagal \nUsername Atau Password Salah")
                                    // alert dialog title align center
                                    message.setSpan(
                                            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                            0,
                                            message.length,
                                            0
                                    )
                                    setMessage(message)
                                    setPositiveButton("Ok", null)
                                    create().show()
                                }
                            }

                        },
                        Response.ErrorListener {
                            Log.e("LoginActivity", it.toString())
                        }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["nrp"] = nrp
                        params["pin"] = pin
                        return params
                    }
                }
                queue.add(stringRequest)
            } else {
                AlertDialog.Builder(this).apply {
                    setMessage("NRP Atau Pin Tidak Boleh Kosong")
                    setPositiveButton("Ok", null)
                    create().show()
                }
            }
        }
    }
}