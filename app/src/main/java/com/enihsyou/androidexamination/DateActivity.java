package com.enihsyou.androidexamination;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        Button datePickerButton = findViewById(R.id.date_picker);
        Button timePickerButton = findViewById(R.id.time_picker);

        datePickerButton.setOnClickListener(new DatePickerButtonListener());
        timePickerButton.setOnClickListener(new TimePickerButtonListener());
    }

    private class DatePickerButtonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                final DatePickerDialog dialog = new DatePickerDialog(DateActivity.this);
                dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(
                        final DatePicker view, final int year, final int month, final int dayOfMonth
                    ) {
                        Toast.makeText(DateActivity.this, String.format("用户选择了 %d年 %d月 %d日", year, month, dayOfMonth),
                            Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        }
    }

    private class TimePickerButtonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            final TimePickerDialog dialog =
                new TimePickerDialog(DateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                        Toast.makeText(DateActivity.this, String.format("用户选择了 %d:%d", hourOfDay, minute),
                            Toast.LENGTH_SHORT).show();
                    }
                }, 12, 0, true);
            dialog.show();
        }
    }
}
