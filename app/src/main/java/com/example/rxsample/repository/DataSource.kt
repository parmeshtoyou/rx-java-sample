package com.example.rxsample.repository

import com.example.rxsample.model.Task

class DataSource {

    companion object {
        fun createTaskList() = listOf<Task>(
            Task("Description1", true, 5),
            Task("Description2", true, 5),
            Task("Description3", false, 4),
            Task("Description4", true, 3),
            Task("Description5", true, 5),
            Task("Description6", false, 1),
            Task("Description7", true, 10)
        )
    }

}