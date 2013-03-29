package com.example.myprototype;

import java.util.ArrayList;

import com.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

public class MainActivity extends BaseActivity {
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;
	private static SlidingMenu mSlidingMenu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_tabs_pager);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);
        
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
                FragmentStackSupport.CountingFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
                LoaderCursorSupport.CursorLoaderListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
                LoaderCustomSupport.AppListFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
                LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        mSlidingMenu=getSlidingMenu();
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	private static void  pageChange(int arg0){
		switch (arg0) {
		case 0:
			mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		default:
			mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			break;
		}
	}
	
	public static class TabsAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		
		public TabsAdapter(FragmentActivity fragmentActivity ,TabHost tabHost,ViewPager viewPager) {
			super(fragmentActivity.getSupportFragmentManager());
			mContext=fragmentActivity;
			mTabHost=tabHost;
			mViewPager=viewPager;
			 mTabHost.setOnTabChangedListener(this);
	            mViewPager.setAdapter(this);
	            mViewPager.setOnPageChangeListener(this);
		}
		 static final class TabInfo {
	            private final String tag;
	            private final Class<?> clss;
	            private final Bundle args;

	            TabInfo(String _tag, Class<?> _class, Bundle _args) {
	                tag = _tag;
	                clss = _class;
	                args = _args;
	            }
	        }

	        static class DummyTabFactory implements TabHost.TabContentFactory {
	            private final Context mContext;

	            public DummyTabFactory(Context context) {
	                mContext = context;
	            }

	            @Override
	            public View createTabContent(String tag) {
	                View v = new View(mContext);
	                v.setMinimumWidth(0);
	                v.setMinimumHeight(0);
	                return v;
	            }
	        }
	        
	        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
	            tabSpec.setContent(new DummyTabFactory(mContext));
	            String tag = tabSpec.getTag();

	            TabInfo info = new TabInfo(tag, clss, args);
	            mTabs.add(info);
	            mTabHost.addTab(tabSpec);
	            notifyDataSetChanged();
	        }
		@Override
		public void onTabChanged(String tabId) {
			 int position = mTabHost.getCurrentTab();
	            mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(arg0);
            widget.setDescendantFocusability(oldFocusability);
            pageChange(arg0);
		}

		@Override
		public Fragment getItem(int arg0) {
			  TabInfo info = mTabs.get(arg0);
	            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			 return mTabs.size();
		}
		
	}

}
