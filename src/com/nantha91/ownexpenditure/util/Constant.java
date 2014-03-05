package com.nantha91.ownexpenditure.util;

import com.nantha91.ownexpenditure.R;

import android.widget.EditText;

public class Constant {

	public static boolean checkData(EditText data) {
		if (data.getText().toString().trim().length() > 0) {
			data.setBackgroundResource(R.drawable.bg_black);
			return true;
		} else {
			data.setBackgroundResource(R.drawable.bg_red);
			data.requestFocus();
			return false;
		}
	}

}
