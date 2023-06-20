package com.example.medicine_location.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicine_location.FirebaseUtil
import com.example.medicine_location.Model.MedicineModel
import com.example.medicine_location.databinding.MedicineLayoutBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Objects
import kotlin.math.log

class MedicineAdapter(private val list: ArrayList<MedicineModel>) : RecyclerView.Adapter<MedicineAdapter.MyViewHolder>() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    var firebaseKey : String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        database = FirebaseUtil.getFirebaseDatabaseInstance()
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

        holder.itemView.setOnClickListener {
            Log.d("@@", "onBindViewHolder: $position")
            val data = HashMap<String, String>()
            data.apply {
                put("drawer", "ajsdgajdfkjajhks")
                put("name", list[position].name!!)
                put("partition", list[position].partition!!)
                put("row", list[position].row!!)
            }
            reference = database.getReference("alldata").child(list[position].firebaseKey!!)
            reference.setValue(data)


        }
    }

    class MyViewHolder(val binding: MedicineLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}