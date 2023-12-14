package uz.frodo.volley

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.frodo.volley.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firstTask.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.secondTask.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }
}