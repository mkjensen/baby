/*
 * Copyright 2016 Martin Kamp Jensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mkjensen.baby;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mkjensen.baby.chart.DateFormatAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class GrowthChartFragment extends Fragment {

  private static final long DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000;

  private LineChart chart;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_growth_chart, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initChart();
  }

  private void initChart() {
    chart = (LineChart) getView().findViewById(R.id.chart);
    chart.setDescription(null);

    Calendar calendar = GregorianCalendar.getInstance();

    List<Entry> weightEntries = new ArrayList<>();
    List<Entry> lengthEntries = new ArrayList<>();

    calendar.set(2016, 8, 14);
    weightEntries.add(new Entry(calendar.getTimeInMillis(), 3905f));
    lengthEntries.add(new Entry(calendar.getTimeInMillis(), 54f));

    calendar.set(2016, 8, 19);
    weightEntries.add(new Entry(calendar.getTimeInMillis(), 3760f));
    lengthEntries.add(new Entry(calendar.getTimeInMillis(), 54f));

    calendar.set(2016, 8, 29);
    weightEntries.add(new Entry(calendar.getTimeInMillis(), 4100f));
    lengthEntries.add(new Entry(calendar.getTimeInMillis(), 55f));

    calendar.set(2016, 9, 7);
    weightEntries.add(new Entry(calendar.getTimeInMillis(), 4380f));
    lengthEntries.add(new Entry(calendar.getTimeInMillis(), 58f));

    LineDataSet weightDataSet = new LineDataSet(weightEntries, getString(R.string.chart_weight));
    weightDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
    weightDataSet.setColor(Color.GREEN);
    weightDataSet.setDrawValues(false);

    LineDataSet lengthDataSet = new LineDataSet(lengthEntries, getString(R.string.chart_length));
    lengthDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
    lengthDataSet.setColor(Color.BLUE);
    lengthDataSet.setDrawValues(false);

    chart.getXAxis().setGranularity(DAY_IN_MILLISECONDS);
    chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    chart.getXAxis().setValueFormatter(
        new DateFormatAxisValueFormatter(new SimpleDateFormat("d/M", Locale.getDefault())));

    chart.getAxisLeft().setGranularity(100f);
    chart.getAxisRight().setGranularity(1f);

    chart.setData(new LineData(weightDataSet, lengthDataSet));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    chart = null;
  }
}
