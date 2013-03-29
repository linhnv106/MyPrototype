package com.example.myprototype;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuListFragment extends ListFragment {				
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<20;i++){
			list.add("Menu " + i);
		}
		MenuAdapter adapter = new MenuAdapter(getActivity(),list);
		setListAdapter(adapter);
	}

	class MenuAdapter extends ArrayAdapter<String>{

		public MenuAdapter(Context context,List<String> list) {
			super(context,0,list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			TextView text = new TextView(getContext());
			
			return text;
		}
		
		
	}

}
