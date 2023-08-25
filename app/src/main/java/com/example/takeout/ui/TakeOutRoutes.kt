package com.example.takeout.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.takeout.R

enum class Screen(@StringRes val resourceId:Int, @DrawableRes val icon:Int?) {
    Home(R.string.home, R.drawable.home),
    MonthView(R.string.month_view, R.drawable.calendar_month),
    TrendView(R.string.trend_view, R.drawable.trend_up),
    Settings(R.string.settings, R.drawable.settings),
    AddNewEntry(R.string.add_new_entry, null),
    FoodList(R.string.food_list, null),
    FoodItem(R.string.food_item, null),
}

