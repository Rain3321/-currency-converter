package com.minwoo.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editUpMoney;
    TextView textTime;
    EditText editDownMoney;
    TextView textUpCurrency, textDownCurrency;
    RecyclerView recyclerView;
    HistoryRecyclerViewAdapter historyRecyclerViewAdapter;

    private HistoryItem item = new HistoryItem(R.drawable.america, R.drawable.korea, "", "", "달러", "원");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("환율 계산");


        editUpMoney = findViewById(R.id.edit_money_1);
        editDownMoney = findViewById(R.id.edit_money_2);
        textUpCurrency = findViewById(R.id.symbol_1);
        textDownCurrency = findViewById(R.id.symbol_2);
        textTime = findViewById(R.id.time_text);

        recyclerView = findViewById(R.id.exchange_history);
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelperCallback callback = new ItemTouchHelperCallback(historyRecyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        recyclerView.setAdapter(historyRecyclerViewAdapter);

        editUpMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUpMoney.setText("");
            }
        });

        editUpMoney.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    exChange(v);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editUpMoney.getWindowToken(), 0);
                    setHistoryList();

                    return true;
                }
                return false;

            }
        });

        editDownMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDownMoney.setText("");
            }
        });

        editDownMoney.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    exChange(v);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDownMoney.getWindowToken(), 0);
                    setHistoryList();

                    return true;
                }
                return false;
            }
        });
    }

    public void setHistoryList() {
        historyRecyclerViewAdapter.addData(item);
        historyRecyclerViewAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
        //historyRecyclerViewAdapter.notifyDataSetChanged();

    }

    public void exChange(View v) {
        if(v.getId() == R.id.edit_money_1) {
            int wantToExchange = Integer.parseInt(editUpMoney.getText().toString());
            String upCountry = textUpCurrency.getText().toString();
            String downCountry = textDownCurrency.getText().toString();
            double finishExchange = 0;

            Log.d("country+getText", wantToExchange + " " + upCountry);

            try {
                String str = new Exchange_task().execute(upCountry, downCountry).get();
                finishExchange = new Exchange_task().jsonParsing(str);
                textTime.setText(new Exchange_task().jsonTime(str));
                Log.d("seo1", str);
                Log.d("seo2", finishExchange + "");

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            finishExchange = finishExchange * wantToExchange;
            editDownMoney.setText(String.format("%.2f", finishExchange));


            item.setMoney1(editUpMoney.getText().toString());
            item.setMoney2(String.format("%.2f", finishExchange));
        }
        else if(v.getId() == R.id.edit_money_2){
            int wantToExchange = Integer.parseInt(editDownMoney.getText().toString());
            String upCountry = textUpCurrency.getText().toString();
            String downCountry = textDownCurrency.getText().toString();
            double finishExchange = 0;

            Log.d("country+getText", wantToExchange + " " + upCountry);

            try {
                String str = new Exchange_task().execute(downCountry, upCountry).get();
                finishExchange = new Exchange_task().jsonParsing(str);
                textTime.setText(new Exchange_task().jsonTime(str));
                Log.d("seo1", str);
                Log.d("seo2", finishExchange + "");

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            finishExchange = finishExchange * wantToExchange;
            editUpMoney.setText(String.format("%.2f", finishExchange));


            item.setMoney2(editDownMoney.getText().toString());
            item.setMoney1(String.format("%.2f", finishExchange));
        }
    }


    public void onLayoutOnClick(View v) {
        if (v.getId() == R.id.upper_layout) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivityForResult(intent, 1);
        } else if (v.getId() == R.id.lower_layout) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {
            if (requestCode == 1) {
                if (!textDownCurrency.getText().toString().equals(data.getStringExtra("str"))) {
                    textUpCurrency.setText(data.getStringExtra("str"));
                    item.setIconId1(data.getIntExtra("num", 0));
                    item.setCurrency1(data.getStringExtra("unit"));
                }else {
                    Toast.makeText(getApplicationContext(), "같은나라로 환전할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }

                editUpMoney.setText("");
                editDownMoney.setText("");

            } else if (requestCode == 2) {
                if (!textUpCurrency.getText().toString().equals(data.getStringExtra("str"))) {
                    textDownCurrency.setText(data.getStringExtra("str"));
                    item.setIconId2(data.getIntExtra("num", 0));
                    item.setCurrency2(data.getStringExtra("unit"));

                }else{
                    Toast.makeText(getApplicationContext(), "같은나라로 환전할 수 없습니다.", Toast.LENGTH_SHORT).show();

                }

                editUpMoney.setText("");
                editDownMoney.setText("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_help){
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}


