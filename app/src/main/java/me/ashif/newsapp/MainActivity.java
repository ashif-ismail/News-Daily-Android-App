package me.ashif.newsapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.ashif.newsapp.EnglishNews.PagerAdapter;

import me.ashif.newsapp.app.AppController;
import me.ashif.newsapp.model.news;
import me.ashif.newsapp.AboutDialog;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public InterstitialAd interstitial;
    public static String title;
    public static String image;
    public static String feedUrl;

    private String[] newsURL = {
            //top news
            "https://api.import.io/store/connector/486aa7ca-ea26-40a5-b374-5e0ac80c0630/_query?input=webpage/url:http%3A%2F%2Fwww.ndtv.com%2Flatest&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",

            //kerala
            "https://api.import.io/store/connector/fd33d5e7-6355-4d96-81ab-81c1b4a71190/_query?input=webpage/url:http%3A%2F%2Fwww.madhyamam.com%2Fen%2Fkerala&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //karnataka
            "https://api.import.io/store/connector/6811511c-0744-44d8-ad0b-65df90ba5336/_query?input=webpage/url:http%3A%2F%2Fwww.ndtv.com%2Fkarnataka-news&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //tamil nadu
            "https://api.import.io/store/connector/33beab0c-c6fa-46a6-92af-c7777f1d139a/_query?input=webpage/url:http%3A%2F%2Fwww.ndtv.com%2Ftamil-nadu-news&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //world
            "https://api.import.io/store/connector/32985e10-fd30-4ce1-bfbb-0dc8202c6a6c/_query?input=webpage/url:http%3A%2F%2Fwww.ndtv.com%2Fworld-news&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //biz
            "https://api.import.io/store/connector/a2575923-a6a8-44cb-a156-6480f78484f2/_query?input=webpage/url:http%3A%2F%2Feconomictimes.indiatimes.com%2Fmarkets&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //edu
            "https://api.import.io/store/connector/98c6f0a5-a88d-4f4d-baa3-147562b37df3/_query?input=webpage/url:http%3A%2F%2Ftimesofindia.indiatimes.com%2Fhome%2Feducation%2Fnews&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            // sports
            "https://api.import.io/store/connector/298053ca-5b57-4c2f-af14-7cee5a6855f9/_query?input=webpage/url:http%3A%2F%2Findianexpress.com%2Fsports%2F&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
            //science
            "https://api.import.io/store/connector/a8465967-e32c-44e5-810f-5449c98fbc76/_query?input=webpage/url:http%3A%2F%2Ftech.firstpost.com&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
//AUTO
            "https://api.import.io/store/connector/e0def9fb-a701-4b3f-9c5d-ffedc8978ace/_query?input=webpage/url:http%3A%2F%2Fwww.zigwheels.com%2Fnews&&_apikey=a54dc57a4ea543e88dde7b2b095d20b76472f66d20c198b521901f77185f5803775ccf5a5bafdbef7f0cdc7deba134eae0f759d27548ca9d28059dd122d69b9d2f626b36813963e0d7ad59edca6269f5",
//india
            "https://api.import.io/store/connector/bbb6e8a3-13d5-4544-8dd1-7398427c44dc/_query?input=webpage/url:http%3A%2F%2Fwww.hindustantimes.com%2Findia%2F&&_apikey=8d118d2054b047dabdc18a10443f20ce6bf3577e42cd832c10c3836c8691da4ab111a602887e1fb68047a34843887b72ef1362783b539cf9222aa1ef3a96ac25e71b680f83b55f72c367c76feadb81d2"
    };
