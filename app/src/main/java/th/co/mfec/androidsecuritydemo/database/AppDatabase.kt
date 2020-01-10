package th.co.mfec.androidsecuritydemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import th.co.mfec.androidsecuritydemo.database.dao.UserDao
import th.co.mfec.androidsecuritydemo.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }

    }
}