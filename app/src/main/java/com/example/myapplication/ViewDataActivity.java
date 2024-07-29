package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewDataActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        myDb = new DatabaseHelper(this);

        listItem = new ArrayList<>();
        listView = findViewById(R.id.listView);

        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = listView.getItemAtPosition(i).toString();
                deleteData(text.split(" ")[0]);
            }
        });
    }

    private void viewData() {
        Cursor cursor = myDb.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listItem.add(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            listView.setAdapter(adapter);
        }
    }

    private void deleteData(String id) {
        Integer deletedRows = myDb.deleteData(id);
        if (deletedRows > 0)
            Toast.makeText(ViewDataActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ViewDataActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
        finish();
        startActivity(getIntent());
    }
}
