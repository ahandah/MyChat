package com.ahah.lz.mychat.contact;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ahah.lz.mychat.R;
import com.ahah.lz.mychat.common.BaseFragment;
import com.ahah.lz.mychat.contact.friends.DiscussionFragment;
import com.ahah.lz.mychat.contact.friends.FriendsFragment;
import com.ahah.lz.mychat.contact.friends.GroupFragment;


import java.util.ArrayList;

public class ContactFragment extends BaseFragment {

    private ViewPager ctPager;
    private TabLayout tabLayout;
    private String[] title = {"好友" , "群" , "讨论组"};

    public ContactFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, null);

        return initContactFragment(view);
    }

    public View initContactFragment(View view){

        ctPager = (ViewPager) view.findViewById(R.id.ctPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//        设置tabLayout
        tabLayout.setupWithViewPager(ctPager);
//        初始化、添加Fragment
        FriendsFragment friendsFragment = new FriendsFragment();
//        DiscussionFragment friendsFragment = new DiscussionFragment();//测试ViewPager
        GroupFragment groupFragment = new GroupFragment();
        DiscussionFragment discussionFragment = new DiscussionFragment();

        final ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(friendsFragment);
        fragments.add(groupFragment);
        fragments.add(discussionFragment);

        ctPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager() , fragments , title));
        ctPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        ctPager.setCurrentItem(0);

        return view;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter{

        ArrayList<Fragment> viewLists;
        String[] title;

        public ViewPagerAdapter(FragmentManager fm , ArrayList<Fragment> list , String[] title){
            super(fm);
            viewLists = list;
            this.title = title;
            System.out.println("--------------"+viewLists.size());
        }

        @Override
        public int getCount() {                                                                 //获得size
            // TODO Auto-generated method stub
            return viewLists.size();
        }

        @Override
        public Fragment getItem(int position) {
            System.out.println(viewLists.get(position)+"--------------"+position);
            return viewLists.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }

        //        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
    }

}
