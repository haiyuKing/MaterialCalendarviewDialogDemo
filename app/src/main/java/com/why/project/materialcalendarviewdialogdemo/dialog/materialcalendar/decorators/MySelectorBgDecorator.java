package com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.why.project.materialcalendarviewdialogdemo.R;

/**
 *  自定义选中的日期的背景颜色
 */
public class MySelectorBgDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public MySelectorBgDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.material_calendar_decorator_selected_bg);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
