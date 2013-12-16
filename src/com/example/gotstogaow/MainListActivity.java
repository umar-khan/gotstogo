package com.example.gotstogaow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainListActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		lvItems = (ListView) findViewById(R.id.lvItems);
		//items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		//Adds items to list manually
		//items.add("Sami");
		//items.add("Umar");
		setupListViewListener();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File peopleFile = new File(filesDir, "people.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(peopleFile));
		} catch (IOException e) {
			e.printStackTrace();
			items = new ArrayList<String>();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File peopleFile = new File(filesDir, "people.txt");
		try {
			FileUtils.writeLines(peopleFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addPersonToGo(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems(); //write to file
	}
	
	
	public void whoGotsToGo(View v) {
		Random randomPerson;
		randomPerson = new Random();
		int indexOfPerson;
		indexOfPerson = randomPerson.nextInt(items.size());
		itemsAdapter.add(items.get(indexOfPerson).toString() + " Gots to Go!");
		saveItems();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}
	

}
