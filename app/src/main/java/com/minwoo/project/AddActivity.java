package com.minwoo.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class AddActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<ListItem> items = new ArrayList<>();

    String[] COUNTRY_STR = {"대한민국", "유럽연합", "미국","체코", "일본","홍콩", "중국","캐나다", "스위스", "칠레", "쿠바", "덴마크", "도미니카", "이집트", "크로아티아", "인도",
    "아이슬란드", "쿠웨이트", "레바논", "헝가리", "몽골", "마카오", "뉴질랜드", "러시아", "싱가포르", "멕시코" , "노르웨이", "루마니아", "스웨덴", "남아프리카공화국", "오스트레일리아", "폴란드", "터키",
    "탄자니아", "태국", "케냐", "캄보디아", "베트남"};
    String[] UNIT = {"원", "유로", "달러","코루나", "엔", "홍콩달러", "위안", "달러", "프랑", "페소", "페소", "크로네", "페소", "파운드", "쿠나", "루피", "크로나", "디나르", "파운드", "포린트",
    "투그릭", "파타카", "달러", "루블", "달러", "페소", "크로네", "레우", "크로나", "랜드", "달러", "즈워티", "리라", "실링", "밧", "실링", "리엘", "동"};
    int[] COUNTRY_ICON = {R.drawable.korea, R.drawable.eu, R.drawable.america, R.drawable.czech, R.drawable.japan,R.drawable.hongkong,R.drawable.china, R.drawable.canada, R.drawable.swiss, R.drawable.chile,
    R.drawable.cuba, R.drawable.denmark, R.drawable.dominica,R.drawable.croatia, R.drawable.egypt, R.drawable.india, R.drawable.island, R.drawable.kuwait, R.drawable.lebanon, R.drawable.hungary, R.drawable.mogolia,
    R.drawable.macao, R.drawable.newzealand, R.drawable.russia, R.drawable.singapore, R.drawable.mexico, R.drawable.norway, R.drawable.romania, R.drawable.sweden, R.drawable.southafrica, R.drawable.australia,
    R.drawable.poland, R.drawable.turkey, R.drawable.turkey, R.drawable.thailand, R.drawable.kenya, R.drawable.cambodia, R.drawable.vietnam};
    String[] EXCHANGE_NAME = {"KRW", "EUR", "USD", "CZK", "JPY", "HKD", "CNY", "CAD", "CHF", "CLP", "CUP", "DKK", "DOP","EGP", "HRK", "INR","ISK", "KWD", "LBP","HUF", "MNT","MOP","NZD","RUB", "SGD",
    "MXN", "NOK", "RON", "SEK", "ZAR", "AUD", "PLN", "TRY", "TZS", "THB", "KES", "KHR", "VND"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("화폐 선택");

        for(int i =0; i< COUNTRY_STR.length;i++){
            items.add(new ListItem(COUNTRY_ICON[i], COUNTRY_STR[i]));
        }

        recyclerViewAdapter = new RecyclerViewAdapter(items);
        recyclerView = findViewById(R.id.add_country_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);




        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                RecyclerViewAdapter mRecyclerViewAdapter = (RecyclerViewAdapter) recyclerView.getAdapter();
                ListItem item = mRecyclerViewAdapter.getListItem(position);
                int getPosition = 0;
                for(int i = 0; i < items.size() ; i++){
                    if(item == items.get(i))
                        getPosition = i;
                }
                Intent intent = new Intent();
                intent.putExtra("str", EXCHANGE_NAME[getPosition]);

                intent.putExtra("num", COUNTRY_ICON[getPosition]);
                intent.putExtra("unit", UNIT[getPosition]);
                setResult(10,intent);
                finish();
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        //searchView 길이 곽차게
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("이름으로 검색합니다.");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerViewAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(true);
        return true;
    }

}
