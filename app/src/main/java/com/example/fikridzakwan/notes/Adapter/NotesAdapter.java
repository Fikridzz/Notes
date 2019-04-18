package com.example.fikridzakwan.notes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fikridzakwan.notes.Constant;
import com.example.fikridzakwan.notes.DBNoteHelper;
import com.example.fikridzakwan.notes.R;
import com.example.fikridzakwan.notes.UpdateNoteActivity;
import com.example.fikridzakwan.notes.model.NotesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final Context context;
    private final List<NotesModel> dataNotesList;

    // Bundel untuk menampung data yang banyak menjadi 1
    private Bundle bundle;

    public NotesAdapter(Context context, List<NotesModel> dataNotesList) {
        this.context = context;
        this.dataNotesList = dataNotesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Mengambil data dari DataNoteList
        final NotesModel dataNotes = dataNotesList.get(i);

        final String id = String.valueOf(dataNotes.getId_());

        viewHolder.tvJudul.setText(dataNotes.getJudul());
        viewHolder.tvIsi.setText(dataNotes.getIsi());

        // Membuat onClick untuk item
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat object bundle
                bundle = new Bundle();
                // Memasukan data ke dalam bundle
                bundle.putInt(Constant.KEY_ID, dataNotes.getId_());
                bundle.putString(Constant.KEY_JUDUL, dataNotes.getJudul());
                bundle.putString(Constant.KEY_ISI, dataNotes.getIsi());

                // Berpindah halaman dan membawa data
                context.startActivity(new Intent(context, UpdateNoteActivity.class).putExtras(bundle));

            }
        });

        // Membuat onClick pada overflow
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Mmbuat pop-up menu
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                // Inflate design xml untuk pop-up menu
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                // Membuat object DB helper
                                DBNoteHelper dbNoteHelper = new DBNoteHelper(v.getContext());

                                // Membuat SQLitedatabase degan mode write
                                SQLiteDatabase deleteData = dbNoteHelper.getWritableDatabase();

                                // Membuat query untuk mencari id_judul
                                String selection = DBNoteHelper.MyColums.id_judul + " LIKE ?";

                                // Menambil data ID
                                String[] selectionArgs = {id};

                                // Perintah delete
                                deleteData.delete(DBNoteHelper.MyColums.namaTable, selection, selectionArgs);

                                // Menghapus data di dalam list
                                dataNotesList.remove(i);

                                notifyItemRemoved(i);
                                notifyItemRangeChanged(0, dataNotesList.size());
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataNotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvJudul)
        TextView tvJudul;
        @BindView(R.id.tvIsi)
        TextView tvIsi;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
