package com.nantha91.ownexpenditure.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nantha91.ownexpenditure.R;
import com.nantha91.ownexpenditure.util.Constant;

public class MainActivity extends Activity {
	SQLiteDatabase sqlite;
	ImageButton imgbtn_add, btn_save, btn_cancel;
	EditText ed_item, ed_rupee;
	LinearLayout add;
	Set<String> s_item = new HashSet<String>();
	ArrayAdapter<String> adapter;
	Spinner spin_items;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		find();
		sqlite = createDB();
		createTable();
		getData();
		setData();
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setIcon(R.drawable.ic_action_cloud);
	}

	private void find() {
		// TODO Auto-generated method stub

		spin_items = (Spinner) findViewById(R.id.spin_items);

	}

	private void createTable() {
		// TODO Auto-generated method stub
		String query = "create table if not exists items" + "(" + "name TEXT,"
				+ "rupees INTEGER" + ")";
		if (sqlite != null) {
			sqlite.execSQL(query);
		}

	}

	private SQLiteDatabase createDB() {
		// TODO Auto-generated method stub
		SQLiteDatabase sql = openOrCreateDatabase("expenditure",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		return sql;
	}

	private void insertData(String itemName) {
		// TODO Auto-generated method stub
		ContentValues content = new ContentValues();
		content.put("name", itemName);
		content.put("rupees", 6);
		sqlite.insert("items", null, content);
		ed_item.setText("");
		getData();
	}

	private void getData() {
		// TODO Auto-generated method stub
		Cursor cur = sqlite.rawQuery("select * from items", null);
		if (cur != null) {

			cur.moveToFirst();
			for (int index = 0; index < cur.getCount(); index++) {
				s_item.add(cur.getString(cur.getColumnIndex("name")));
				cur.moveToNext();
			}
		}
	}

	private void setData() {
		List<String> ls_item = new ArrayList<String>(s_item);
		if (ls_item.size() > 0) {
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, ls_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spin_items.setAdapter(adapter);
		}
	}

	private boolean checkData() {
		// TODO Auto-generated method stub
		if (ed_item.getText().toString().trim().length() > 0) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "settings", Toast.LENGTH_LONG).show();

			break;
		case R.id.add_item:
			Toast.makeText(this, "add item", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this, AddItems.class));
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
