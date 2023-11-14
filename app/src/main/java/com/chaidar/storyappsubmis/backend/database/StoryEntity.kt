package com.chaidar.storyappsubmis.backend.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

@Entity(tableName = "story")
data class StoryEntity (
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("description")
)