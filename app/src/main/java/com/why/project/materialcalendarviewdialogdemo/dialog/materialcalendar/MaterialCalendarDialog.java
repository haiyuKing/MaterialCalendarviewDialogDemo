package com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.why.project.materialcalendarviewdialogdemo.R;
import com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.decorators.MySelectorBgDecorator;
import com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.decorators.SelectedDayDecorator;
import com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.decorators.TodayDecorator;

import java.util.Calendar;
import java.util.Date;

import static com.why.project.materialcalendarviewdialogdemo.R.id.calendarView;

/**
 * Created by HaiyuKing
 * Used  Material样式日历对话框
 * 参考资料：https://github.com/prolificinteractive/material-calendarview
 */

public class MaterialCalendarDialog extends DialogFragment {

	private static final String TAG = "MaterialCalendarDialog";
	/**View实例*/
	private View myView;
	/**context实例*/
	private Context mContext;
	/**标记：用来代表是从哪个界面打开的这个对话框*/
	private String mTag;

	//年份文本
	private TextView mYear;
	//月日星期文本
	private TextView mMonthDay;
	//日历控件
	private MaterialCalendarView mCalendarView;

	//取消文本
	private TextView mCancleBtn;
	//确定文本
	private TextView mOkBtn;

	private Date selectedDate;//选中的日期

	private final SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator();

	public static MaterialCalendarDialog getInstance(Context mContext, Date selectedDate){
		MaterialCalendarDialog dialog = new MaterialCalendarDialog();
		dialog.mContext = mContext;
		if(selectedDate == null){
			Calendar calendar = Calendar.getInstance();
			selectedDate = calendar.getTime();
		}
		dialog.selectedDate = selectedDate;

		return dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);//全屏
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));//设置背景为半透明，并且没有标题
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		//设置窗体全屏
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		myView = inflater.inflate(R.layout.dialog_material_calendar, container, false);
		return myView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化控件以及设置初始数据和监听事件
		initView();
		//初始化数据
		initData();
		//初始化监听事件
		initEvent();
	}


	/**
	 * 设置宽度和高度值，以及打开的动画效果
	 */
	@Override
	public void onStart() {
		super.onStart();

		//设置对话框的宽高，必须在onStart中
//        Window window = this.getDialog().getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//全屏（盖住状态栏）
//        window.setGravity(Gravity.BOTTOM);//设置在底部
		//打开的动画效果--缩放+渐隐

		//设置对话框的宽高，必须在onStart中
		DisplayMetrics metrics = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Window window = this.getDialog().getWindow();
		window.setLayout(metrics.widthPixels, metrics.heightPixels - getStatusBarHeight(mContext));
		window.setGravity(Gravity.BOTTOM);//设置在底部
		//打开的动画效果--缩放+渐隐
	}

	/**获取状态栏的高度*/
	private int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**实例化控件*/
	private void initView() {
		mYear = (TextView) myView.findViewById(R.id.tv_year);
		mMonthDay = (TextView) myView.findViewById(R.id.tv_monthday);
		mCalendarView = (MaterialCalendarView) myView.findViewById(calendarView);
		mCancleBtn = (TextView) myView.findViewById(R.id.tv_cancel);
		mOkBtn = (TextView) myView.findViewById(R.id.tv_ok);
	}

	private void initData() {
		mTag = this.getTag();
		Log.e(TAG, "mTag="+mTag);

		parseDateToYearMonthDayWeek(selectedDate);//显示默认的日期文本

		mCalendarView.setSelectedDate(selectedDate);//设置选中的日期
		mCalendarView.setCurrentDate(selectedDate);//实现定位到选中日期的当月

		mCalendarView.addDecorators(new MySelectorBgDecorator(mContext),
				selectedDayDecorator,
				new TodayDecorator(mContext));

	}

	//初始化监听事件
	private void initEvent() {

		//日历控件的点击事件
		mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
			@Override
			public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
				selectedDate = date.getDate();
				parseDateToYearMonthDayWeek(selectedDate);

				//刷新选中的状态
				selectedDayDecorator.setDate(selectedDate);
				widget.invalidateDecorators();
			}
		});

		//取消按钮的点击事件
		mCancleBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		//确定按钮的点击事件
		mOkBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(OnokClickLitener != null){
					OnokClickLitener.onOkClick(selectedDate);
				}
			}
		});

	}


	/*=====================OnOkClickLitener================================*/
	public interface OnOkClickLitener
	{
		/**确定按钮的点击事件*/
		void onOkClick(Date date);
	}

	private OnOkClickLitener OnokClickLitener;

	public void setOnOkClickLitener(OnOkClickLitener OnokClickLitener)
	{
		this.OnokClickLitener = OnokClickLitener;
	}

	/**解析日期，获取年月日星期*/
	private void parseDateToYearMonthDayWeek(Date date){

		//获取默认选中的日期的年月日星期的值，并赋值
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期

		String yearStr = calendar.get(Calendar.YEAR)+"";//获取年份
		int month = calendar.get(Calendar.MONTH) + 1;//获取月份
		String monthStr = month < 10 ? "0" + month : month + "";
		int day = calendar.get(Calendar.DATE);//获取日
		String dayStr = day < 10 ? "0" + day : day + "";
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		String weekStr = "";
		/*星期日:Calendar.SUNDAY=1
		 *星期一:Calendar.MONDAY=2
		 *星期二:Calendar.TUESDAY=3
		 *星期三:Calendar.WEDNESDAY=4
		 *星期四:Calendar.THURSDAY=5
		 *星期五:Calendar.FRIDAY=6
		 *星期六:Calendar.SATURDAY=7 */
		switch (week) {
			case 1:
				weekStr = "周日";
				break;
			case 2:
				weekStr = "周一";
				break;
			case 3:
				weekStr = "周二";
				break;
			case 4:
				weekStr = "周三";
				break;
			case 5:
				weekStr = "周四";
				break;
			case 6:
				weekStr = "周五";
				break;
			case 7:
				weekStr = "周六";
				break;
			default:
				break;
		}

		Log.e(TAG, yearStr + "年" + monthStr + "月" + dayStr + "日" + "  " + weekStr);

		mYear.setText(yearStr + "年");

		String formatStr = "%1$s月%2$s日  %3$s";
		mMonthDay.setText(String.format(formatStr,monthStr,dayStr,weekStr));

	}

}
