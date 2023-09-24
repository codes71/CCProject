package com.example.ccproject.Models

class ToDoModel {
    @JvmField
    var id = 0
    @JvmField
    var status = 0
    @JvmField
    var task: String? = null
        override fun toString(): String {
            return "$task"
        }

}