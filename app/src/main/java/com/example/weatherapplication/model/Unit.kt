package com.example.weatherapplication.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "units_tbl")
data class Unit(
    @PrimaryKey
    @ColumnInfo(name = "units")
    val unit: String
)
