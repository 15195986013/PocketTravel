package com.njbd.pt.utils.enums;

import java.sql.Time;

/**
 * Created by 李建成
 *
 * @date 2016/12/9-13:36.on NJBD
 */
public enum Day {

    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    private Day time;

    public Day getTime() {
        return time;
    }

    public void setTime(Day time) {
        this.time = time;
    }
}
