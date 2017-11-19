package admin.eoe.squad.patient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 26/06/2017.
 */

public class DocViewAdapter extends FragmentPagerAdapter {
    public DocViewAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        if(position==0){
            fragment = new DocsTreatFrag();
        }else if(position == 1){
            fragment = new DocCalendar();
        }else if(position == 2){
            fragment = new DocContactFrag();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Treatment";
            case 1:
                return "Reports";
            case 2:
                return "Contact";
        }
        return null;
    }
}
