package com.example.fikridzakwan.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNoteActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsi)
    EditText edtIsi;
    @BindView(R.id.btnSave)
    Button btnSave;

    // TODO 2 Membuat variable yang dibutuhkan
    private Bundle bundle;
    private String judul, isi;
    private int id_judul;

    private DBNoteHelper dbNoteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        ButterKnife.bind(this);

        // TODO 3
        setTitle("Update Note");

        // Membuat object DBHelper
        dbNoteHelper = new DBNoteHelper(this);
        // Menangkap data Bundel
        bundle = getIntent().getExtras();

        // Pengecekan bundel
        if (bundle != null) {
            showData();
        }

    }

    private void showData() {
        // Mengambil data id  yang ada di dalam bundel
        id_judul = bundle.getInt(Constant.KEY_ID);
        judul = bundle.getString(Constant.KEY_JUDUL);
        isi = bundle.getString(Constant.KEY_ISI);

        // Menampilkan data ke layar
        edtJudul.setText(judul);
        edtIsi.setText(isi);
    }

    // TODO 1 Butterknife
    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        // TODO 4 Menyimpan data update ke SQLite
        // Mengambil data dari edtText
        getData();

        // Mengupdate data ke SQLite
        updateData();

        // Menghilangkan data di edtText agar terlihat update
        clearData();

        finish();
    }

    private void clearData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void updateData() {
        // Buat object SQLitedatabase untuk kita dapat menggunakan operasi SQLite, mode Read untuk update
        SQLiteDatabase database = dbNoteHelper.getReadableDatabase();

        // Menapung data ke dalam conten values
        ContentValues values = new ContentValues();
        // Mengisi data ke content values
        values.put(DBNoteHelper.MyColums.judul, judul);
        values.put(DBNoteHelper.MyColums.isi, isi);

        // Membuat Query untuk mencara data berdasarkan ID judul
        String selection = DBNoteHelper.MyColums.id_judul + " LIKE ? ";
        // Menampung id yang ditargetkan
        String[] selectionArgs = {String.valueOf(id_judul)};

        // Melakukan operasi update
        database.update(DBNoteHelper.MyColums.namaTable, values, selection, selectionArgs);
        Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        judul = edtJudul.getText().toString();
        isi = edtIsi.getText().toString();
    }
}
