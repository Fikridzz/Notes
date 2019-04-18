package com.example.fikridzakwan.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBNoteHelper  extends SQLiteOpenHelper {

    // TODO 4 membuat variable constant nama table dan nama kolom yang digunakan
    // Tabel ini bisa diakses Class manapun
    public static abstract class MyColums implements BaseColumns{
        public static final String namaTable = "Notes";
        public static final String id_judul = "id_judul";
        public static final String judul = "Judul";
        public static final String isi = "Isi";
    }

    // TODO 2 Membuat variable namaDB dari versi
    private static final String namaDatabase = "catatan.db";
    private static final int versionDatabase = 1;

    // TODO 3 Membuat kontruktor DBHelper
    public  DBNoteHelper(Context context) {
        super(context, namaDatabase, null, versionDatabase);
    }

    // TODO 1 implement method dari SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 6 Menjalankan perintah untuk membut table
        db.execSQL(SQL_CREATE_TABLE);

    }

    // TODO 5 Perintah untuk membuat table
    // "CREATE TABLE Notes(ID Judul INTEGER PRIAMERY KEY AUTOINCREMENT, Judul TEXT NOT NULL, Isi TEXT NOT NULL)
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MyColums.namaTable
            + "(" + MyColums.id_judul + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MyColums.judul + " TEXT NOT NULL, " + MyColums.isi + " TEXT NOT NULL)";

    //TODO 7 Perintah untuk menghapus table
    // "DROP TABLE IF EXSISTS Notes
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MyColums.namaTable;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);

    }
}
