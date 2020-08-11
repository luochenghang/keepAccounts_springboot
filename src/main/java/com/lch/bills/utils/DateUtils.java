package com.lch.bills.utils;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ConfigUtils
 * @Description: 获取配置文件类
 * @date 2018年10月24日 下午4:50:55
 */
public class DateUtils {

    //获取下一个月
    public static String getLastMonth(Date date, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + n); // 设置为上一个月
        date = calendar.getTime();
        String accDate = sdf.format(date);
        return accDate;
    }

    public static List<Map.Entry<String, String>> getMonthMap(Date minDate, Date maxDate) throws ParseException {
        Map<String, String> map = new HashMap<>();
        boolean bo = true;
        int n = 0;
        String currentMonth = getLastMonth(new Date(), 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (minDate == null || maxDate==null){
            map.put(currentMonth, "本月");
            List<Map.Entry<String, String>> res = new ArrayList<Map.Entry<String, String>>(
                    map.entrySet());
            Collections.sort(res, new Comparator<Map.Entry<String, String>>() {

                @Override
                public int compare(Map.Entry<String, String> o1,
                                   Map.Entry<String, String> o2) {
                    return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
                }
            });
            return res;
        }


        String maxDateMonth = getLastMonth(maxDate, 0);
        while (bo) {
            String DateMonth = getLastMonth(minDate, n);

            if (DateMonth.equals(currentMonth)) {
                map.put(DateMonth, "本月");
            } else {
                map.put(DateMonth, DateMonth);
            }

            n++;
            if (sdf.parse(DateMonth).compareTo(sdf.parse(maxDateMonth)) >= 0) {
                bo = false;
            }
        }

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
//                if(o1.getKey().toString()>o2.getKey().toString()){
//                    return 1;
//                }else {
//                    return -1;
//                }
                return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
            }
        });
        return infoIds;


    }

    /**
     * 获取年份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<Map.Entry<String, String>> getYearMap(Date minDate, Date maxDate) throws ParseException {
        Calendar cMin = Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Map<String, String> map = new HashMap<>();
        if (minDate == null || maxDate==null){
            map.put(String.valueOf(now.get(Calendar.YEAR)), "本年");
            List<Map.Entry<String, String>> res = new ArrayList<Map.Entry<String, String>>(
                    map.entrySet());
            Collections.sort(res, new Comparator<Map.Entry<String, String>>() {

                @Override
                public int compare(Map.Entry<String, String> o1,
                                   Map.Entry<String, String> o2) {
                    return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
                }
            });
            return res;
        }
        cMin.setTime(minDate); // 设置为时间
        cMax.setTime(maxDate); // 设置为时间
        for (int i = cMin.get(Calendar.YEAR); i <= cMax.get(Calendar.YEAR); i++) {
            if (now.get(Calendar.YEAR) == i) {
                map.put(String.valueOf(i), "本年");
            } else {
                map.put(String.valueOf(i), String.valueOf(i));
            }

        }

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
            }
        });
        return infoIds;


    }


    //获取startDate距离当前所有的周   return = { 0=本周, 1=上周, 2=上2周, 3=上3周}
    public static List<Map.Entry<String, String>> getWeekMap(Date minDate, Date maxDate) throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, String> map = new HashMap<>();
        if (minDate == null || maxDate==null){
            String currentWeek = getLastTimeInterval(0);
            String array[] = currentWeek.split(",");
            String start_time = array[0];//本周第一天
            map.put(start_time, "本周");
            List<Map.Entry<String, String>> res = new ArrayList<Map.Entry<String, String>>(
                    map.entrySet());
            Collections.sort(res, new Comparator<Map.Entry<String, String>>() {

                @Override
                public int compare(Map.Entry<String, String> o1,
                                   Map.Entry<String, String> o2) {
                    return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
                }
            });
            return res;
        }
        boolean bo = true;
        int n = 0;
        while (bo) {
            String currentWeek = getLastTimeInterval(n);
            String array[] = currentWeek.split(",");
            String current_time = array[0];//前n周的周一

            //获取最小日期当前周 周一
            Date min_time = sdf.parse(getTimeInterval(minDate).split(",")[0]);
            if (n == 0 && minDate.compareTo(sdf.parse(current_time)) <= 0 && sdf.parse(current_time).compareTo(maxDate)<=0){
                map.put(current_time, "本周");
            }

            if (min_time.compareTo(sdf.parse(current_time)) > 0) {
                bo = false;
            }
            if (n > 0)
                map.put(current_time, "上" + n + "周");
            n++;
        }

        //判断当前周的第七天是否大于最大日期 大于或者等于 false 否则true
        String current_end_time = getLastTimeInterval(0).split(",")[1];//本周第一天
        boolean next = sdf.parse(current_end_time).compareTo(maxDate) > 0 ? false : true;
        int t = -1;
        while (next) {
            String currentWeek = getLastTimeInterval(t);
            String array[] = currentWeek.split(",");
            String end_time = array[1];
            String start_time = array[0];//本周第一天

            if (maxDate.compareTo(sdf.parse(end_time)) <= 0) {//如果下t周的最大日期大于等于传过来的最大日期就结束循环
                next = false;
                break;
            }
            map.put(start_time, "下" + (-t) + "周");
            t--;
        }
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            @Override
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
//                if(o1.getKey()>o2.getKey()){
//                    return 1;
//                }else {
//                    return -1;
//                }
                return Collator.getInstance(Locale.CHINESE).compare(o1.getKey(), o2.getKey());
            }
        });
        return infoIds;
    }


    public static String getLastDay(String year_month) throws ParseException {//year_month = 2020-01
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(year_month + "-01"));
        int actualMaximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return  year_month + "-"+ actualMaximum;
    }

    //根据当前日期获得所在周的日期区间（周一和周日日期）

    public static String getTimeInterval(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin + "," + imptimeEnd;
    }

    //根据当前日期获得上n周的日期区间（上n周周一和周日日期）

    public static String getLastTimeInterval(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7 * n);
        calendar2.add(Calendar.DATE, offset2 - 7 * n);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }

    //获取一周开始到结束的list集合
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        Date startDate = sdf.parse("2020-08-09");


//        cal.setTime(sdf.parse("2010-08-03"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK)); //3  星期1
//        cal.setTime(sdf.parse("2010-07-04"));
        System.out.println(getLastDay("2020-01"));//4 星期2
        System.out.println(getLastDay("2020-02"));//4 星期2
        System.out.println(getLastDay("2020-03"));//4 星期2
        System.out.println(getLastDay("2020-04"));//4 星期2
        System.out.println(getLastDay("2020-05"));//4 星期2
        System.out.println(getLastDay("2020-06"));//4 星期2
        System.out.println(getLastDay("2020-07"));//4 星期2
        System.out.println(getLastDay("2020-08"));//4 星期2
        System.out.println(getLastDay("2020-09"));//4 星期2
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-09")));//4 星期2
//        System.out.println(getYearMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-09")));//4 星期2
//
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-10")));//4 星期2
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-10")));//4 星期2
//        System.out.println(getYearMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-09")));//4 星期2
//
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-10")));//4 星期2
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-09")));//4 星期2
//        System.out.println(getYearMap(sdf.parse("2020-08-09"), sdf.parse("2020-08-09")));//4 星期2
//
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-09-09")));//4 星期2
//        System.out.println(getWeekMap(sdf.parse("2020-08-09"), sdf.parse("2020-09-09")));//4 星期2
//        System.out.println(getYearMap(sdf.parse("2020-08-09"), sdf.parse("2020-09-09")));//4 星期2

//        cal.setTime(sdf.parse("2010-08-05"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK)); //星期3
//        cal.setTime(sdf.parse("2010-08-06"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//        cal.setTime(sdf.parse("2010-08-07"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));//7
//        cal.setTime(sdf.parse("2010-08-08"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));//1 6
//
//        cal.setTime(sdf.parse("2010-08-09"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//
//        cal.setTime(sdf.parse("2010-08-10"));
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));

//        Date endDate = sdf.parse("2025-09-19");
//        System.out.println(getLastTimeInterval(0));
//        System.out.println(getLastTimeInterval(1));
//        System.out.println(getLastTimeInterval(2));
//        System.out.println(getLastTimeInterval(3));
//        System.out.println(getLastTimeInterval(4));

//        System.out.println(getMonthMap(null, null));
//        System.out.println(getYearMap(null, null));
//        System.out.println(getWeekMap(null, null));

    }

}
