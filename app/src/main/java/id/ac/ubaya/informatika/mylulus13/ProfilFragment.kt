package id.ac.ubaya.informatika.mylulus13

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.matakuliah_card_layout.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val q = Volley.newRequestQueue(activity)
        val nrp = Global.getUserNRP(activity!!)
        val url = "http://10.0.2.2/myLulus13/profil.php?nrp=$nrp"
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> {
                Log.e("apiresult", it)
                //Baca JSON Object Utama
                val obj = JSONObject(it)
                //Cek Key Result
                if(obj.getString("result") == "OK") {
                    //Dapatkan JSON ARRAY
                    val data = obj.getJSONArray("data")
                    //Baca Satu Satu
                    for(i in 0 until data.length()) {
                        //Ambil JSON Object untuk setiap index
                        val dataobj = data.getJSONObject(i)
                        EditTextNRP.setText(dataobj.getString("nrp"))
                        EditTextNama.setText(dataobj.getString("nama"))
                        EditTextTahunMasuk.setText(dataobj.getString("angkatan"))
                    }
                    Log.e("Profil" , it)
                }

            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogout.setOnClickListener {
            activity!!.getSharedPreferences(Global.SHARED_PREFERENCES, Activity.MODE_PRIVATE).edit {
                remove(Global.SHARED_PREF_KEY_NRP_USER)
            }
            requireActivity().finish()
            startActivity(Intent(activity!!, LoginActivity::class.java))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}