package com.example.medicine_location.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine_location.Model.MedicineModel
import com.example.medicine_location.databinding.MedicineLayoutBinding

class MedicineAdapter(private val list: ArrayList<MedicineModel>) : RecyclerView.Adapter<MedicineAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MedicineLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        holder.binding.medName.text = data.name
        holder.binding.rowLabel.text = "ROW : "
        holder.binding.partitionLabel.text = "PARTITION : "
        holder.binding.drawerLabel.text = "DRAWER : "
        holder.binding.rowLabel.append(data.row)
        holder.binding.partitionLabel.append(data.partition)
        holder.binding.drawerLabel.append(data.drawer)
    }

    class MyViewHolder(val binding: MedicineLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}