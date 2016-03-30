package  me.ashif.newsapp;


import me.ashif.newsapp.app.AppController;
import me.ashif.newsapp.model.news;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter  {
    private Activity activity;
    private LayoutInflater inflater;
    private List<news> newsItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<news> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView title = (TextView) convertView.findViewById(R.id.newstitle);
        TextView url = (TextView) convertView.findViewById(R.id.url);
        NetworkImageView thumbpic= (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        news item = newsItems.get(position);

        title.setText(item.getTitle());

        // Checking for null feed url

            url.setText(item.getUrl());



        thumbpic.setImageUrl(item.getThumbnailUrl(), imageLoader);


        return convertView;

    }

}