package com.nantha91.ownexpenditure.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nantha91.ownexpenditure.R;
import com.nantha91.ownexpenditure.util.Constant;

public class MainActivity extends Activity implements OnDateChangeListener {
	SQLiteDatabase sqlite;
	ImageButton imgbtn_add, btn_save, btn_cancel;
	EditText ed_item, ed_rupee;
	LinearLayout add;
	Set<String> s_item = new HashSet<String>();
	ArrayAdapter<String> adapter;
	Spinner spin_items;
	CalendarView cal;
	Context context = MainActivity.this;

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
		/*
		 * ActionBar bar = getActionBar(); bar.setDisplayHomeAsUpEnabled(true);
		 * bar.setIcon(R.drawable.ic_action_cloud);
		 */
	}

	private void find() {
		// TODO Auto-generated method stub

		spin_items = (Spinner) findViewById(R.id.spin_items);
		cal = (CalendarView) findViewById(R.id.calendarView1);
		cal.setOnDateChangeListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		Constant.ToastLong(getApplicationContext(), year + "/" + month + "/"
				+ dayOfMonth);
		String date1 = Todaydate();
		String date2 = year + "-" + month + "-" + dayOfMonth;
		if (checkDateEqu(date1, date2, "equal")) {
			startActivity(new Intent(MainActivity.this, DayActivity.class));
		} else {
			Constant.ToastLong(getApplicationContext(),
					"Day should be Today to enter your activities");
		}

	}

	private String Todaydate() {
		// TODO Auto-generated method stub
		String today = null;
		Calendar calendar = Calendar.getInstance();
		today = calendar.get(Calendar.YEAR) + "-"
				+ calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DATE);
		return today;

	}

	private boolean checkDateEqu(String date1, String date2, String check) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date Date1 = null, Date2 = null;
		try {
			Date1 = sdf.parse(date1);
			Date2 = sdf.parse(date2);
			if (check.equals("equal")) {
				if (Date1.compareTo(Date2) == 0) {
					Log.v("date is :", "OK");
					return true;
				} else
					Constant.ToastShort(MainActivity.this,
							"Enter the proper date for  Rent start date and Lease Start Date");
				return false;
			} else if (Date1.compareTo(Date2) > 0) {
				Log.v("date is greate :", "ok");
				return true;
			} else
				Constant.ToastShort(MainActivity.this,
						"Enter the proper  Lease End Date");
			return false;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

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
