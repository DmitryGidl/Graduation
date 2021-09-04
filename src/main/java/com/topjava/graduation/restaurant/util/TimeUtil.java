package com.topjava.graduation.restaurant.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class TimeUtil {
    private static final LocalTime voteFinish = LocalTime.of(11,0);

    public static boolean  isDateToday(LocalDate localDate) {
       return localDate.isEqual(localDate);
    }
    public static LocalTime getVoteFinishTime() {
        return voteFinish;
    }
}
