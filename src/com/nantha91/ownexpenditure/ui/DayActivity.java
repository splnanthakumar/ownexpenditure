package com.nantha91.ownexpenditure.ui;

import com.nantha91.ownexpenditure.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.widget.EditText;

public class DayActivity extends Activity {
	EditText ed_news;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dayactivities);
		ed_news = (EditText) findViewById(R.id.edtxt_News);
		ed_news.setMovementMethod(ArrowKeyMovementMethod.getInstance());
	}

}
