package id.ac.ubaya.informatika.mylulus13

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ambil_matkul.*
import kotlinx.android.synthetic.main.activity_ambil_matkul.groupSemester
import kotlinx.android.synthetic.main.activity_ambil_matkul.inputTahunAjaran
import kotlinx.android.synthetic.main.activity_ambil_matkul.spinnerNisbi
import kotlinx.android.synthetic.main.activity_ambil_matkul.textViewKode
import kotlinx.android.synthetic.main.activity_ambil_matkul.textViewMatkul
import kotlinx.android.synthetic.main.activity_ambil_matkul.textViewSks
import kotlinx.android.synthetic.main.activity_update_matkul.*
import kotlinx.android.synthetic.main.activity_update_matkul.view.*

class UpdateMatkulActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_matkul)

        val nrp = intent.getStringExtra(Global.EXTRA_NRP).toString()
        textViewKode.setText(intent.getStringExtra(Global.EXTRA_KodeMatkul))
        textViewMatkul.setText(intent.getStringExtra(Global.EXTRA_NamaMatkul))
        textViewSks.setText(intent.getIntExtra(Global.EXTRA_SKS,0).toString() + " SKS")
        inputTahunAjaran.setText(intent.getStringExtra(Global.EXTRA_TAHUN_AJARAN))
        val semesterData =  intent.getStringExtra(Global.EXTRA_SEMESTER)
        //Set Radio Button
        if (semesterData=="gasal"){
            groupSemester.radioButtonGasal.isChecked = true
        }
        else{
            groupSemester.radioButtonGenap.isChecked = true
        }

        val listNisbi = arrayListOf<String>("A", "AB","B", "BC", "C","D", "E" )
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listNisbi)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerNisbi.adapter = adapter
        var nisbi = ""
        spinnerNisbi.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val text: String = parent?.getItemAtPosition(position).toString()
                nisbi = text
            }

        }

        var semester = ""
        groupSemester.setOnCheckedChangeListener { _, id ->

            if(id == R.id.radioButtonGasal){
                semester = "gasal"
            }
            if(id== R.id.radioButtonGenap)
            {
                semester="genap"
            }
        }

        buttonSimpanPerubahan.setOnClickListener {

            val tahun = inputTahunAjaran.text.toString()
            //pengecekkan input tahun ajaran
            //code
            val queue = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2/myLulus13/updateMatkul.php"
            val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    Response.Listener {
                        Log.e("Update Matkul" , it)
                        if(it.contains("Berhasil")){
                            //Regis Berhasil
                            AlertDialog.Builder(this).apply {
                                setMessage("Update Berhasil")
                                setPositiveButton("Ok") { _, _->
                                    startActivity(Intent(this@UpdateMatkulActivity,MainActivity::class.java))
                                }
                                create().show()
                            }
                        }
                        else{
                            //Regis Gagal
                            AlertDialog.Builder(this).apply {
                                setMessage("Update Matkul Gagal!")
                                setPositiveButton("Ok",null)
                                create().show()
                            }
                        }
                    },
                    Response.ErrorListener {
                        Log.e("UpdateMatkul" , it.toString())
                    }
            ){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String,String>()
                    params["nrp"] = nrp
                    params["kode_mk"] = textViewKode.text.toString()
                    params["semester"] = semester
                    params["tahun_ambil"] = tahun
                    params["nisbi"] = nisbi
                    return params
                }
            }
            queue.add(stringRequest)

        }

    }
}