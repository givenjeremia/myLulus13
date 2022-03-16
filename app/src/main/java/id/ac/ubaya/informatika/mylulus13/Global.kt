package id.ac.ubaya.informatika.mylulus13

import android.app.Activity
import android.content.Context



object Global {
    val EXTRA_NRP = "NRP"
    val EXTRA_KodeMatkul = "KodeMatkul"
    val EXTRA_NamaMatkul = "NamaMatkul"
    val EXTRA_SKS = "JumlahSKS"
    val EXTRA_nisbi = "nisbi"
    val EXTRA_SEMESTER = "semester"
    val EXTRA_TAHUN_AJARAN = "tahun_ajaran"

    //Simpan NRP
    const val SHARED_PREFERENCES = "mykuliah"
    const val SHARED_PREF_KEY_NRP_USER = "nrp_user"
    fun getUserNRP(context: Context): String? {
        return context.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE)
                .getString(SHARED_PREF_KEY_NRP_USER, null)
    }
}

