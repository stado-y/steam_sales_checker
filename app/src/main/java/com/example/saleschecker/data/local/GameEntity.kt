package com.example.saleschecker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saleschecker.data.local.GameEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    primaryKeys = ["id", "price", "discount_pct"],
)
data class GameEntity(
    val id: Int,
    val name: String,
    val image: String,
    val background: String,
    val review_percent: Int,
    val price: Int,
    val discount_pct: Int,
    val is_free_game: Boolean,
    val win: Int?,
    val mac: Int?,
    val linux: Int?,
) {
    companion object {
        const val TABLE_NAME = "games_table"
    }
}