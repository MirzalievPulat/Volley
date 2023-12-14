package uz.frodo.volley

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.frodo.volley.adapters.SecondAdapter
import uz.frodo.volley.databinding.ActivitySecondBinding
import uz.frodo.volley.databinding.BottomSheetBinding
import uz.frodo.volley.model.Currency

class SecondActivity : AppCompatActivity(),OnClick {
    lateinit var binding: ActivitySecondBinding
    var requestQueue:RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkHelper = NetworkHelper(this)


        if (networkHelper.isOnline()) {
            binding.progressBar.visibility = View.VISIBLE
            fetchData()
        } else {
            binding.textView2.visibility = View.VISIBLE
        }

    }

    private fun fetchData() {
        requestQueue = Volley.getRequestQueue(applicationContext)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, "http://cbu.uz/uz/arkhiv-kursov-valyut/json/", null,
            { jsonArray ->
                val type = object : TypeToken<List<Currency>>() {}.type
                val list: List<Currency> = Gson().fromJson(jsonArray.toString(), type)

                val adapter = SecondAdapter(this)
                adapter.submitList(list)
                binding.rvSecond.adapter = adapter

                binding.progressBar.visibility = View.GONE
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                binding.textView2.visibility = View.VISIBLE
                binding.textView2.text = error.message
            })

        jsonArrayRequest.tag = "currency"
        requestQueue?.add(jsonArrayRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (requestQueue != null){
            requestQueue?.cancelAll("currency")
        }
    }

    override fun onClick(currency: Currency) {
        val bottomSheet = BottomSheetDialog(this)
        val sheetBinding = BottomSheetBinding.inflate(layoutInflater)
        bottomSheet.setContentView(sheetBinding.root)

        sheetBinding.sheetName.text = currency.CcyNm_UZ
        sheetBinding.sheetShort.text = currency.Ccy
        sheetBinding.sheetAmount.text = currency.Rate
        if (currency.Diff.substring(0,1) == "-"){
            sheetBinding.sheetDiff.setTextColor(Color.RED)
            sheetBinding.sheetDiff.text = currency.Diff
        }else{
            sheetBinding.sheetDiff.setTextColor(Color.parseColor("#4CAF50"))
            sheetBinding.sheetDiff.text = "+"+currency.Diff
        }
        sheetBinding.sheetDate.text = currency.Date
        bottomSheet.show()
    }
}