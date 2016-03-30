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

public class KannadaNews extends AppCompatActivity {

    public InterstitialAd interstitial;

    private String jsonArray = "results";
    private String[] knnewsURL = {
            //karanataka
            "https://api.import.io/store/connector/c6977a45-a541-488e-8151-ebe4adea73c4/_query?input=webpage/url:http%3A%2F%2Fwww.prajavani.net%2Fcategories%2F%25E0%25B2%25B0%25E0%25B2%25BE%25E0%25B2%259C%25E0%25B3%258D%25E0%25B2%25AF&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //india
            "https://api.import.io/store/connector/a387ce17-11e8-4d9a-ae99-188ebd2e4efe/_query?input=webpage/url:http%3A%2F%2Fvijaykarnataka.indiatimes.com%2Fnews%2Findia%2Farticlelist%2F11193745.cms&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //world
            "https://api.import.io/store/connector/eae5bc3b-f314-48e2-91c3-6774ce12603e/_query?input=webpage/url:http%3A%2F%2Fwww.prajavani.net%2Fcategories%2F%25E0%25B2%25B5%25E0%25B2%25BF%25E0%25B2%25A6%25E0%25B3%2587%25E0%25B2%25B6&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //biz
            "https://api.import.io/store/connector/265333c7-cebe-454d-af0c-a39b1a4f5569/_query?input=webpage/url:http%3A%2F%2Fwww.prajavani.net%2Fcategories%2F%25E0%25B2%25B5%25E0%25B2%25BE%25E0%25B2%25A3%25E0%25B2%25BF%25E0%25B2%259C%25E0%25B3%258D%25E0%25B2%25AF-0&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //sports
            "https://api.import.io/store/connector/491383c5-ff14-4a57-9f85-fa7abbb9aeb4/_query?input=webpage/url:http%3A%2F%2Fwww.prajavani.net%2Fcategories%2F%25E0%25B2%2595%25E0%25B3%258D%25E0%25B2%25B0%25E0%25B3%2580%25E0%25B2%25A1%25E0%25B3%2586-0&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //politics
            "https://api.import.io/store/connector/9f0ddf4a-b448-42d8-a0bb-a6116847f7dd/_query?input=webpage/url:http%3A%2F%2Fwww.kannadaprabha.com%2Fpolitics&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2",
            //district news
            "https://api.import.io/store/connector/56e25bb1-93d1-441a-b574-28b6203aafab/_query?input=webpage/url:http%3A%2F%2Fkannada.oneindia.com%2Fnews%2Fdistricts%2F&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //tech
            "https://api.import.io/store/connector/62e0c8a5-2b17-4389-a958-df0ab3c5be9d/_query?input=webpage/url:http%3A%2F%2Fkannada.gizbot.com%2F&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //auto news
            "https://api.import.io/store/connector/b62262dc-725e-4e7e-a41b-cce24e44fa49/_query?input=webpage/url:http%3A%2F%2Fkannada.drivespark.com%2Ffour-wheelers%2F&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //blore
            "https://api.import.io/store/connector/bb1cf92d-6e82-4447-8c22-fa103c138192/_query?input=webpage/url:http%3A%2F%2Fwww.udayavani.com%2Fkannada%2Fcategory%2Fbangalore-rural-news&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5"


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
        setContentView(R.layout.activity_kannada_news);
        interstitial = new InterstitialAd(KannadaNews.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6509378199111522/6262948893");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView4);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("ED80C97C07AA094CE2FD02BB5CAD7DEA")
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

        listView = (ListView) findViewById(R.id.list3);
        newsItems = new ArrayList<news>();
        listAdapter = new CustomListAdapter(this, newsItems);
        listView.setAdapter(listAdapter);


        makeJSONRequest(knnewsURL[0],"gridnewsitem_link/_text","gridnewsitem_link","largesize_image");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_kn);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.karnataka_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.india_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.world_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.bz_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sports_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.politics_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.district_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tech_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.auto_kn)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.blore_kn)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_kn);
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
                        makeJSONRequest(knnewsURL[0],"gridnewsitem_link/_text","gridnewsitem_link","largesize_image");
                        break;
                    case 1:
                        makeJSONRequest(knnewsURL[1],"link_2/_text","link_2","image");
                        break;
                    case 2:
                        makeJSONRequest(knnewsURL[2],"gridnewsitem_link/_text","gridnewsitem_link","largesize_image");
                        break;
                    case 3:
                        makeJSONRequest(knnewsURL[3],"gridnewsitem_link/_text","gridnewsitem_link","alignleft_image");
                        break;
                    case 4:
                        makeJSONRequest(knnewsURL[4],"gridnewsitem_link/_text","gridnewsitem_link","alignleft_image");
                        break;
                    case 5:
                        makeJSONRequest(knnewsURL[5],"link_2/_text","link_1","image");
                        break;
                    case 6:
                        makeJSONRequest(knnewsURL[6],"collection_link/_text","collection_link","articleimg_image");
                        break;
                    case 7:
                        makeJSONRequest(knnewsURL[7],"desc_link/_text","desc_link","leftimg_image");
                        break;
                    case 8:
                        makeJSONRequest(knnewsURL[8],"homeheading_link/_text","homeheading_link","home_image");
                        break;
                    case 9:
                        makeJSONRequest(knnewsURL[9],"fieldcontenttext_link/_text","fieldcontenttext_link","fieldcontent_image");
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
                        makeJSONRequest(knnewsURL[0],"gridnewsitem_link/_text","gridnewsitem_link","largesize_image");
                        break;
                    case 1:
                        makeJSONRequest(knnewsURL[1],"link_2/_text","link_2","image");
                        break;
                    case 2:
                        makeJSONRequest(knnewsURL[2],"gridnewsitem_link/_text","gridnewsitem_link","largesize_image");
                        break;
                    case 3:
                        makeJSONRequest(knnewsURL[3],"gridnewsitem_link/_text","gridnewsitem_link","alignleft_image");
                        break;
                    case 4:
                        makeJSONRequest(knnewsURL[4],"gridnewsitem_link/_text","gridnewsitem_link","alignleft_image");
                        break;
                    case 5:
                        makeJSONRequest(knnewsURL[5],"link_2/_text","link_1","image");
                        break;
                    case 6:
                        makeJSONRequest(knnewsURL[6],"collection_link/_text","collection_link","articleimg_image");
                        break;
                    case 7:
                        makeJSONRequest(knnewsURL[7],"desc_link/_text","desc_link","leftimg_image");
                        break;
                    case 8:
                        makeJSONRequest(knnewsURL[8],"homeheading_link/_text","homeheading_link","home_image");
                        break;
                    case 9:
                        makeJSONRequest(knnewsURL[9],"fieldcontenttext_link/_text","fieldcontenttext_link","fieldcontent_image");
                        break;
                }
            }
        });
    }
    public void makeJSONRequest(final String url, final String titleObj,final String urlObj, final String imgObj) {
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
                    parseJsonFeed(response,titleObj,urlObj,imgObj);
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
    private void parseJsonFeed(JSONObject response,String newsTitle,String newsUrl,String newsImage) {
        try {
            JSONArray feedArray = response.getJSONArray(jsonArray);

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                news item = new news();
                String title = feedObj.getString(newsTitle);
                item.setTitle(title);

                // Image might be null sometimes
                String image = feedObj.isNull(newsImage) ? null : feedObj.getString(newsImage);
                item.setThumbnailUrl(image);

                // url might be null sometimes
                String feedUrl = feedObj.isNull(newsUrl) ? null : feedObj
                        .getString(newsUrl);
                 item.seturl(feedUrl);
                //description name = news_description;
                //String feeddate = feedObj.isNull("dateline_value") ? null :feedObj.getString("dateline_value");
               // item.seturl(feeddate);
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
