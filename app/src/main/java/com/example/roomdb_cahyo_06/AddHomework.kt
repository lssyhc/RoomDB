package com.example.roomdb_cahyo_06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.roomdb_cahyo_06.databinding.ActivityAddHomeworkBinding
import com.example.roomdb_cahyo_06.room.Constant
import com.example.roomdb_cahyo_06.room.Homework
import com.example.roomdb_cahyo_06.room.HomeworkDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHomework : AppCompatActivity() {

    val db by lazy { HomeworkDatabase(this) }
    private lateinit var binding: ActivityAddHomeworkBinding

    private var homeworkID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListener()
        homeworkID = intent.getIntExtra("intent_id", 0)
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                binding.btnUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                binding.btnSave.visibility = View.GONE
                binding.btnUpdate.visibility = View.GONE
                getHomework()
            }
            Constant.TYPE_UPDATE -> {
                binding.btnSave.visibility = View.GONE
                getHomework()
            }
        }
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.homeworkDao().addHomework(
                    Homework(0, binding.etTitle.text.toString().trim(), binding.etDescription.text.toString().trim())
                )
                finish()
            }
        }
        binding.btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.homeworkDao().updateHomework(
                    Homework(homeworkID, binding.etTitle.text.toString().trim(), binding.etDescription.text.toString().trim())
                )
                finish()
            }
        }
    }

    fun getHomework() {
        homeworkID = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val homeworks = db.homeworkDao().getHomework(homeworkID)[0]
            binding.etTitle.setText(homeworks.title)
            binding.etDescription.setText(homeworks.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
