package com.example.ccproject

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ccproject.Aid.ToDoAdapter
import com.example.ccproject.Handles.DatabaseHandler
import com.example.ccproject.Models.ToDoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.penguincoders.doit.RecyclerItemTouchHelper

import java.util.Objects


class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var db: DatabaseHandler
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var taskList: MutableList<ToDoModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DatabaseHandler(this)
        db.openDatabase()
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(db, this@MainActivity)
        tasksRecyclerView.adapter = tasksAdapter
        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
        fab = findViewById(R.id.fab)
        taskList = db.allTasks.toMutableList()

        taskList.reverse()

        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(
                supportFragmentManager, AddNewTask.TAG
            )
        }
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.allTasks
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}