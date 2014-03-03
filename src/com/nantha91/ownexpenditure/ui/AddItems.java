package com.nantha91.ownexpenditure.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nantha91.ownexpenditure.R;
import com.nantha91.ownexpenditure.util.Constant;

/**
 * @author nantha kumar <splnanthakumar@gmail.com>
 * @date 2-03-2014
 * 
 */
public class AddItems extends Activity implements OnClickListener {
	SQLiteDatabase sqlite;
	Button b_add, b_cancel;
	TextView t_item_name, t_item_price;
	EditText ed_item_name, ed_item_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		addItemFind();
		sqlite = sqlitecreateDB();
	}

	private SQLiteDatabase sqlitecreateDB() {

		return new MainActivity().sqlite;

	}

	private void addItemFind() {
		// TODO Auto-generated method stub
		// findviewbyid
		b_add = (Button) findViewById(R.id.btn_add);
		b_cancel = (Button) findViewById(R.id.btn_cancel);
		t_item_name = (TextView) findViewById(R.id.txt_itemname);
		t_item_price = (TextView) findViewById(R.id.txt_itemprice);
		ed_item_name = (EditText) findViewById(R.id.edtxt_itemname);
		ed_item_price = (EditText) findViewById(R.id.edtxt_itemprice);
		// listeners
		b_add.setOnClickListener(this);
		b_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_add:
			if (check())
				insertData(ed_item_name.getText().toString().trim(),
						ed_item_price.getText().toString().trim());

			break;
		case R.id.btn_cancel:
			finish();
			break;

		default:
			break;
		}

	}

	private void insertData(String itemname, String itemprice) {
		// TODO Auto-generated method stub
		ContentValues content = new ContentValues();
		content.put("item", itemname);
		content.put("price", itemprice);
		sqlite.insert("items", null, content);

	}

	private boolean check() {
		// TODO Auto-generated method stub
		if (Constant.checkData(ed_item_name)
				&& Constant.checkData(ed_item_price))
			return true;
		else
			return false;
	}

}
