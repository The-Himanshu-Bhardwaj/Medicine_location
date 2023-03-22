package com.example.medicine_location

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.medicine_location.databinding.ActivityNewMedBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewMedActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var snackView: View

    private lateinit var binding: ActivityNewMedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        snackView = findViewById(android.R.id.content)

        database = FirebaseUtil.getFirebaseDatabaseInstance()
        reference = database.getReference("alldata")

        binding.submitButton.setOnClickListener {
            binding.loadingCircle.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                validateAndPush()
            }
        }

        binding.clearButton.setOnClickListener {
            clearFields()
            showSnackbar("Cleared All Fields !")
        }

        binding.first.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("FIRST")
        }
        binding.second.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("SECOND")
        }
        binding.third.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("THIRD")
        }
        binding.fourth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("FOURTH")
        }
        binding.fifth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("FIFTH")
        }
        binding.sixth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("SIXTH")
        }
        binding.seventh.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("SEVENTH")
        }
        binding.eighth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("EIGHTH")
        }
        binding.nineth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("NINTH")
        }
        binding.tenth.setOnClickListener {
            binding.rowBox.text = null
            binding.rowBox.setText("TENTH")
        }

        // PARTITION

        binding.first1.setOnClickListener {
            binding.partitionBox.text = null
            binding.partitionBox.setText("FIRST")
        }
        binding.second1.setOnClickListener {
            binding.partitionBox.text = null
            binding.partitionBox.setText("SECOND")
        }
        binding.third3.setOnClickListener {
            binding.partitionBox.text = null
            binding.partitionBox.setText("THIRD")
        }
        binding.fourth4.setOnClickListener {
            binding.partitionBox.text = null
            binding.partitionBox.setText("FOURTH")
        }
        binding.fifth5.setOnClickListener {
            binding.partitionBox.text = null
            binding.partitionBox.setText("FIFTH")
        }

    }

    private fun validateAndPush() {
        val name = binding.nameBox.text.toString().uppercase()
        val row = binding.rowBox.text.toString().uppercase()
        val partition = binding.partitionBox.text.toString().uppercase()
        val drawer = binding.drawerBox.text.toString().uppercase()

        if (name.isBlank() || name.isEmpty() ||
            row.isBlank() || row.isEmpty() ||
            partition.isBlank() || partition.isEmpty() ||
            drawer.isBlank() || drawer.isEmpty() ) {

            showSnackbar("All field are compulsory !")
        } else {
            val data : HashMap<String, Any> = HashMap()
            data.apply {
                put("name", name)
                put("drawer", drawer)
                put("partition", partition)
                put("row", row)
            }

            reference.push().setValue(data).addOnSuccessListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.loadingCircle.visibility = View.GONE
                }
                showSnackbar("Saved Successfully !")
                clearFields()
            }.addOnFailureListener {
                showSnackbar("FAILED ! PLEASE CHECK INTERNET or Contact Me !")
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@NewMedActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showSnackbar(message: String) {

        val snackbar = Snackbar.make(snackView, message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        snackbar.setDuration(800)
        val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackbar.view.layoutParams = params
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray_text))
        snackbar.show()

    }

    private fun clearFields() {
        binding.nameBox.text = null
        binding.rowBox.text = null
        binding.partitionBox.text = null
        binding.drawerBox.text = null
    }

}
