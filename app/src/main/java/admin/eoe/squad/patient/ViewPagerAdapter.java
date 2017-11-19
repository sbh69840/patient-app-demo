package admin.eoe.squad.patient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 26/06/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment =null;
        if(position==0){
            fragment = new TreatmentFragment();
        }else if(position == 1){
            fragment = new BookFragment();
        }else if(position == 2){
            fragment = new HomeMain();
        }else if(position == 3){
            fragment = new TrackingFragment();
        }else if(position == 4){
            fragment = new Fragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }


}
