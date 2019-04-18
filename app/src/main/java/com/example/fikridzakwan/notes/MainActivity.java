package com.example.fikridzakwan.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.fikridzakwan.notes.Adapter.NotesAdapter;
import com.example.fikridzakwan.notes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    @BindView(R.id.fabMain)
    FloatingActionButton fabMain;

    // Variable untuk DBHelper
    private DBNoteHelper dbNoteHelper;
    // Penampung Data
    private List<NotesModel> dataNoteList;
    // Membuat variable adapter
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Membuat object DBHelper
        dbNoteHelper = new DBNoteHelper(this);

        // Kita isi variable list
        dataNoteList = new ArrayList<>();

        // KIta ambil data dari SQLite
        getData();

        // Buat object adapter
        notesAdapter = new NotesAdapter(this, dataNoteList);

        // Setting Layoutmanager
        rvMain.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        // Memasang  adapter ke recyclerview
        rvMain.setAdapter(notesAdapter);
    }

    private void getData() {
        // Kita membuat object SQLite database dgn mode Read
        SQLiteDatabase readData = dbNoteHelper.getReadableDatabase();

        // Membuat perintah mengambil data
        // "SELECT * FROM Notes ORDER BY ID Judul DESC"
        String query = "SELECT * FROM " + DBNoteHelper.MyColums.namaTable + " ORDER BY " + DBNoteHelper.MyColums.id_judul + " DESC";

        // KIta akan mengambil data menggunakan cursor
        Cursor cursor = readData.rawQuery(query, null);

        // Kita arahkan cursor ke awal
        cursor.moveToFirst();

        // Menambil data secara berulang
        for (int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            dataNoteList.add(new NotesModel(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataNoteList.clear();
        getData();
        notesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fabMain)
    public void onViewClicked() {
        // Perintah untuk berpindah ke activity tambah
        startActivity(new Intent(this, TambahNoteActivity.class));
    }
}
