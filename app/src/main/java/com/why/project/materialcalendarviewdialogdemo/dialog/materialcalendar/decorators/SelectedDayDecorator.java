package com.why.project.materialcalendarviewdialogdemo.dialog.materialcalendar.decorators;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * 选中的日期的文本特殊显示（加粗、加大字号）
 */
public class SelectedDayDecorator implements DayViewDecorator {

    private CalendarDay selectedDate = null;

    public SelectedDayDecorator() {
        selectedDate = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return selectedDate != null && day.equals(selectedDate) && !day.equals(CalendarDay.today());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new StyleSpan(Typeface.BOLD));
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.selectedDate = CalendarDay.from(date);
    }
}
