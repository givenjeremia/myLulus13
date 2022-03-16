package id.ac.ubaya.informatika.mylulus13

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_cek_kelulusan.*
import kotlinx.android.synthetic.main.fragment_profil.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CekKelulusanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CekKelulusanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val q = Volley.newRequestQueue(activity)
        val nrp = Global.getUserNRP(activity!!)
        val url = "http://10.0.2.2/myLulus13/cek_lulus.php?nrp=$nrp"
        var stringRequest = StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {
                    Log.e("apiresult", it)
                    //Baca JSON Object Utama
                    val obj = JSONObject(it)
                    //Cek Key Result
                    obj.getString("status") == "OK"
                    val ipk = obj.getString("ipk")
                    val ipk_min = obj.getString("ipk_min")
                    val sks = obj.getString("sks")
                    val sks_min = obj.getString("sks_min")
                    val nilai_d = obj.getString("nilai_d")
                    val nilai_d_max = obj.getString("nilai_d_max")
                    textViewIPK.setText("IPK : $ipk / $ipk_min")
                    textViewTotalSKS.setText("Total SKS : $sks / $sks_min")
                    textViewTotalSksNilai.setText("Total SKS Nilai D : $nilai_d / $nilai_d_max")
                    if (obj.getString("kesimpulan") == "ok"){
                        txthasil.setText("LULUS")
                        txthasil.setTextColor(Color.parseColor("#88D8C7"))
                    }
                    else{
                        txthasil.setText("TIDAK LULUS")
                        txthasil.setTextColor(Color.parseColor("#ff0000"))
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
        return inflater.inflate(R.layout.fragment_cek_kelulusan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabWhatsapp.setOnClickListener {
            //Buat Send Intent
            val sendIntent : Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT , txtMessage.text.toString())
//                type = "text/plain"
//                `package` = "com.whatsapp"
                //WHATAPP Intent
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://api.whatsapp.com/send?text=" +
                        txthasil.text.toString())

            }
            //Buat Share Intent
            val shareIntent : Intent = Intent.createChooser(sendIntent,"Kirim Menggunakan")
            startActivity(shareIntent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CekKelulusanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CekKelulusanFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}