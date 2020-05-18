package com.example.diagnalprogrammingtest.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.diagnalprogrammingtest.R;
import com.example.diagnalprogrammingtest.model.Content;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class DataUtils {

    private static String loadJSONFromAsset(Context mContext, String mFileName) {
        String json;

        try {
            InputStream is = mContext.getAssets().open(mFileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Content> fetchContent(Context mContext, String mPageName) {
        String fetchContent = loadJSONFromAsset(mContext, mPageName);
        ArrayList<Content> listOfContent = new ArrayList<>();
        try {
            assert fetchContent != null;
            JSONObject jObjPage;
            JSONObject jsonObject = new JSONObject(fetchContent);
            if (jsonObject.has("page")){
                 jObjPage = jsonObject.getJSONObject("page");

                 if (jObjPage.has("content-items")){

                     JSONObject objectContent=jObjPage.getJSONObject("content-items");

                     JSONArray jsonArray = objectContent.getJSONArray("content");
                     for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jobjCountry = jsonArray.getJSONObject(i);
                         Content countryModel = new Content(jobjCountry.optString("name"),
                                 jobjCountry.optString("poster-image"),
                                 jObjPage.optString("total-content-items"),
                                 jObjPage.optString("page-num"),
                                 jObjPage.optString("page-size"),
                                 jObjPage.optString("title"));
                         listOfContent.add(countryModel);
                     }
                 }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfContent;
    }


    public static HashMap<String,Integer> getImage(){

        HashMap<String,Integer> list = new HashMap<>();
        list.put("poster1",R.drawable.poster1);
        list.put("poster2",R.drawable.poster2);
        list.put("poster3",R.drawable.poster3);
        list.put("poster4",R.drawable.poster4);
        list.put("poster5",R.drawable.poster5);
        list.put("poster6",R.drawable.poster6);
        list.put("poster7",R.drawable.poster7);
        list.put("poster8",R.drawable.poster8);
        list.put("poster9",R.drawable.poster9);
        return list;

    }

    public static void hideKeyBoard(Activity activity, View view) {

        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
