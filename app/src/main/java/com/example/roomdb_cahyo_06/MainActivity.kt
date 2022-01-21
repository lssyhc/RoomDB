package com.example.roomdb_cahyo_06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb_cahyo_06.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.appcompat.app.AlertDialog
import com.example.roomdb_cahyo_06.room.*

class MainActivity : AppCompatActivity() {

    val dbHomework by lazy { HomeworkDatabase(this) }
    val dbTodolist by lazy { TodolistDatabase(this) }
    lateinit var homeworkAdapter: HomeworkAdapter
    lateinit var todolistAdapter: TodolistAdapter

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListenerHomework()
        setupRecyclerViewHomework()
        setupListenerTodolist()
        setupRecyclerViewTodolist()
    }

    override fun onStart() {
        super.onStart()
        loadHomeworks()
        loadTodolists()
    }

    fun loadHomeworks(){
        CoroutineScope(Dispatchers.IO).launch {
            val homeworks = dbHomework.homeworkDao().getHomeworks()
            Log.d("MainActivity","dbResponse: $homeworks")
            withContext(Dispatchers.Main){
                homeworkAdapter.setData(homeworks)
            }
        }
    }

    fun loadTodolists(){
        CoroutineScope(Dispatchers.IO).launch {
            val todolists = dbTodolist.todolistDao().getTodolists()
            Log.d("MainActivity","dbResponse: $todolists")
            withContext(Dispatchers.Main){
                todolistAdapter.setData(todolists)
            }
        }
    }

    private fun setupListenerHomework() {
        binding.homework.setOnClickListener {
            intentEditHomework(0, Constant.TYPE_CREATE)
        }
    }

    private fun setupListenerTodolist(){
        binding.todolist.setOnClickListener {
            intentEditTodolist(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEditHomework(homeworkID : Int, intentType : Int){
        startActivity(Intent(this@MainActivity, AddHomework::class.java)
            .putExtra("intent_id", homeworkID)
            .putExtra("intent_type", intentType)
        )
    }

    fun intentEditTodolist(todolistID : Int, intentType : Int){
        startActivity(Intent(this@MainActivity, AddTodolist::class.java)
            .putExtra("intent_id", todolistID)
            .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerViewHomework() {
        homeworkAdapter = HomeworkAdapter(arrayListOf(), object : HomeworkAdapter.OnAdapterChangeListener{
            override fun onRead(homework: Homework) {
                intentEditHomework(homework.id, Constant.TYPE_READ)
            }

            override fun onUpdate(homework: Homework) {
                intentEditHomework(homework.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(homework: Homework) {
                deleteDialogHomework(homework)
            }
        })
        binding.rvHomework.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = homeworkAdapter
        }
    }

    private fun setupRecyclerViewTodolist(){
        todolistAdapter = TodolistAdapter(arrayListOf(), object : TodolistAdapter.OnAdapterChangeListener{
            override fun onRead(todolist: Todolist) {
                intentEditTodolist(todolist.id, Constant.TYPE_READ)
            }

            override fun onUpdate(todolist: Todolist) {
                intentEditTodolist(todolist.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(todolist: Todolist) {
                deleteDialogTodolist(todolist)
            }
        })
        binding.rvTodolist.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = todolistAdapter
        }
    }

    private fun deleteDialogHomework(homework: Homework){
        val alertDeleteDialog = AlertDialog.Builder(this)
        alertDeleteDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin untuk menghapus ${homework.title}")
            setNegativeButton("Batal") { dialogInterface, i -> dialogInterface.dismiss() }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    dbHomework.homeworkDao().deleteHomework(homework)
                    loadHomeworks()
                }
            }
        }
        alertDeleteDialog.show()
    }

    private fun deleteDialogTodolist(todolist: Todolist){
        val alertDeleteDialog = AlertDialog.Builder(this)
        alertDeleteDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin untuk menghapus ${todolist.title}")
            setNegativeButton("Batal") { dialogInterface, i -> dialogInterface.dismiss() }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    dbTodolist.todolistDao().deleteTodolist(todolist)
                    loadTodolists()
                }
            }
        }
        alertDeleteDialog.show()
    }
}