package com.example.saleschecker.data.local.games

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.saleschecker.data.local.games.GameEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    //primaryKeys = ["id", "price", "discount_pct"],
)
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String? = null,
    val background: String? = null,
    val review_percent: Int,
    val price: Float,
    val discount_pct: Int,
    val is_free_game: Boolean,
    val win: Int? = null,
    val mac: Int? = null,
    val linux: Int? = null,
    val currency: String = "Default",
) {
    companion object {
        const val TABLE_NAME = "games_table"
    }
}