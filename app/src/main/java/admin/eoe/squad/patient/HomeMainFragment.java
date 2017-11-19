package admin.eoe.squad.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 27/06/2017.
 */

public class HomeMainFragment extends AppCompatActivity {
    private TabLayout tab;
    private ViewPagerAdapter vpa;
    private ViewPager vp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        vpa = new ViewPagerAdapter(getSupportFragmentManager());
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpa);
        tab = (TabLayout) findViewById(R.id.sliding_tabs);
        tab.setupWithViewPager(vp);
        vp.setCurrentItem(2);
        for(int i = 0; i<1;i++){
            tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticongray);
            tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicongray);
            tab.getTabAt(2).setIcon(R.mipmap.menuhomeicongray);
            tab.getTabAt(3).setIcon(R.mipmap.menutrackicongrayr2);
            tab.getTabAt(4).setIcon(R.mipmap.menusquadicongray);
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int index = tab.getSelectedTabPosition();
                switch(index){
                    case 0:
                        tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticonblue);
                        tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicongray);
                        tab.getTabAt(2).setIcon(R.mipmap.menuhomeicongray);
                        tab.getTabAt(3).setIcon(R.mipmap.menutrackicongrayr2);
                        tab.getTabAt(4).setIcon(R.mipmap.menusquadicongray);
                        break;
                    case 1:
                        tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticongray);
                        tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicon);
                        tab.getTabAt(2).setIcon(R.mipmap.menuhomeicongray);
                        tab.getTabAt(3).setIcon(R.mipmap.menutrackicongrayr2);
                        tab.getTabAt(4).setIcon(R.mipmap.menusquadicongray);
                        break;
                    case 2:
                        tab.getTabAt(2).setIcon(R.mipmap.menuhomeicon);
                        tab.getTabAt(3).setIcon(R.mipmap.menutrackicongrayr2);
                        tab.getTabAt(4).setIcon(R.mipmap.menusquadicongray);
                        tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticongray);
                        tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicongray);
                        break;
                    case 3:
                        tab.getTabAt(3).setIcon(R.mipmap.menutrackiconr2);
                        tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticongray);
                        tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicongray);
                        tab.getTabAt(2).setIcon(R.mipmap.menuhomeicongray);
                        tab.getTabAt(4).setIcon(R.mipmap.menusquadicongray);
                        break;
                    case 4:
                        tab.getTabAt(4).setIcon(R.mipmap.menusquadicon);
                        tab.getTabAt(0).setIcon(R.mipmap.menutreatmenticongray);
                        tab.getTabAt(1).setIcon(R.mipmap.menuresourcesicongray);
                        tab.getTabAt(2).setIcon(R.mipmap.menuhomeicongray);
                        tab.getTabAt(3).setIcon(R.mipmap.menutrackicongrayr2);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
}
