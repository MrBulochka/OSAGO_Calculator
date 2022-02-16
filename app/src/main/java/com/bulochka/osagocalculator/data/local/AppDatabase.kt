package com.bulochka.osagocalculator.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bulochka.osagocalculator.utils.DataSource
import com.bulochka.osagocalculator.data.local.dao.CoefficientDao
import com.bulochka.osagocalculator.data.local.dao.DataDao
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Coefficient::class, Data::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun coefficientDao(): CoefficientDao
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App database"
                )
                    .addCallback(AppDatabaseCallBack(scope, context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
    private class AppDatabaseCallBack(
        private val scope: CoroutineScope,
        private val context: Context
    ): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val coefficientDao = database.coefficientDao()
                    val dataDao = database.dataDao()

                    coefficientDao.insertCoefficients(DataSource(context).COEFFICIENTS)
                    dataDao.insertData(DataSource(context).DATA)
                }
            }
        }
    }
}