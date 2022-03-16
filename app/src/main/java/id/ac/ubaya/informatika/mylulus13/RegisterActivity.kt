package id.ac.ubaya.informatika.mylulus13

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonCancel.setOnClickListener {
            finish()
        }
        var cekNrpPIN = false;
        var cekPIN = false;
        btnRegis.setOnClickListener {
            val nrp = EditTextNRP.text.toString()
            val pin = EditTextPIN.text.toString()
            val ulangiPin = EditTextUlangiPIN.text.toString()
            val nama = EditTextNama.text.toString()
            val tahunMasuk = EditTextTahunMasuk.text.toString()
            //Cek Apakah Ada Yang Kosong , Jika Ada Yang Kosong Maka Error
            if(nrp.isNotEmpty() && pin.isNotEmpty() && ulangiPin.isNotEmpty() && nama.isNotEmpty() && tahunMasuk.isNotEmpty() ){
                //Cek Pin Dan NRP Kalau Lebih
                if (pin.trim().length > 8 || nrp.trim().length != 9){
                    cekNrpPIN = false
                    AlertDialog.Builder(this).apply {
                        setMessage("PIN Harus Kurang Dari 8 Karakter \nNRP Harus 9 Karakter")
                        setPositiveButton("Oke" ,null)
                        create().show()
                    }
                }
                else cekNrpPIN = true
                //Cek Kesamaan PIN Dan Ulangi PIN
                if (pin == ulangiPin){
                    cekPIN = true
                }
                else{
                    AlertDialog.Builder(this).apply {
                        setMessage("Pin Dan Ulangi Pin Tidak Sama!")
                        setPositiveButton("Oke" ,null)
                        create().show()
                    }
                }
                //Insert Database Jika Semua Sudah Di Cek
                if(cekNrpPIN && cekPIN){
                    val queue = Volley.newRequestQueue(this)
                    val url = "http://10.0.2.2/myLulus13/register.php"
                    val stringRequest = object : StringRequest(
                        Request.Method.POST,
                        url,
                        Response.Listener {
                            Log.e("RegisterMahasiswa" , it)
                            if(it.contains("Berhasil")){
                                //Regis Berhasil
                                AlertDialog.Builder(this).apply {
                                    setMessage("Register Berhasil")
                                    setPositiveButton("Ok") { _, _->
                                        this@RegisterActivity.finish()
                                    }
                                    create().show()
                                }
                            }
                            else{
                                //Regis Gagal
                                AlertDialog.Builder(this).apply {
                                    setTitle("Register Gagal")
                                    setMessage("NRP Sudah Ada!!")
                                    setPositiveButton("Ok",null)
                                    create().show()
                                }
                            }
                        },
                        Response.ErrorListener {
                            Log.e("RegisterMahasiswa" , it.toString())
                        }
                    ){
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String,String>()
                            params["nrp"] = nrp
                            params["pin"] = pin
                            params["nama"] = nama
                            params["tahun_masuk"] = tahunMasuk
                            return params
                        }
                    }
                    queue.add(stringRequest)
                }

            }
            else{
                AlertDialog.Builder(this).apply {
                    setMessage("Harap Di Isi Semua")
                    setPositiveButton("Ok" ,null)
                    create().show()
                }
            }
        }
    }
}