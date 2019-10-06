package io.kaendagger.apodcompanion.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class ApodOffline(
    @PrimaryKey
    val date:String,
    val explanation:String,
    val title:String,
    val path:String
)