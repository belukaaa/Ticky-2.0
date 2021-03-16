package com.raywenderlich.ticky

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
class Taskie (
    @PrimaryKey(autoGenerate = true)
    val taskId : Int ,
    val title : String ,
    val color : String ?= null,
    val datetime: String? = null,
    var checked: Boolean = false,
    var selected: Boolean = false,
    var dateLong: Long? = null ,
    var sortingColor : Int

)