package com.indivisible.counter;

//import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends ListActivity{

	String[] alarmTitles;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		String[] alarmTitles = bundle.getStringArray("alarmTitles");
		setListAdapter(new ArrayAdapter<String>(
				Menu.this, android.R.layout.simple_list_item_1, alarmTitles));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Class<?> ourClass = null;
		try {
			String nextClass = alarmTitles[position];
			ourClass = Class.forName("com.indivisible.thenewboston." + nextClass);
			Intent ourIntent = new Intent(Menu.this, ourClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			Toast toast = Toast.makeText(Menu.this, "Not a suitable class", Toast.LENGTH_SHORT);
			toast.show();
			e.printStackTrace();
		}
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(android.view.Menu menu) {			// long ref to Menu as we made a "Menu"
//		// Auto-generated method stub
//		super.onCreateOptionsMenu(menu);
//		MenuInflater blowUp = getMenuInflater();
//		blowUp.inflate(R.menu.cool_menu, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Auto-generated method stub
//		//return super.onOptionsItemSelected(item);  removed because we're not passing up to super
//		switch(item.getItemId()){
//		case R.id.aboutUs:
//			Intent i = new Intent("com.indivisible.thenewboston.ABOUT");
//			startActivity(i);
//			break;
//		case R.id.prefs:
//			Intent p = new Intent("com.indivisible.thenewboston.PREFS");
//			startActivity(p);
//			break;
//		case R.id.exit:
//			finish();
//			break;
//		}
//		return false; 	// arbitrary? just set to fit method shell?
//	}
	
	


}
