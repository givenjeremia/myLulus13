package id.ac.ubaya.informatika.mylulus13

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_mata_kuliah.*
import kotlinx.android.synthetic.main.fragment_mata_kuliah.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MataKuliahFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MataKuliahFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var matakuliahs:ArrayList<MataKuliah> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nrp = Global.getUserNRP(activity!!)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val q = Volley.newRequestQueue(activity)
        val url = "http://10.0.2.2/myLulus13/matakuliah.php?nrp=$nrp"
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                //Baca JSON Object Utama
                val obj = JSONObject(it)
                //Cek Key Result
                if(obj.getString("result") == "OK") {
                    //Dapatkan JSON ARRAY
                    val data = obj.getJSONArray("data")
                    //Baca Satu Satu
                    for(i in 0 until data.length()) {
                        //Ambil JSON Object untuk setiap index
                        val playObj = data.getJSONObject(i)
                        //Cek Apakah Sudah Di Abil Atau Tidak
                        var diambil = false
                        var nisbi = ""
                        var semester = ""
                        var tahun_ambil=""
                        if (playObj.getString("diambil") == "1" && playObj.getString("nisbi") !=null && playObj.getString("nisbi") !=null && playObj.getString("tahun_ambil") !=null){
                            diambil = true
                            nisbi = playObj.getString("nisbi")
                            semester = playObj.getString("semester")
                            tahun_ambil = playObj.getString("tahun_ambil")
                        }
                        val matakuliah = MataKuliah(
                            playObj.getString("kode"),
                            playObj.getString("nama"),
                            playObj.getInt("sks"),
                                diambil,
                                nisbi,
                                semester,
                                tahun_ambil

                        )
                        matakuliahs.add(matakuliah)
                    }
                    updateList()
                    Log.d("apiresult",matakuliahs.toString())
                }

            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)


    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mata_kuliah, container, false)
    }
    fun updateList() {
        val lm = LinearLayoutManager(activity)
        view?.matakuliahView?.let {
            it.layoutManager = lm
            it.setHasFixedSize(true)
            it.adapter = MataKuliahAdapter(activity!!, matakuliahs)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MataKuliahFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MataKuliahFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}