private String[] jsonObj = {


};
    private static String jsonArray = "results";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static ListView listView;
    private static CustomListAdapter listAdapter;
    private static List<news> newsItems;
    private static ProgressDialog pDialog;
    private static NetworkImageView imageView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        interstitial = new InterstitialAd(MainActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6509378199111522/6262948893");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

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

        listView = (ListView) findViewById(R.id.list);
        newsItems = new ArrayList<news>();
        listAdapter = new CustomListAdapter(this, newsItems);
        listView.setAdapter(listAdapter);
        if (!ConnectionListetener.checkNetworkConnection(this)) {
            showAlert(this, "No Data Connection", "News Daily Requires an Active Internet Connection");
        }
        else
            makeJSONRequest(newsURL[0], jsonArray, "nstoryheader_link/_text", "newimg_image", "newimg_link");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("TOP NEWS"));
        tabLayout.addTab(tabLayout.newTab().setText("INDIA"));
        tabLayout.addTab(tabLayout.newTab().setText("WORLD"));
        tabLayout.addTab(tabLayout.newTab().setText("BUSINESS"));
        tabLayout.addTab(tabLayout.newTab().setText("EDUCATION"));
        tabLayout.addTab(tabLayout.newTab().setText("KERALA"));
        tabLayout.addTab(tabLayout.newTab().setText("KARNATAKA"));
        tabLayout.addTab(tabLayout.newTab().setText("TAMIL NADU"));
        tabLayout.addTab(tabLayout.newTab().setText("SPORTS"));
        tabLayout.addTab(tabLayout.newTab().setText("TECH & GADGETS"));
        tabLayout.addTab(tabLayout.newTab().setText("AUTO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
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
                        makeJSONRequest(newsURL[0],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 1:
                        makeJSONRequest(newsURL[10],jsonArray,"list_link/_text","listimg_image","list_link");
                        break;
                    case 2:
                        makeJSONRequest(newsURL[4],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 3:
                        makeJSONRequest(newsURL[5],jsonArray,"newsheading_link/_text","flt_image","newsheading_link");
                        break;
                    case 4:
                        makeJSONRequest(newsURL[6],jsonArray,"link_2/_text","image","link_2");
                        break;
                    case 5:
                        makeJSONRequest(newsURL[1],jsonArray,"header_link/_text","img_image","header_link");
                        break;
                    case 6:
                        makeJSONRequest(newsURL[2],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 7:
                        makeJSONRequest(newsURL[3],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 8:
                        makeJSONRequest(newsURL[7],jsonArray,"caption_link/_text","stoimg_image","caption_link");
                        break;
                    case 9:
                        makeJSONRequest(newsURL[8],jsonArray,"mt7_link/_title","imgbx_image","mt7_link");
                        break;
                    case 10:
                        makeJSONRequest(newsURL[9],jsonArray,"fontpts_link/_text","tableftcontent_image","tableftcontent_link");
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
                        makeJSONRequest(newsURL[0],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 1:
                        makeJSONRequest(newsURL[10],jsonArray,"list_link/_text","listimg_image","list_link");
                        break;
                    case 2:
                        makeJSONRequest(newsURL[4],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 3:
                        makeJSONRequest(newsURL[5],jsonArray,"newsheading_link/_text","flt_image","newsheading_link");
                        break;
                    case 4:
                        makeJSONRequest(newsURL[6],jsonArray,"link_2/_text","image","link_2");
                        break;
                    case 5:
                        makeJSONRequest(newsURL[1],jsonArray,"header_link/_text","img_image","header_link");
                        break;
                    case 6:
                        makeJSONRequest(newsURL[2],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 7:
                        makeJSONRequest(newsURL[3],jsonArray,"nstoryheader_link/_text","newimg_image","newimg_link");
                        break;
                    case 8:
                        makeJSONRequest(newsURL[7],jsonArray,"caption_link/_text","stoimg_image","caption_link");
                        break;
                    case 9:
                        makeJSONRequest(newsURL[8],jsonArray,"mt7_link/_title","imgbx_image","mt7_link");
                        break;
                    case 10:
                        makeJSONRequest(newsURL[9],jsonArray,"fontpts_link/_text","tableftcontent_image","tableftcontent_link");
                        break;
                }
            }
        });
    }

    private void showAlert(MainActivity mainActivity, String s, String s1) {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(this);
        alert.setTitle(s);
        alert.setMessage(s1);
        alert.show();
    }

    public void makeJSONRequest(String url,String jsonArray, final String titleObj, final String imgObj,final String linkObj) {
        listAdapter.notifyDataSetChanged();
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
                    parseJsonFeed(response,titleObj,imgObj,linkObj);
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
                image = feedObj.isNull(newsImage) ? null : feedObj.getString(newsImage);
                item.setThumbnailUrl(image);

                // url might be null sometimes
                feedUrl = feedObj.isNull(newsLink) ? null : feedObj
                        .getString(newsLink);
                 item.seturl(feedUrl);
               // String feeddate = feedObj.isNull(newsTime) ? null: feedObj.getString(newsTime);
                //item.seturl(feeddate);

                passInfo(image, feedUrl,title);
                newsItems.add(item);

            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void passInfo(final String newsImage, final String newsLink, final String newsTitdle) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newsIntent = new Intent(getApplicationContext(),NewsInDetailActivity.class);
                TextView urlText = (TextView) view.findViewById(R.id.url);
                feedUrl = urlText.getText().toString();
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_mal) {
            Intent mal = new Intent(MainActivity.this, MalayalamNewsActivity.class);
            startActivity(mal);
        }
        if (id == R.id.nav_hin) {
            Intent hin = new Intent(MainActivity.this, HindiNewsActivity.class);
            startActivity(hin);
        }
        if (id == R.id.nav_kan) {
            Intent kan = new Intent(MainActivity.this, KannadaNews.class);
            startActivity(kan);
        }
        if(id == R.id.about) {
            AboutDialog.show(this);
        }
        if(id == R.id.rate){
            Intent rateIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=me.ashif.newsapp"));
            startActivity(rateIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
