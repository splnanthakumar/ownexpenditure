package com.nantha91.ownexpenditure.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.nantha91.ownexpenditure.R;
import com.nantha91.ownexpenditure.util.Constant;

public class MainActivity extends Activity implements
		android.view.View.OnClickListener {
	SQLiteDatabase sqlite;
	ImageButton imgbtn_add, btn_save, btn_cancel;
	EditText ed_item, ed_rupee;
	LinearLayout add;
	Set<String> s_item = new HashSet<String>();
	ArrayAdapter<String> adapter;
	Spinner spin_items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		find();
		sqlite = createDB();
		createTable();
		getData();
		setData();
	}

	private void find() {
		// TODO Auto-generated method stub
		imgbtn_add = (ImageButton) findViewById(R.id.imageButton1);
		btn_save = (ImageButton) findViewById(R.id.imgbtn_save);
		btn_cancel = (ImageButton) findViewById(R.id.imgbtn_cancel);
		ed_item = (EditText) findViewById(R.id.edtxt_itemname);
		ed_rupee = (EditText) findViewById(R.id.edtxt_rupee);
		add = (LinearLayout) findViewById(R.id.linear_additem);
		spin_items = (Spinner) findViewById(R.id.spin_items);

		// initialization
		add.setVisibility(View.GONE);

		// listeners
		imgbtn_add.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButton1:
			add.setVisibility(View.VISIBLE);
			break;

		case R.id.imgbtn_save:
			if (Constant.checkData(ed_item)) {
				insertData(ed_item.getText().toString().trim());
			}
			break;
		case R.id.imgbtn_cancel:
			add.setVisibility(View.GONE);

			break;

		default:
			break;
		}
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

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // TODO
	 * Auto-generated method stub MenuInflater inflater = getMenuInflater();
	 * inflater.inflate(R.menu.main, menu); return
	 * super.onCreateOptionsMenu(menu);
	 * 
	 * }
	 */
}
