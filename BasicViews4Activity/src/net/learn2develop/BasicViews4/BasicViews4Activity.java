package net.learn2develop.BasicViews4;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class BasicViews4Activity extends Activity {
	TimePicker timePicker;
	DatePicker datePicker;

	int hour, minute;
	int yr, month, day;

	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		timePicker = (TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);

		// showDialog(TIME_DIALOG_ID);
		datePicker = (DatePicker) findViewById(R.id.datePicker);

		//---get the current date---
		Calendar today = Calendar.getInstance();
		yr = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH);
		day = today.get(Calendar.DAY_OF_MONTH);

		showDialog(DATE_DIALOG_ID);        
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(
					this, mTimeSetListener, hour, minute, false);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(
					this, mDateSetListener, yr, month, day);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener()
	{
		public void onDateSet(
				DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			yr = year;
			month = monthOfYear;
			day = dayOfMonth;
			Toast.makeText(getBaseContext(),
					"You have selected : " + (month + 1) +
					"/" + day + "/" + year,
					Toast.LENGTH_SHORT).show();
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
			new TimePickerDialog.OnTimeSetListener()
	{
		public void onTimeSet(
				TimePicker view, int hourOfDay, int minuteOfHour)
		{
			hour = hourOfDay;
			minute = minuteOfHour;

			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");			
			Date date = new Date(0,0,0, hour, minute);
			String strDate = timeFormat.format(date);

			Toast.makeText(getBaseContext(),
					"You have selected " + strDate,
					Toast.LENGTH_SHORT).show();			
		}
	};

	public void onClick(View view) {
		Toast.makeText(getBaseContext(),
				"Date selected:" + (datePicker.getMonth() + 1) +
				"/" + datePicker.getDayOfMonth() +
				"/" + datePicker.getYear() + "\n" +
				"Time selected:" + timePicker.getCurrentHour() +
				":" + timePicker.getCurrentMinute(),
				Toast.LENGTH_SHORT).show();
	}

}