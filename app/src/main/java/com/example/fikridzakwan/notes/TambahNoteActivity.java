package com.example.fikridzakwan.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahNoteActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsi)
    EditText edtIsi;
    @BindView(R.id.btnSave)
    Button btnSave;

    // Buat variable untuk database
    private DBNoteHelper dbNoteHelper;

    // Buat variable unutuk menanmpung data dari user
    private String getJudul,getIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_note);
        ButterKnife.bind(this);

        // Buat mengganti judul actionbar
        setTitle("Add New Data");

        // Buat object untuk memanggil db helper
        dbNoteHelper = new DBNoteHelper(this);
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        getData();
        saveData();
    }

    private void saveData() {
        // Membuat object SQLiteDatabase degan mode menulis
        SQLiteDatabase create = dbNoteHelper.getWritableDatabase();

        // Kita tampung data dari user ke dalma ContentValues agar meringkas
        ContentValues values = new ContentValues();
        values.put(DBNoteHelper.MyColums.judul, getJudul);
        values.put(DBNoteHelper.MyColums.isi, getIsi);

        // Tambahkan data baru ke dalama table
        create.insert(DBNoteHelper.MyColums.namaTable, null,values);
        Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();

        // Finish
        cleanData();
        finish();
    }

    private void cleanData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void getData() {
        // Memanggil dara inputan user
        getJudul = edtJudul.getText().toString();
        getIsi = edtJudul.getText().toString();
    }
}
