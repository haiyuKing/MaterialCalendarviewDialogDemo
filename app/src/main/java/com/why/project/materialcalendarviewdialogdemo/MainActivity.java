package com.why.project.materialcalendarviewdialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.MaterialCalendarDialog;
import com.why.project.materialcalendarviewdialogdemo.utils.DateTimeHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Create By HaiyuKing
 * Used 基于开源库material-calendarview 材料设计日历控件《https://github.com/prolificinteractive/material-calendarview》的使用Demo
 * 注意：建议搭配工具类——DateTimeHelper【日期类型与字符串互转以及日期对比相关操作】
 */
public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private EditText edt_starttime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initDatas();
		initEvents();
	}

	private void initViews() {
		edt_starttime = (EditText) findViewById(R.id.edt_starttime);

	}

	private void initDatas() {
	}

	private void initEvents() {

		//开始日期输入框的点击事件监听
		edt_starttime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Date todayDate = Calendar.getInstance().getTime();
				if(! TextUtils.isEmpty(edt_starttime.getText().toString())){
					try {
						todayDate = DateTimeHelper.parseStringToDate(edt_starttime.getText().toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				//显示日历对话框
				MaterialCalendarDialog calendarDialog = MaterialCalendarDialog.getInstance(MainActivity.this,todayDate);
				calendarDialog.setOnOkClickLitener(new MaterialCalendarDialog.OnOkClickLitener() {
					@Override
					public void onOkClick(Date date) {
						edt_starttime.setText(DateTimeHelper.formatToString(date,"yyyy-MM-dd"));
					}
				});
				calendarDialog.show(getSupportFragmentManager(),TAG);
			}
		});
	}
}
