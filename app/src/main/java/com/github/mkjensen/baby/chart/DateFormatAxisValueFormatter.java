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

package com.github.mkjensen.baby.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.text.DateFormat;
import java.util.Date;

public class DateFormatAxisValueFormatter implements AxisValueFormatter {

  private final Date date;

  private final DateFormat dateFormat;

  public DateFormatAxisValueFormatter(DateFormat dateFormat) {
    this.date = new Date();
    this.dateFormat = dateFormat;
  }

  @Override
  public String getFormattedValue(float value, AxisBase axis) {
    long timeInMillis = (long) value;
    date.setTime(timeInMillis);
    return dateFormat.format(date);
  }

  @Override
  public int getDecimalDigits() {
    return 0;
  }
}
