package com.example.roomdb_cahyo_06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.roomdb_cahyo_06.databinding.ActivityAddTodolistBinding
import com.example.roomdb_cahyo_06.room.Constant
import com.example.roomdb_cahyo_06.room.Todolist
import com.example.roomdb_cahyo_06.room.TodolistDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTodolist : AppCompatActivity() {

    val db by lazy { TodolistDatabase(this) }
    private lateinit var binding: ActivityAddTodolistBinding

    private var todolistID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListener()
        todolistID = intent.getIntExtra("intent_id", 0)
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
                getTodolist()
            }
            Constant.TYPE_UPDATE -> {
                binding.btnSave.visibility = View.GONE
                getTodolist()
            }
        }
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.todolistDao().addTodolist(
                    Todolist(0, binding.etTitle.text.toString().trim(), binding.etDescription.text.toString().trim())
                )
                finish()
            }
        }
        binding.btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.todolistDao().updateTodolist(
                    Todolist(todolistID, binding.etTitle.text.toString().trim(), binding.etDescription.text.toString().trim())
                )
                finish()
            }
        }
    }

    fun getTodolist() {
        todolistID = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val todolists = db.todolistDao().getTodolist(todolistID)[0]
            binding.etTitle.setText(todolists.title)
            binding.etDescription.setText(todolists.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
