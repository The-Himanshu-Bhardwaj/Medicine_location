package com.example.medicine_location.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_location.FirebaseUtil
import com.example.medicine_location.Model.MedicineModel
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var database: FirebaseDatabase
    private var reference: DatabaseReference
    private var _medicineLiveList = MutableLiveData<ArrayList<MedicineModel>>()
    private var _listOfData = ArrayList<MedicineModel>()
    val finalMedicineList get() = _medicineLiveList


    init {
        database = FirebaseUtil.getFirebaseDatabaseInstance()
        reference = database.getReference("alldata")
        reference.keepSynced(true)

//        val data = HashMap<String, String>()
//        data.apply {
//            put("name", "himanshu")
//            put("row", "SECOND")
//            put("drawer", "1")
//            put("partition", "1")
//        }
//        reference.push().setValue(data)

        viewModelScope.launch(Dispatchers.IO) {
            getMedicineList()
        }

    }

    private fun getMedicineList() {
        _listOfData.clear()
       reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data : HashMap<String, Any> = snapshot.value as HashMap<String, Any>

                for (medData in data.entries) {
                    val medicineModel = MedicineModel()
                    val singleMed : HashMap<String, Any> = medData.value as HashMap<String, Any>

                    medicineModel.name = singleMed["name"].toString()
                    medicineModel.drawer = singleMed["drawer"].toString()
                    medicineModel.row = singleMed["row"].toString()
                    medicineModel.partition = singleMed["partition"].toString()
                    medicineModel.firebaseKey = medData.key

                    _listOfData.add(medicineModel)
                    _medicineLiveList.postValue(_listOfData)
                    Log.d("@@", "list - >>> ${_listOfData.size}")
                }

            }

            override fun onCancelled(error: DatabaseError) {
               // show snackbar here
            }
        })
    }
}