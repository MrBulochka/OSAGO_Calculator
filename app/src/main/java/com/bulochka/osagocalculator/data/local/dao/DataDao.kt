package com.bulochka.osagocalculator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bulochka.osagocalculator.data.model.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {
    @Query("SELECT * FROM data_table")
    fun getData(): Flow<List<Data>>

    @Query("SELECT * FROM data_table WHERE id = :id")
    suspend fun getDataById(id: Int): Data

    @Insert
    suspend fun insertData(data: List<Data>)

    @Update
    suspend fun updateData(data: Data)

    @Update
    suspend fun updateAllData(listData: List<Data>)
}