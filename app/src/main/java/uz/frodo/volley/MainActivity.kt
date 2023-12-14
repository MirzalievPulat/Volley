package uz.frodo.volley

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.frodo.volley.adapters.MainAdapter
import uz.frodo.volley.databinding.ActivityMainBinding
import uz.frodo.volley.model.Users
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var pDialog:ProgressDialog
    var requestQueue:RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkHelper = NetworkHelper(this)

        pDialog = ProgressDialog(this)
        pDialog.setMessage("Please wait...")
        pDialog.setCancelable(false)


        if (networkHelper.isOnline()){
            pDialog.show()
            fetchData()
        }else{
            binding.textView.visibility = View.VISIBLE
        }
    }


    private fun fetchData() {
        requestQueue = Volley.getRequestQueue(applicationContext)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,"https://api.github.com/users",null,
            {jsonArray->
                val type = object : TypeToken<List<Users>>(){}.type
                val list:List<Users> = Gson().fromJson(jsonArray.toString(),type)

                val adapter = MainAdapter()
                adapter.submitList(list)
                binding.rvMain.adapter = adapter

                pDialog.hide()
            },
            {error->
                pDialog.hide()
                binding.textView.visibility  = View.VISIBLE
                binding.textView.text = error.message
            })

        jsonArrayRequest.tag = "user"
        requestQueue?.add(jsonArrayRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (requestQueue != null){
            requestQueue?.cancelAll("currency")
        }
    }
}