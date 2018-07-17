package ming.com.avlearner.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 此文件涉及嵌入版，请在同步独立版程序时，同步嵌入版程序
 */

public class TimeUtils {
    
    private TimeUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String formatMsShortTime(long lt) {
        Date msgTime = new Date(lt);

        return msgTime.toString().substring(11, 16);
    }

    public static String formatMsDate(long lt) {
        Date msgTime = new Date(lt);

        int year = msgTime.getYear() + 1900;
        int month = msgTime.getMonth();
        int day = msgTime.getDate();

        return year + "-" + (month + 1) + "-" + (day);
    }


    /**
     * 返回 年 月 日 字符串
     *
     * @param lt
     * @return
     */
    public static String formatMsDateYearAndMonthAndDay(long lt) {
        Date msgTime = new Date(lt);

        int year = msgTime.getYear() + 1900;
        int month = msgTime.getMonth();
        int day = msgTime.getDate();

        if (month < 10 || day < 10) {

            if (month < 10 && day < 10) {
                return year + "年0" + (month + 1) + "月0" + (day) + "日";
            } else if (month < 10 && day >= 10) {
                return year + "年0" + (month + 1) + "月" + (day) + "日";
            } else if (month >= 10 && day < 10) {
                return year + "年" + (month + 1) + "月0" + (day) + "日";
            } else {
                return year + "年" + (month + 1) + "月" + (day) + "日";
            }

        } else {
            return year + "年" + (month + 1) + "月" + (day) + "日";
        }


//        return year + "年" + (month + 1) + "月" + (day) + "日";
    }

    public static String formatDate(long lt) {
        Date msgTime = new Date(lt);

        int year = msgTime.getYear() + 1900;
        int month = msgTime.getMonth() + 1;
        int day = msgTime.getDate();
        int h = msgTime.getHours();
        int m = msgTime.getMinutes();
        int s = msgTime.getSeconds();

        return year + "-" + (month) + "-" + (day) + " " + h + ":" + m + ":" + s;
    }


    /**
     * 返回指定时间到当前时间所经过的时间，并格式化其显示
     *
     * @param since
     * @return
     */
    public static String getTimeFromNow(long since) {
        long offset = System.currentTimeMillis() - since;
        if (offset < 0) {
            return "刚刚";
        }
        long second = offset / 1000;
        if (second < 60) {
            // if (second == 0) {
            // second = 1;
            // }
            // return second + "秒钟前";
            return "刚刚";
        }
        long minutes = second / 60;
        if (minutes < 60) {
            return minutes + "分钟前";
        }
        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "小时前";
        }
        long days = hours / 24;
        if (days < 30) {
            return days + "天前";
        }
        long months = days / 30;
        if (months < 12) {
            return months + "月前";
        }
        return "很久以前";
    }

    public static boolean isInTheSameYear(long src, long dest) {
        Calendar last = Calendar.getInstance();
        last.setTimeInMillis(src);
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(dest);
        int lastYear = last.get(Calendar.YEAR);
        int currentYear = current.get(Calendar.YEAR);
        return (lastYear == currentYear);
    }

    /**
     * 判断两个时间是否在同一天,用于导航获取域名对应IP及端口时使用
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isInTheSameDay(String date1, String date2) {
        if (!TextUtils.isEmpty(date1) && !TextUtils.isEmpty(date2)) {
            if (date1.equals(date2)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 判断两个时间是否在同一天,用于导航获取域名对应IP及端口时使用
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean isInTheSameDay(long src, long dest) {
        Calendar last = Calendar.getInstance();
        last.setTimeInMillis(src);
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(dest);
        int lastDay = last.get(Calendar.DAY_OF_YEAR);
        int currentDay = current.get(Calendar.DAY_OF_YEAR);
        return (lastDay == currentDay);
    }


    /**
     * 08-07 12:36
     *
     * @return
     */
    public static String MM_DD_HH_mm_Format(long times) {
        String formatString = "MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date(times));
    }

    public static String yyyy_MM_DD_HH_mm_format(long times) {
        String formatString = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date(times));
    }

    public static String yyyy_MM_DD_HH_mm_format_forcrash(long times) {
        String formatString = "yyyy_MM_dd_HH_mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date(times));
    }

    public static String yyyy_MM_DD_format(long times) {
        String formatString = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date(times));
    }

    public static String MM_DD_format(long times) {
        String formatString = "MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString);
        return dateFormat.format(new Date(times));
    }


    public static String yyyy_MM_DD_Format(long times) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(times));
    }


    /**
     * 方法简介：判断同一年中的两天是否在同一周
     * 输入项说明：
     * 返回项说明：
     *
     * @param date
     * @return
     */
    public static boolean isThisWeek(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar theDay = Calendar.getInstance();
        theDay.setTime(date);
        now.setFirstDayOfWeek(Calendar.MONDAY);
        theDay.setFirstDayOfWeek(Calendar.MONDAY);

        int nowDayOfWeek = now.get(Calendar.WEEK_OF_YEAR);
        int theDayOfWeek = theDay.get(Calendar.WEEK_OF_YEAR);
        if (nowDayOfWeek == theDayOfWeek) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 方法简介：返回一天是周几
     * 输入项说明：
     * 返回项说明：
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weekArray = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar theDay = Calendar.getInstance();
        theDay.setTime(date);
        int week = theDay.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        if (week >= 1 && week <= 7) {
            weekStr = weekArray[week];
        }
        return weekStr;
    }

    public static String formatDateDiff(long diffDate) {
        boolean reverse = false;
        if (diffDate < 0) {
            reverse = true;
            diffDate = Math.abs(diffDate);
        }
        long day = diffDate / (24 * 60 * 60 * 1000);
        long hour = (diffDate / (60 * 60 * 1000) - day * 24);
        long min = ((diffDate / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (diffDate / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day > 0) {
            return String.valueOf(day) + (reverse == true ? "天后" : "天前");
        }
        if (hour > 0) {
            return String.valueOf(hour) + (reverse == true ? "小时后" : "小时前");
        }
        if (min > 0) {
            return String.valueOf(min) + (reverse == true ? "分钟后" : "分钟前");
        }
        if (s > 0) {
            return String.valueOf(s) + (reverse == true ? "秒后" : "秒前");
        } else {
            return "1秒前";
        }
    }


}
