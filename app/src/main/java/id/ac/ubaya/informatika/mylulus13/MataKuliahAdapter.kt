package id.ac.ubaya.informatika.mylulus13

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.color.MaterialColors
import kotlinx.android.synthetic.main.matakuliah_card_layout.view.*
import org.json.JSONObject

class MataKuliahAdapter(val context: Context, val matakuliahs: ArrayList<MataKuliah>) : RecyclerView.Adapter<MataKuliahAdapter.MataKuliahViewHolder>() {

    val userNRP = Global.getUserNRP(context)

    class MataKuliahViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MataKuliahViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.matakuliah_card_layout, parent, false)

        return MataKuliahViewHolder(v)
    }

    override fun getItemCount() = matakuliahs.size

    override fun onBindViewHolder(holder: MataKuliahViewHolder, position: Int) {
        val matakuliah = matakuliahs[position]
        with(holder.view) {
            //Panggil NRP
            val nrp = userNRP
            //Dalam Keaadan Default
            textViewKodeMataKuliah.text = matakuliah.kode
            textViewNamaMataKuliah.text = matakuliah.nama
            //Cek Semester Kosong
            var sksDanTahun = ""
            if (matakuliah.semester!="") sksDanTahun =  "${matakuliah.sks} SKS (${matakuliah.semester} ${matakuliah.tahun_ambil})"
            else sksDanTahun =  "${matakuliah.sks} SKS"
            textViewSksDanTahun.text = sksDanTahun
            textViewNisbi.text = matakuliah.nisbi
            Log.e("LOG_TAG", matakuliah.toString())
            //Color Thema
            R.color.design_default_color_surface
            if (matakuliah.diambil) {
                cardView.setCardBackgroundColor(Color.parseColor("#5DADE2"))
                buttonAmbil.visibility = View.GONE
            }
            else {
                cardView.setBackgroundColor(MaterialColors.getColor(this, R.attr.colorSurface))
                buttonUpdate.visibility = View.GONE
            }

            buttonAmbil.setOnClickListener {
                val intent = Intent(context, AmbilMatkulActivity::class.java)
                intent.putExtra(Global.EXTRA_NRP, nrp)
                intent.putExtra(Global.EXTRA_KodeMatkul, matakuliah.kode)
                intent.putExtra(Global.EXTRA_NamaMatkul, matakuliah.nama)
                intent.putExtra(Global.EXTRA_SKS, matakuliah.sks)
                context.startActivity(intent)
            }
            buttonUpdate.setOnClickListener {
                val  intent = Intent(context, UpdateMatkulActivity::class.java)
                intent.putExtra(Global.EXTRA_NRP,nrp)
                intent.putExtra(Global.EXTRA_KodeMatkul, matakuliah.kode)
                intent.putExtra(Global.EXTRA_NamaMatkul, matakuliah.nama)
                intent.putExtra(Global.EXTRA_SKS, matakuliah.sks)
                intent.putExtra(Global.EXTRA_SEMESTER, matakuliah.semester)
                intent.putExtra(Global.EXTRA_TAHUN_AJARAN, matakuliah.tahun_ambil)
                intent.putExtra(Global.EXTRA_nisbi, matakuliah.nisbi)
                context.startActivity(intent)
            }
        }

    }
}