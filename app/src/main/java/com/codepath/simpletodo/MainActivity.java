package com.codepath.simpletodo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private static final String ITEMS_TEXT_FILE = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems=(ListView)findViewById(R.id.lvItems);
        items=new ArrayList<>();

        readItems();

        itemsAdapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);

        setupViewListener();
    }

    private void setupViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View item, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra(EditItemActivity.EDIT_ITEM_POSITION, pos);
                i.putExtra(EditItemActivity.EDIT_ITEM_VALUE,  ((TextView) item).getText());

                startActivityForResult(i, EditItemActivity.EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EditItemActivity.EDIT_ITEM_REQUEST_CODE) {
            items.set(data.getIntExtra(EditItemActivity.EDIT_ITEM_POSITION,-1),data.getStringExtra(EditItemActivity.EDIT_ITEM_VALUE));
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,ITEMS_TEXT_FILE);
        try{
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,ITEMS_TEXT_FILE);
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onAddItem (View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
}
