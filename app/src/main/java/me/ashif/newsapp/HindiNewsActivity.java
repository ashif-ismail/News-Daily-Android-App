package me.ashif.newsapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class HindiNewsActivity extends AppCompatActivity {
 public InterstitialAd interstitial;
    private static String jsonAray = "results";
    private String[] hnnewsURL = {
            //zara hatke
            "https://api.import.io/store/connector/62f429ed-bfb0-4987-b46f-238f984d9d17/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fzara-hatke&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //india
           "https://api.import.io/store/connector/2300c04e-a776-49f9-a183-09a401bba389/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Findia&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //world
            "https://api.import.io/store/connector/e0d2fd95-b32e-4cb0-942f-6cdd3459c263/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fworld&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
           //biz
            "https://api.import.io/store/connector/e0d2fd95-b32e-4cb0-942f-6cdd3459c263/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fbusiness&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //education
            "https://api.import.io/store/connector/e9ed5845-a00c-47f8-bdd3-13eb09cb1254/_query?input=webpage/url:http%3A%2F%2Fwww.jagran.com%2Fjosh%2Fshiksha-news-hindi.html&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //sports
            "https://api.import.io/store/connector/e0d2fd95-b32e-4cb0-942f-6cdd3459c263/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fsports&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //auto
            "https://api.import.io/store/connector/e0d2fd95-b32e-4cb0-942f-6cdd3459c263/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fauto&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //cricket
            "https://api.import.io/store/connector/e0d2fd95-b32e-4cb0-942f-6cdd3459c263/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Fcricket&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //tech
            "https://api.import.io/store/connector/001e0ac4-dd68-4e75-a3b6-38a0dccff9c0/_query?input=webpage/url:http%3A%2F%2Fwww.jagran.com%2Ftechnology-hindi.html&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //food and fitness
            "https://api.import.io/store/connector/d731766e-1bdf-4a29-b9b1-7e59c15f62d6/_query?input=webpage/url:http%3A%2F%2Fkhabar.ndtv.com%2Fnews%2Ffood&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
             //new delhi
            "https://api.import.io/store/connector/39606ea1-fe42-4c24-916c-501906adf84b/_query?input=webpage/url:http%3A%2F%2Fwww.jagran.com%2Flocal%2Fdelhi_new-delhi-city-news-hindi.html&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5"
    };




    private static final String TAG = MainActivity.class.getSimpleName();
    private static ListView listView;
    private static CustomListAdapter listAdapter;
    private static List<news> newsItems;
    private static ProgressDialog pDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_news);
        interstitial = new InterstitialAd(HindiNewsActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6509378199111522/6262948893");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView3);

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

        listView = (ListView) findViewById(R.id.list2);
        newsItems = new ArrayList<news>();
        listAdapter = new CustomListAdapter(this, newsItems);
        listView.setAdapter(listAdapter);


        makeJSONRequest(hnnewsURL[0],"fl_link/_text","fl_link","news_image");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_hn);
        tabLayout.addTab(tabLayout.newTab().setText("ज़रा हटके"));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.india_hn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.world_hn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.biz_hn)));
        tabLayout.addTab(tabLayout.newTab().setText("शिक्षा"));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sports_hn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.auto_hn)));
        tabLayout.addTab(tabLayout.newTab().setText("क्रिकेट"));
        tabLayout.addTab(tabLayout.newTab().setText("टेक"));
        tabLayout.addTab(tabLayout.newTab().setText("फुड आंड फिटनेस"));
        tabLayout.addTab(tabLayout.newTab().setText("नई दिल्ली"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_hn);
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
                        makeJSONRequest(hnnewsURL[0],"fl_link/_text","fl_link","news_image");
                        break;
                    case 1:
                        makeJSONRequest(hnnewsURL[1],"fl_link/_text","news_link","news_image");
                        break;
                    case 2:
                        makeJSONRequest(hnnewsURL[2],"fl_link/_text","news_link","news_image");
                        break;
                    case 3:
                        makeJSONRequest(hnnewsURL[3],"fl_link/_text","news_link","news_image");
                        break;
                    case 4:
                        makeJSONRequest(hnnewsURL[4],"link_1/_text","link_3","image");
                        break;
                    case 5:
                        makeJSONRequest(hnnewsURL[5],"fl_link/_text","news_link","news_image");
                        break;
                    case 6:
                        makeJSONRequest(hnnewsURL[6],"fl_link/_text","news_link","news_image");
                        break;
                    case 7:
                        makeJSONRequest(hnnewsURL[7],"fl_link/_text","news_link","news_image");
                        break;
                    case 8:
                        makeJSONRequest(hnnewsURL[8],"link_1/_text","link_1","image");
                        break;
                    case 9:
                        makeJSONRequest(hnnewsURL[9],"fl_link/_text","news_link","news_image");
                        break;
                    case 10:
                        makeJSONRequest(hnnewsURL[10],"link_1/_text","link_1","image");
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
                        makeJSONRequest(hnnewsURL[0],"fl_link/_text","fl_link","news_image");
                        break;
                    case 1:
                        makeJSONRequest(hnnewsURL[1],"fl_link/_text","news_link","news_image");
                        break;
                    case 2:
                        makeJSONRequest(hnnewsURL[2],"fl_link/_text","news_link","news_image");
                        break;
                    case 3:
                        makeJSONRequest(hnnewsURL[3],"fl_link/_text","news_link","news_image");
                        break;
                    case 4:
                        makeJSONRequest(hnnewsURL[4],"link_1/_text","link_3","image");
                        break;
                    case 5:
                        makeJSONRequest(hnnewsURL[5],"fl_link/_text","news_link","news_image");
                        break;
                    case 6:
                        makeJSONRequest(hnnewsURL[6],"fl_link/_text","news_link","news_image");
                        break;
                    case 7:
                        makeJSONRequest(hnnewsURL[7],"fl_link/_text","news_link","news_image");
                        break;
                    case 8:
                        makeJSONRequest(hnnewsURL[8],"link_1/_text","link_1","image");
                        break;
                    case 9:
                        makeJSONRequest(hnnewsURL[9],"fl_link/_text","news_link","news_image");
                        break;
                    case 10:
                        makeJSONRequest(hnnewsURL[10],"link_1/_text","link_1","image");
                        break;
                }
            }
        });
    }
    public void makeJSONRequest(String url, final String titleObj, final String linkObj, final String imgObj) {
        newsItems.clear();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Reading News Contents" );
        pDialog.show();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response,titleObj,linkObj,imgObj);
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
    private void parseJsonFeed(JSONObject response,String newsTitle,String newsLink,String newsImage) {
        try {
            JSONArray feedArray = response.getJSONArray(jsonAray);

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                news item = new news();
                String title = feedObj.getString(newsTitle);
                item.setTitle(title);

                // Image might be null sometimes
                String image = feedObj.isNull(newsImage) ? null : feedObj.getString(newsImage);
                item.setThumbnailUrl(image);

                // url might be null sometimes
               String feedUrl = feedObj.isNull(newsLink) ? null : feedObj
                        .getString(newsLink);
                item.seturl(feedUrl);
                //description name = news_description;
                //String feeddate = feedObj.isNull("dateline_value") ? null :feedObj.getString("dateline_value");
                passInfo(image, feedUrl,title);
                newsItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void passInfo(String image, String feedUrl, String title) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newsIntent = new Intent(getApplicationContext(),NewsInDetailActivity.class);
                TextView urlText = (TextView) view.findViewById(R.id.url);
                String feedUrl = urlText.getText().toString();
                newsIntent.putExtra("url", feedUrl);
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
