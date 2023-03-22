package com.example.medicine_location

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.medicine_location.Adapter.MedicineAdapter
import com.example.medicine_location.Model.MedicineModel
import com.example.medicine_location.ViewModel.HomeViewModel
import com.example.medicine_location.databinding.ActivityHomeAcitivtyBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeAcitivtyBinding
    private lateinit var adapter: MedicineAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var snackView: View

    private var _finalList = ArrayList<MedicineModel>()
    private var filteredLst = ArrayList<MedicineModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        snackView = findViewById(android.R.id.content)
        getAllMedicines()


        binding.refreshButton.setOnClickListener {
            showSnackbar("Refreshing list please wait...")
            getAllMedicines()
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewMedActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.Main) {
                    if (newText != null && newText != "") {
                        delay(500)
                        filterList(newText)
                    } else {
                        adapter = MedicineAdapter(_finalList)
                        binding.recylerView.adapter = adapter
                    }
                }
                return false
            }

        })

        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                getAllMedicines()
                return false
            }
        })

    }

    fun filterList(newText: String) {
        filteredLst.clear()


        for (model in _finalList) {
            val name = model.name
            val result = name!!.replace("\\s".toRegex(), "").lowercase()
            if (result.contains(newText.lowercase())) {
                filteredLst.add(model)
            }
        }
        adapter = MedicineAdapter(filteredLst)
        binding.recylerView.adapter = adapter


    }

    private fun getAllMedicines() {

        binding.recylerView.visibility = View.GONE
        binding.shimmerAnimation.visibility = View.VISIBLE
        binding.shimmerAnimation.startShimmer()

        viewModel.finalMedicineList.observe(this) { liveDataList ->
            _finalList = liveDataList
            binding.textView2.text = "Medicine & Other Items (${_finalList.size})"
            adapter = MedicineAdapter(_finalList)
            binding.recylerView.adapter = adapter
            binding.shimmerAnimation.stopShimmer()
            binding.shimmerAnimation.visibility = View.GONE
            binding.recylerView.visibility = View.VISIBLE

        }


    }

    private fun showSnackbar(message: String) {

        val snackbar = Snackbar.make(snackView, message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbar.setDuration(500)
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray_text))
        snackbar.show()

    }

    override fun onBackPressed() {

        val snackbar = Snackbar.make(snackView, "Exit Application ?", Snackbar.LENGTH_LONG)
        snackbar.setAction("Yes") {
            finishAffinity()
        }.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarTextColor))
        snackbar.show()

    }
}