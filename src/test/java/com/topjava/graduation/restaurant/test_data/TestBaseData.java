package com.topjava.graduation.restaurant.test_data;

import java.time.LocalDate;

public abstract class TestBaseData {
    public static final LocalDate todayDate = LocalDate.now();
    public static final LocalDate yesterdayDate = LocalDate.now().minusDays(1);
    public static final LocalDate tomorrowDate = LocalDate.now().plusDays(1);
}
