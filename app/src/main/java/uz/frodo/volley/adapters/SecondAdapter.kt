package uz.frodo.volley.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.frodo.volley.OnClick
import uz.frodo.volley.databinding.ItemSecondBinding
import uz.frodo.volley.model.Currency

class SecondAdapter(val onClick: OnClick): ListAdapter<Currency,SecondAdapter.MyViewHolder>(MyDiffUtil()) {
    class MyViewHolder(val binding:ItemSecondBinding): RecyclerView.ViewHolder(binding.root)

    class MyDiffUtil: DiffUtil.ItemCallback<Currency>(){
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSecondBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.currency1.text = item.CcyNm_UZ
        holder.binding.currency2.text = item.Rate

        if (item.Diff.substring(0,1) == "-"){
            holder.binding.currency3.setTextColor(Color.RED)
            holder.binding.currency3.text = item.Diff
        }else{
            holder.binding.currency3.setTextColor(Color.parseColor("#4CAF50"))
            holder.binding.currency3.text = "+"+item.Diff
        }

        holder.binding.container.setOnClickListener {
            onClick.onClick(item)
        }
    }

}