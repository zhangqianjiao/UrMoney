package com.example.pipid.urmoney;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CostItem> mCostItemList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new DatabaseHelper(this);
        mCostItemList = new ArrayList<>();
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initCostData();
        mAdapter = new CostListAdapter(this,mCostItemList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostItem costItem = new CostItem();
                        costItem.costTitle = title.getText().toString();
                        costItem.costMoney = money.getText().toString();
                        costItem.costDate = date.getYear() + "-" + (date.getMonth() + 1)+"-" +
                                date.getDayOfMonth();
                        mDatabaseHelper.insertCost(costItem);
                        mCostItemList.add(costItem);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
    }

    private void initCostData() {
        Cursor cursor = mDatabaseHelper.getAllCostDate();
        if(cursor != null){
            while (cursor.moveToNext()){
                CostItem costItem = new CostItem();
                costItem.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costItem.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costItem.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostItemList.add(costItem);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ClearLatest:
                mCostItemList.remove(mCostItemList.size() - 1);
                Toast.makeText(this, "Clear latest record", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_CurrencyConverter:
                Intent intent = new Intent(MainActivity.this, CurrentActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_Clearpage:
                mDatabaseHelper.removeAllCost();
                Toast.makeText(this, "Clear All Record", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
