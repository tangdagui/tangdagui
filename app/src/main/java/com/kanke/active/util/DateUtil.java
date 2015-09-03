/**
 * ****************************************************************************
 *
 * @(#)DateUtil.java 2012-12-27
 * <p>
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * *****************************************************************************
 */
package com.kanke.active.util;

import android.annotation.SuppressLint;

import com.kanke.active.base.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2012-12-27 下午01:26:15
 */
public final class DateUtil {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYYMMDDHHMMSS = "yyyymmddhhmmss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMMMDD = "yyyyMMMMdd";

    public static final String YYYYMMMMDDDD = "yyyyMMMMdddd";

    public static final String YYYY_MM_DD_1 = "yyyy:MM:dd";

    public static final String YYYY_MM_DD_2 = "yyyy/MM/dd";

    public static final String YYYY_MM_DD_3 = "yyyy.MM.dd";

    public static final String YYYY_MM_DD_4 = "yyyy-MM-dd";

    public static final String DD_MM_YYYY_1 = "dd-MM-yyyy";

    public static final String DD_MM_YYYY_2 = "dd.MM.yyyy";

    public static final String DD_MM_YYYY_3 = "dd/MM/yyyy";

    public static final String MM_DD_YYYY_1 = "MM-dd-yyyy";

    public static final String MM_DD_YYYY_2 = "MM.dd.yyyy";

    public static final String MM_DD_YYYY_3 = "MM/dd/yyyy";

    public static final String YYYY_DD_MM_1 = "yyyy:dd:MM";

    public static final String YYYY_DD_MM_2 = "yyyy/dd/MM";

    public static final String YYYY_DD_MM_3 = "yyyy.dd.MM";
    public static final String YYYY_DD_3 = "yyyy-MM";

    private DateUtil() {

    }

    /**
     * 获取并格式化当前时间
     *
     * @return String
     */
    public static String formatCurrentDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(System.currentTimeMillis());
    }

    /**
     * 格式化指定日期时间
     *
     * @param date
     * @param format
     * @return String
     */
    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);

    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTime1(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MM_DD_YYYY_1);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTime(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_ORDER_DATE_NOM);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }
    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimeforDynamic(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_ORDER_DATE);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef1(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE_1);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef8(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE_3);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef4(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE_FOR_SERVER);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }
    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef5(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE_2);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }
    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef3(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE_2);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String timestampToDateTimef2(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_COMMON_DATE);
        String date = dateFormat.format(new Date(timeStamp * 1000L));
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String GetCurentTime() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DETAIL_DATE);
        String date = dateFormat.format(d);
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String GetCurentTime1() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_COMMON_DATE);
        String date = dateFormat.format(d);
        return date;
    }

    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }

    public static Long dateTimeToTimestampf(String dateTime) {
        Long timestamp;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = date.getTime() / 1000;
        return timestamp;
    }

    public static Long dateTimeToTimestampf1(String dateTime) {
        Long timestamp;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = date.getTime() / 1000;
        return timestamp;
    }

    /*将时间戳转化为星期*/
    public static String getWeek(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp*1000));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }

    //将当前时间转化为时间戳
    public static Long nowTimeToTimestampf() {
        Long timestamp;
        Date time = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        try {
             time = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timestamp = time.getTime() / 1000;
        return timestamp;
    }

    @SuppressLint("SimpleDateFormat")
    public static String diff(long timeStamp) {
        String ret = "";
        try {
            long s = (new Date().getTime() - new Date(timeStamp * 1000L).getTime());
            //System.err.println("当前时间"+new Date().getTime());
            // System.err.println("历史时间"+new Date(timeStamp*1000L).getTime());
            long count = 0;
            if ((count = s / (1000 * 60 * 60 * 24)) > 0) {
                if (count > 365) {
                    ret = "超过1年";
                } else if (count > 30) {
                    ret = count / 30 + "月前";
                } else {
                    ret = count + "天前";
                }
            } else if ((count = s / (1000 * 60 * 60)) > 0) {
                ret = count + "小时前";
            } else if ((count = s / (1000 * 60)) > 0) {
                ret = count + "分钟前";
            } else {
                ret = "刚刚";
            }
        } catch (Exception e) {
        }

        return ret;
    }

    /**
     * 格式化指定日期时间字符串
     *
     * @param dateStr
     * @return String
     */
    public static String formatDate(String dateStr) {
        Date date = new Date(dateStr);
        DateFormat dateFormat = new SimpleDateFormat(YYYY_DD_3);
        return dateFormat.format(date);
    }

    public static int compare_date(String oldDate, String newDate) {

        DateFormat df = new SimpleDateFormat(YYYY_DD_3);
        try {
            Date dt1 = df.parse(oldDate);
            Date dt2 = df.parse(newDate);
            if (dt1.after(dt2)) {
                System.out.println("oldDate newDate");
                return 1;
            } else if (dt1.before(dt2)) {
                System.out.println("oldDate在newDate后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
