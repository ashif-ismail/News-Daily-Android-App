package me.ashif.newsapp.EnglishNews;

/**
 * Created by almukthar on 27/12/15.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;


public class PagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LatestNews tab0 = new LatestNews();
                return tab0;
            case 1:
                NationalNews tab1 = new NationalNews();
                return tab1;
            case 2:
                InternationalNews tab2 = new InternationalNews();
                return tab2;
            case 3:
                BusinessNews tab3 = new BusinessNews();
                return tab3;
            case 4:
                EducationNews tab4 = new EducationNews();
                return tab4;
            case 5:
                CricketNews tab5 = new CricketNews();
                return tab5;
            case 6:
                SportsNews tab6 = new SportsNews();
                return tab6;
            case 7:
                SciTechNews tab7 = new SciTechNews();
                return tab7;
            case 8:
                Opinion tab8 = new Opinion();
                return tab8;
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}