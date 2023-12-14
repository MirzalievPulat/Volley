package uz.frodo.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object Volley {
    private var requestQueue:RequestQueue? = null

    fun getRequestQueue(context: Context):RequestQueue{
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.applicationContext)
        }
        return requestQueue!!
    }

}