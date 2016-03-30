package me.ashif.newsapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.ashif.newsapp.EnglishNews.PagerAdapter;
import me.ashif.newsapp.app.AppController;
import me.ashif.newsapp.model.news;

public class MalayalamNewsActivity extends AppCompatActivity {
    public InterstitialAd interstitial;
    public static String title;
    TextView titleText;
    public static String feedUrl;
    public static String image;

    private String jsonArray = "results";
    private String[] mlnewsURL = {
            //kerela
"https://api.import.io/store/connector/dc8dc525-fd33-44fc-a528-1636235b5fff/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fnews%2Fkerala&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
    //india
      "https://api.import.io/store/connector/5280cc30-b476-4b6d-86b2-d4e5023934fe/_query?input=webpage/url:http%3A%2F%2Fwww.reporterlive.com%2Fnational&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //world
       "https://api.import.io/store/connector/e8e77559-1ff2-4071-95f1-a17c25b8adc7/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fnews%2Fworld&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //uae
       "https://api.import.io/store/connector/e855c997-ca3b-4576-b003-ca5f2a6493fb/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fnri%2Fgulf%2Fuae&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //saudi
            "https://api.import.io/store/connector/e855c997-ca3b-4576-b003-ca5f2a6493fb/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fnri%2Fgulf%2Fsaudi-arabia&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //sports crt
"https://api.import.io/store/connector/8f8a3290-28f4-49a5-9468-7072ac459d1d/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fsports&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //education
            "https://api.import.io/store/connector/e43f3fb7-264a-4d48-8499-66b78d43f17b/_query?input=webpage/url:http%3A%2F%2Fmediaonetv.in%2Fkerala/education%2F&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //career news
"https://api.import.io/store/connector/d2ee98de-5672-4f0a-88a1-ce8b5d56a71c/_query?input=webpage/url:http%3A%2F%2Fwww.manoramanews.com%2Fnews%2Fgulf.html&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //tech
          "https://api.import.io/store/connector/1e809f89-4074-435d-ba75-c9c4500b60da/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Ftechnology&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
//politics
            "https://api.import.io/store/connector/cd307d84-88a8-42ea-8649-ce6013165f85/_query?input=webpage/url:http%3A%2F%2Fwww.madhyamam.com%2Ftaxonomy%2Fterm%2F61&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
    //health
            "https://api.import.io/store/connector/b5fc15b4-5eba-4ab9-8808-88763c63ec45/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fhealth&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //gadgets
            "https://api.import.io/store/connector/c2c94dd0-6947-4bfa-bac0-53b5a88252cc/_query?input=webpage/url:http%3A%2F%2Fwww.madhyamam.com%2Ftechnology&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //biz
            "https://api.import.io/store/connector/051a360b-2265-4140-950f-348f04b17940/_query?input=webpage/url:http%3A%2F%2Fwww.mathrubhumi.com%2Fmoney/business-news&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2"
    };
    private static final String TAG = MainActivity.class.getSimpleName();
    private static ListView listView;
    private static CustomListAdapter listAdapter;
    private static List<news> newsItems;
    private static ProgressDialog pDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static ImageLoader imageLoader;
    private static NetworkImageView networkImageView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malayalam_news);

        interstitial = new InterstitialAd(MalayalamNewsActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6509378199111522/9076814497");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView2);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("ED80C97C07AA094CE2FD02BB5CAD7DEA")
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });

        listView = (ListView) findViewById(R.id.list1);
        newsItems = new ArrayList<news>();
        listAdapter = new CustomListAdapter(this, newsItems);
        listView.setAdapter(listAdapter);



        makeJSONRequest(mlnewsURL[0], "link/_text", "img_image", "link");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_ml);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.kerala_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.india_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.world_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.uae_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.saudi_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sports_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.education_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.gulf_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tech_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.politics_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.health_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.gadgets_ml)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.business_ml)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_ml);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //  viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        makeJSONRequest(mlnewsURL[0],"link/_text","img_image","link");
                        break;
                    case 1:
                        makeJSONRequest(mlnewsURL[1],"cattitle_link/_title","image","link");
                        break;
                    case 2:
                        makeJSONRequest(mlnewsURL[2],"commontext_value","img_image","link");
                        break;
                    case 3:
                        makeJSONRequest(mlnewsURL[3],"link/_text","img_image","link");
                        break;
                    case 4:
                        makeJSONRequest(mlnewsURL[4],"link/_text","img_image","link");
                        break;
                    case 5:
                        makeJSONRequest(mlnewsURL[5],"commontext_value","img_image","link");
                        break;
                    case 6:
                        makeJSONRequest(mlnewsURL[6],"link/_text","image","link");
                        break;
                    case 7:
                        makeJSONRequest(mlnewsURL[7],"malayalam_link/_text","videoicon_image","malayalam_link");
                        break;
                    case 8:
                        makeJSONRequest(mlnewsURL[8],"commontext_link/_text","col_image","commontext_link");
                        break;
                    case 9:
                        makeJSONRequest(mlnewsURL[9],"hd_link/_text","img_image","hd_link");
                        break;
                    case 10:
                        makeJSONRequest(mlnewsURL[10],"commontext_link/_text","image","link");
                        break;
                    case 11:
                        makeJSONRequest(mlnewsURL[11],"hd_link/_text","img_image","hd_link");
                        break;
                    case 12:
                        makeJSONRequest(mlnewsURL[12],"link/_text","img_image","link");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        makeJSONRequest(mlnewsURL[0],"link/_text","img_image","link");
                        break;
                    case 1:
                        makeJSONRequest(mlnewsURL[1],"link/_text","img_image","link");
                        break;
                    case 2:
                        makeJSONRequest(mlnewsURL[2],"commontext_value","img_image","link");
                        break;
                    case 3:
                        makeJSONRequest(mlnewsURL[3],"link/_text","img_image","link");
                        break;
                    case 4:
                        makeJSONRequest(mlnewsURL[4],"link/_text","img_image","link");
                        break;
                    case 5:
                        makeJSONRequest(mlnewsURL[5],"commontext_value","img_image","link");
                        break;
                    case 6:
                        makeJSONRequest(mlnewsURL[6],"link/_text","image","link");
                        break;
                    case 7:
                        makeJSONRequest(mlnewsURL[7],"malayalam_link/_text","videoicon_image","malayalam_link");
                        break;
                    case 8:
                        makeJSONRequest(mlnewsURL[8],"commontext_link/_text","col_image","commontext_link");
                        break;
                    case 9:
                        makeJSONRequest(mlnewsURL[9],"hd_link/_text","img_image","hd_link");
                        break;
                    case 10:
                        makeJSONRequest(mlnewsURL[10],"commontext_link/_text","image","link");
                        break;
                    case 11:
                        makeJSONRequest(mlnewsURL[11],"hd_link/_text","img_image","hd_link");
                        break;
                    case 12:
                        makeJSONRequest(mlnewsURL[12],"link/_text","img_image","link");
                        break;
                }
            }
        });
    }

    private void makeJSONRequest(String url, final String titleObj, final String imageObj, final String linkObj) {
        newsItems.clear();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Reading News Contents");
        pDialog.show();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response,titleObj,imageObj,linkObj);
                    hidePDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
    private void parseJsonFeed(JSONObject response,String newsTitle,String newsImage,String newsLink) {
        try {
            JSONArray feedArray = response.getJSONArray(jsonArray);

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                news item = new news();
                title = feedObj.getString(newsTitle);
                item.setTitle(title);

                // Image might be null sometimes
                String image = feedObj.isNull(newsImage) ? null : feedObj.getString(newsImage);
                item.setThumbnailUrl(image);

                // url might be null sometimes
                String feedUrl = feedObj.isNull(newsLink) ? null : feedObj
                        .getString(newsLink);
                 item.seturl(feedUrl);
                //String feeddate = feedObj.getString(newsTime);
                //item.seturl(feeddate);
                passInfo(image,feedUrl,title);
                newsItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void passInfo(final String newsImage, final String newsUrl, final String newsTitle) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newsIntent = new Intent(getApplicationContext(),NewsInDetailActivity.class);
                TextView urlText = (TextView) view.findViewById(R.id.url);
                feedUrl = urlText.getText().toString();
                newsIntent.putExtra("url",feedUrl);
                startActivity(newsIntent);
            }
        });
    }

    private static void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}

