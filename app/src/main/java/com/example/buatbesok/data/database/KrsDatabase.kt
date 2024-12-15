package com.example.buatbesok.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buatbesok.data.dao.MahasiswaDao
import com.example.buatbesok.data.entity.Mahasiswa

//Mendefinisikan database dengan tabel Mahasiswa
@Database(entities = [Mahasiswa::class], version =1, exportSchema = false)
abstract class  KrsDatabase : RoomDatabase() {

    //Mendefinisikan fungsi untuk mengakses data Mahasiswa
    abstract fun mahasiswaDao(): MahasiswaDao

    companion object {
        @Volatile //Memastikan bahwa nilai variable Insance selalu sama di
        private var Instance: KrsDatabase? = null

        fun geDtabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java,
                    "KrsDatabase"
                )
                    .build().also { Instance }
            })
        }
    }
}