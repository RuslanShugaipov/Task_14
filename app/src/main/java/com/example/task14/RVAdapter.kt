package com.example.task14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task14.databinding.StringItemBinding
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class RVAdapter : RecyclerView.Adapter<RVAdapter.RVHolder>() {
    private val stringList = ArrayList<String>()
    private var style = 0

    class RVHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = StringItemBinding.bind(item)
        fun bind(string: String) = with(binding) {
            tvString.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.string_item, parent, false)
        return RVHolder(view)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        when (style) {
            0 -> {
                holder.itemView.findViewById<TextView>(R.id.tvString)
                    .setTextAppearance(R.style.tvStyle1)
            }
            1 -> {
                holder.itemView.findViewById<TextView>(R.id.tvString)
                    .setTextAppearance(R.style.tvStyle2)
            }
            2 -> {
                holder.itemView.findViewById<TextView>(R.id.tvString)
                    .setTextAppearance(R.style.tvStyle3)
            }
        }
        holder.bind(stringList[position])
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

    fun addString(string: String) {
        stringList.add(string)
        notifyDataSetChanged()
    }

    fun changeStyle() {
        style = nextInt(3)
    }

    fun clear(){
        val size = stringList.size
        stringList.clear()
    }
}