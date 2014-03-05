package com.nantha91.ownexpenditure.util;

import com.nantha91.ownexpenditure.R;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Constant {

	private static Toast toast;

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

	public static void ToastLong(Context context, String message) {
		toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.show();

	}

	public static void ToastShort(Context context, String message) {
		toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();

	}
}
