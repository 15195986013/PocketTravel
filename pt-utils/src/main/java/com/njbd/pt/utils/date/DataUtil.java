package com.njbd.pt.utils.date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create 李建成
 * on 16/12/6.
 */
public class DataUtil {

    public final static Long timeDiffer(Date date1, Date date2) {
        long diff = 0;
        try {
            diff = (date1.getTime() - date2.getTime()) / (1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    public static List<Date> findMonths(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.MONTH, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    public static List<Date> findHours(Date dBegin, Date dEnd) {
        List dateList = new ArrayList();
        dateList.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.HOUR, 1);
            dateList.add(calBegin.getTime());
        }
        return dateList;
    }

    /**
     * @return
     */
    public static Date getDate(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * string 转date  yyyy-MM-dd HH:mm
     * @return
     */
    public static Date getDateMin(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatMillisTo(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * string 转date  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateMinSecond(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * string 转date
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 得到年月日
     * @return
     */
    public static String getYearDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }


    /**
     * 得到时间的一串数字
     */
    public static String getNumberDate() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        System.out.println(hashCodeV);
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }


    /**
     * 时间按年向前推
     */
    public static Date getYearToBefore(Calendar c) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("之前:" + f.format(c.getTime()));
        c.add(Calendar.YEAR, -5);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("之后:" + f.format(c.getTime()));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(f.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 判断是否超过24小时
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static boolean judgmentDate(Date start, Date end) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long cha = end.getTime() - start.getTime();
        if (cha < 0) {
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result <= 24 * 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到当前时间的前一天
     * @param date
     * @return
     */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }


    /**
     * 得到当前时间的后一天
     * @param date
     * @return
     */
    public static Date getNextDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }


    /**
     * 两个时间之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 得到指定日期的年月日
     * @param date
     * @return
     */
    public static String getYeas1(String date) {
        DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("YYYY-MM-dd");
        LocalDate localDate = formatter.parseLocalDate(date);
        System.out.println("yearOfCentury: " + localDate.getYearOfEra());
        System.out.println("monthOfYear: " + localDate.getMonthOfYear());
        System.out.println("dayOfMonth: " + localDate.getDayOfMonth());

        return String.valueOf(localDate.getYear());

    }

    /**
     * 得到指定日期的年月日
     * @param date
     * @return
     */
    public static int getYeas2(String date) {
        DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("YYYY-MM-dd");
        LocalDate localDate = formatter.parseLocalDate(date);
        return localDate.getMonthOfYear();
    }


    /**
     * 比较两个日期大小
     * @param dt1
     * @param dt2
     * @return
     */
    public static int compare_date(Date dt1, Date dt2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static Date dateAdd(Date date, Integer dayNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, dayNum);//把日期往后增加dayNum天.整数往后推,负数往前移动
        date = calendar.getTime();   //这个时间就是日期往后推31天的结果
        return date;
    }
    public static String timeAddTime(String time, String time2) {
        String result = null;
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        for (String str : time.split(":")) {
            list.add(Integer.parseInt(str));
        }

        for (String str : time2.split(":")) {
            list2.add(Integer.parseInt(str));
        }
        Integer hour = list.get(0) + list2.get(0);
        Integer minute = list.get(1) + list2.get(1);
        Integer second = list.get(2) + list2.get(2);

        if (second >= 60) {
            minute += second / 60;
            second = second - 60;
        }
        if (minute >= 60) {
            hour += minute / 60;
            minute = minute - 60;
        }
        result = String.format("%02d:%02d:%02d", hour, minute, second);
        return result;
    }

    public static Integer timeToMin(String time) {

        List<Integer> list = new ArrayList<Integer>();

        for (String str : time.split(":")) {
            list.add(Integer.parseInt(str));
        }
        Integer min = 0;
        Integer hour = list.get(0) * 60;
        Integer minute = list.get(1);
        min = hour + minute;
        return min;
    }

}
