package com.example.diagnalprogrammingtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.diagnalprogrammingtest.R;
import com.example.diagnalprogrammingtest.adapter.AdapterShow;
import com.example.diagnalprogrammingtest.databinding.ActivityMainBinding;
import com.example.diagnalprogrammingtest.model.Content;
import com.example.diagnalprogrammingtest.utils.DataUtils;
import com.example.diagnalprogrammingtest.utils.GridSpacingItemDecoration;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener{
    private Context mContext;
    private String PAGE_NAME="";
    private ArrayList<Content> listOfContent=new ArrayList<>();
    private AdapterShow adapterShow;
    private int current_page;
    private boolean fetch_data=true;
    private ActivityMainBinding activityMainBinding;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlack));

        mContext=this;
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);



        setAdapter();
        PAGE_NAME="contentlisting_page1";
        current_page=1;
        loadContent(PAGE_NAME);
        clickListener();

    }

    private void setAdapter() {
        adapterShow = new AdapterShow(this, listOfContent);
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            int spanCount = 3;
            int spacing = 30;
            activityMainBinding.rvShowType.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true
            ));
            activityMainBinding.rvShowType.setLayoutManager(new GridLayoutManager(this,3));


        } else {

            int spanCount = 7;
            int spacing =30;
            activityMainBinding.rvShowType.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
            activityMainBinding.rvShowType.setLayoutManager(new GridLayoutManager(this, 7));

        }
        activityMainBinding.rvShowType.setAdapter(adapterShow);
    }


    private void clickListener() {


        activityMainBinding.ivSearch.setOnClickListener(this);
        activityMainBinding.ivCloseSearch.setOnClickListener(this);
        activityMainBinding.ivBack.setOnClickListener(this);



        activityMainBinding.rvShowType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {


                if (fetch_data){
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    if (current_page == 1) {
                        PAGE_NAME = "contentlisting_page2";
                        loadContent(PAGE_NAME);
                        current_page = 2;
                    } else if (current_page == 2) {
                        PAGE_NAME = "contentlisting_page3";
                        loadContent(PAGE_NAME);
                        fetch_data=false;
                    }
                }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        activityMainBinding.etSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length()>=3){
                    filterList(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filterList(String searchString ) {
        activityMainBinding.rvShowType.setVisibility(View.VISIBLE);
        activityMainBinding.tvNoDataFound.setVisibility(View.GONE);

        if (listOfContent.size()>0){

                    ArrayList<Content> filteredList = new ArrayList<>();

                    for (Content item : listOfContent) {
                        if (item.getName().toLowerCase().contains(searchString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.size()>0){


                        activityMainBinding.rvShowType.setVisibility(View.VISIBLE);
                        activityMainBinding.tvNoDataFound.setVisibility(View.GONE);
                        adapterShow.filterList(filteredList,searchString);

                    }
                    else {
                        activityMainBinding.rvShowType.setVisibility(View.GONE);
                        activityMainBinding.tvNoDataFound.setVisibility(View.VISIBLE);
                    }
                }

    }
    private void loadContent(String page_name) {
        listOfContent.addAll(DataUtils.fetchContent(mContext, page_name));
        adapterShow.notifyDataSetChanged();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.iv_search:

                activityMainBinding.ivBack.setVisibility(View.GONE);
                activityMainBinding.showTitle.setVisibility(View.GONE);
                activityMainBinding.ivSearch.setVisibility(View.GONE);
                activityMainBinding.etSearchText.setVisibility(View.VISIBLE);
                activityMainBinding.etView.setVisibility(View.VISIBLE);
                activityMainBinding.ivCloseSearch.setVisibility(View.VISIBLE);

                break;

            case R.id.iv_close_search:

                activityMainBinding.rvShowType.setVisibility(View.VISIBLE);
                activityMainBinding.tvNoDataFound.setVisibility(View.GONE);

                DataUtils.hideKeyBoard(MainActivity.this,view);

                activityMainBinding.etSearchText.setText("");
                activityMainBinding.etSearchText.setHint("Search");
                activityMainBinding.ivBack.setVisibility(View.VISIBLE);
                activityMainBinding.showTitle.setVisibility(View.VISIBLE);
                activityMainBinding.ivSearch.setVisibility(View.VISIBLE);
                activityMainBinding.etSearchText.setVisibility(View.GONE);
                activityMainBinding.etView.setVisibility(View.GONE);
                activityMainBinding.ivCloseSearch.setVisibility(View.GONE);
                adapterShow.filterList(listOfContent,"");
                break;

            case R.id.iv_back:
                onBackPressed();

                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }


        }, 2000);

    }


}
