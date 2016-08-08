package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    //Static values for Activity
    public static final int EDIT_ITEM_REQUEST_CODE = 111;
    public static final String  EDIT_ITEM_POSITION = "POSITION";
    public static final String  EDIT_ITEM_VALUE = "VALUE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText editText =(EditText)findViewById(R.id.etEditItem);
        editText.setText(getIntent().getStringExtra(EDIT_ITEM_VALUE));
        editText.setSelection(editText.getText().length());

    }

    public void onEditItem (View v){
        EditText etNewItem = (EditText)findViewById(R.id.etEditItem);
        Intent data = new Intent();
        data.putExtra(EDIT_ITEM_VALUE,etNewItem.getText().toString());
        data.putExtra(EDIT_ITEM_POSITION,getIntent().getIntExtra(EDIT_ITEM_POSITION,-1));

        setResult(RESULT_OK,data);
        finish();

    }



}
