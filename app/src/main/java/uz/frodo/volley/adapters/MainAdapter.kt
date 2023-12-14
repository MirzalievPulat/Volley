package uz.frodo.volley.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.frodo.volley.databinding.ItemMainBinding
import uz.frodo.volley.model.Users

class MainAdapter(): ListAdapter<Users,MainAdapter.MyViewHolder>(MyDiffUtil()) {
    class MyViewHolder(val binding:ItemMainBinding): RecyclerView.ViewHolder(binding.root)

    class MyDiffUtil: DiffUtil.ItemCallback<Users>(){
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        Picasso.get().load(item.avatar_url).into(holder.binding.shapeableImage)
        holder.binding.login.text = item.login
    }

}