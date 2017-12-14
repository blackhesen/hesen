package com.hesen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    public static final long YEAR_MILLION_SECONDS = 365 * 24 * 60 * 60 * 1000;
    public static final long MONTH_MILLION_SECONDS = 30 * 24 * 60 * 60 * 1000;
    public static final long DAY_MILLION_SECONDS = 24 * 60 * 60 * 1000;
    public static final long HOUR_MILLION_SECONDS = 60 * 60 * 1000;
    public static final long MINUTE_MILLION_SECONDS = 60 * 1000;
    public static final long MILLOIN_SECONDS = 1000;

    public static final String DATE_STRING_FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_STRING_FORMAT_SYS_ALL = "yyyy_MM_dd_HH_mm_ss";
    public static final String DATE_STRING_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm";
    public static final String DATE_STRING_FORMAT_CN = "yyyy年MM月dd日";
    public static final String DATE_STRING_FORMAT_WEEK_CN = "yyyy/MM/dd E";
    public static final String YEAR_MONTH_DATE = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DATE_2 = "yyyy/MM/dd";
    public static final String MONTH_DATE = "MM-dd";
    public static final String MONTH_DATE_CN = "MM月dd日";
    public static final String DATE_FORMAT_TIME = "HH:mm";

    public static final String DATE_STRING_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_STRING_FORMAT_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm";
    public static final String DATE_STRING_FORMAT_DAY2 = "yyyy.MM.dd";
    public static final String DATE_STRING_FORMAT_DAY3 = "yyyyMMdd";
    public static final String DATE_STRING_FORMAT_CN2 = "yyyy年MM月";
    public static final String DATE_STRING_FORMAT_CN3 = "yyyy年";

    public static final String TIME = "HH:mm:ss";
    public static final String DEFAULT = "yyyy-MM-dd";
    public static final String STANDARD = "yyyy-MM-dd HH:mm";
    public static final String ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String SIMPLE_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_STRING_FORMAT_DAY_HOUR_MIN_CN = "yyyy年MM月dd日 HH时mm分";
    public static final String DATE_STRING_FORMAT_MON_DAY_HOUR_MIN_CN = "MM月dd日 HH时mm分";

/******************************Long类型转换为String类型 ****************************************/

    /**
     * 将Long类型时间转换为指定格式的String类型
     *
     * @param dateNumber Long类型的时间
     * @param formatStr 时间格式
     * @return
     */
    public static String dateLongToWantStr(Long dateNumber, String formatStr) {
        if (dateNumber == null || dateNumber.equals(0L) || formatStr == null || formatStr.length() == 0) {
            return null;
        }
        Date date = new Date(dateNumber);
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }
    
    /**
     * 将Long类型时间转换为yyyy-MM-dd HH:mm格式的String类型
     * 
     * @param dateNumber
     * @return
     */
    public static String dateLongToMinStr(Long time) {
    	if (time == null || time.equals(0L)){
    		return null;
    	}
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_DEFAULT);
        return sdf.format(date);
    }
    
    /**
     * 将Long类型时间转换为yyyy-MM-dd HH:mm:ss格式的String类型
     * 
     * @param dateNumber
     * @return
     */
    public static String dateLongToFullStr(Long time) {
    	if (time == null || time.equals(0L)){
    		return null;
    	}
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return sdf.format(date);
    }
    
    /**
     * Long类型时间转化为{yyyy年MM月dd日 HH时mm分ss秒}格式的String类型
     * 
     * @param time
     * @return
     */
    public static String dateLongToFullCNStr(Long time) {
        if (time == null || time.equals(0L))
            return null;
        return new SimpleDateFormat(SIMPLE_CN).format(new Date(time));
    }

/*****************************Date类型转化为String类型****************************************/
    
    /**
     * 将Date类型时间转换为指定格式的String类型
     * 
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToWantStr(Date date, String formatStr) {
        if (date == null || StringUtil.isEmpty(formatStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }
    
    /**
     * 将Date类型时间转换为yyyy-MM-dd HH:mm格式的String类型
     * 
     * @param date
     * @return
     */
    public static String dateToMinStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_DEFAULT);
        return sdf.format(date);
    }
    
    /**
     * 将Date类型时间转换为yyyy-MM-dd HH:mm:ss格式的String类型
     * 
     * @param date
     * @return
     */
    public static String dateToFullStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return sdf.format(date);
    }
   
/*****************************String类型转化为Long类型****************************************/
    
    /**
     * 将String时间以指定格式解析为Long类型的时间
     * 
     * @param format 时间格式
     * @param time 时间字符串
     * @return
     */
    public static Long convertDateStringToDateLong(String format, String time) {
    	if (StringUtil.isEmpty(format) || StringUtil.isEmpty(time)) {
			return null;
		}
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = fmt.parse(time);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if ( null != d){
            return d.getTime();
        }
        else{
            return null;
        }
    }
    
/*****************************String类型转化为Date类型****************************************/
    
    /**
	 * 将String类型的时间以指定格式解析为Date类型
	 * 
	 * @param strDate
	 * @param formatStr
	 * @return
	 */
	public static Date strToDate(String strDate, String formatStr){
		
		if (StringUtil.isEmpty(strDate)) {
            return null;
        }
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);  
	    Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		return date;
	}
	
/*****************************获取系统当前时间****************************************/
	
    /**
     * 获取系统时间格式为{yyyy-MM-dd HH:mm:ss}的String类型
     * 
     * @return
     */
    public static String getSysTime() {
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        String r = format.format(new Date());
        return r;
    }
    
    /**
     * 获取当前时间Long类型，精确到分
     */
    public static Long getTheDayTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_DAY_HOUR_MIN);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_DAY_HOUR_MIN, sdf.format(new Date()));
    }

/*****************************时间段处理****************************************/

    // 段时间
    public static String toPeriodTime(long m) {
        StringBuffer sb = new StringBuffer();
        long years = m / YEAR_MILLION_SECONDS;
        m = m - years * YEAR_MILLION_SECONDS;
        long months = m / MONTH_MILLION_SECONDS;
        m = m - months * MONTH_MILLION_SECONDS;
        long days = m / DAY_MILLION_SECONDS;
        m = m - days * DAY_MILLION_SECONDS;
        long hours = m / HOUR_MILLION_SECONDS;
        m = m - hours * HOUR_MILLION_SECONDS;
        long minutes = m / MINUTE_MILLION_SECONDS;
        if (0 != years) {
            sb.append(String.valueOf(years));
            sb.append("年");
        }
        if (0 != months) {
            sb.append(String.valueOf(months));
            sb.append("个月");
        }
        if (0 != days) {
            sb.append(String.valueOf(days));
            sb.append("天");
        }
        if (0 != hours) {
            sb.append(String.valueOf(hours));
            sb.append("小时");
        }
        if (0 != minutes) {
            sb.append(String.valueOf(minutes));
            sb.append("分钟");
        }
        return sb.toString();
    }
    
    /**
     * 时间段转换
     * 
     * 刚刚
     * 2-59分钟前
     * 1-23小时前
     * 1-29天前
     * 1-12个月前
     * 1年前
     */

    public static String toChangeTime(long m) {

        String result = "刚刚";

        long mtime = System.currentTimeMillis();
        long _mtime = mtime - m;

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        if( _mtime < nm * 2 ){
            result = "刚刚";
        }else if( nm * 2 <= _mtime && _mtime < nm * 60 ){
            long min = _mtime % nd % nh / nm;// 计算差多少分钟
            result = min+"分钟前";
        }else if(nh <= _mtime && _mtime < nh * 24){
            long hour = _mtime % nd / nh;// 计算差多少小时
            result = hour+"小时前";
        }else if(nd <= _mtime && _mtime < nd * 30){
            long day=_mtime/nd;
            result = day+"天前";
        }else if(nd * 30 <= _mtime && _mtime < nd * 30 * 12){
            long month=_mtime /(nd * 30);
            result = month+"个月前";
        }else if(_mtime >= nd * 30 * 12){
            long year=_mtime /(nd * 30 * 12);
            result = year+"年前";
        }else{
            result = "刚刚";
        }

        return result;
    }
    
    //TODO 注释
    public static long getQuot(String time1, String time2) {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return quot;
    }

    /**
     * 获取当天零点零分零秒的Long值
     */
    public static Long getTheDayTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STRING_FORMAT_DAY);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_DAY, sdf.format(new Date()));
    }

/*****************************获取未来N天****************************************/
    
    /**
     * 获取未来第N天
     * 
     * @param n
     * @return
     */
    public static Date getInnerDay(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);
        return calendar.getTime();
    }

    /**
     * 获取未来第N天零点零分零秒的Long值
     *
     * @param n
     * @return
     */
    public static Long getInnerDayTime(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取startTime未来N天零点零分零秒的Long值
     * 
     * @param standardTime
     * @param differenceDayNum
     * @return
     */
    public static Long getInnerDayTime(long standardTime, int differenceDayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(standardTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + differenceDayNum);
        return calendar.getTimeInMillis();
    }

    public static Long getInnerDayTime(String standardTime, int differenceDayNum) {
        if (StringUtil.isEmpty(standardTime)){
        	return null;
        }
        Long dateLong = convertDateStringToDateLong(DateUtil.DATE_STRING_FORMAT_DAY, standardTime);
        return getInnerDayTime(dateLong, differenceDayNum);
    }

/*****************************获取当前月第一天****************************************/
    /**
     * 获取当前月第一天 (自定义格式),返回String类型
     * 
     * @param format
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getFirtDayOfMonth(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return df.format(startCal.getTime());
    }

    /**
     * 获取当前月第一天 (yyyy-MM-dd),返回String类型
     */
    @SuppressWarnings("static-access")
    public static String getFirtDayOfMonth() {
        SimpleDateFormat df = new SimpleDateFormat(YEAR_MONTH_DATE);
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return df.format(startCal.getTime());
    }

    /**
     * 获得当前月第一天 (yyyy-MM-dd HH:mm:ss),返回String类型
     */
    @SuppressWarnings("static-access")
    public static String getFirtDayTimeOfMonth() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        String day_start = df.format(startCal.getTime());
        return day_start;
    }
    
    /**
     * 获取当前月第一天 (yyyy-MM-dd HH:mm:ss),返回Long类型
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static Long getFirstDayDateTimeOfMonth() {
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        Date date = (Date) startCal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(date));

    }


    /**
     * 获取当前月第一天 ,返回Date类型
     *
     * @return
     * @throws
     */
    @SuppressWarnings("static-access")
    public static Date getFirstDayOfMonth() {
        Calendar startCal = Calendar.getInstance();
        startCal.set(startCal.DATE, 1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        Date date = (Date) startCal.getTime().clone();
        return date;
    }

    /**
     * 获取一个月之前的时间 (yyyy-MM-dd HH:mm:ss),返回Long类型
     *
     * @return
     */
    public static Long getOneMonthBeforeDateTime() {
        Calendar startCal = Calendar.getInstance();
        startCal.add(Calendar.MONTH, -1);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        Date date = (Date) startCal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(date));
    }

/*****************************获取当前月最后一天****************************************/
    
    /**
     * 获得当前月最后一天 (自定义),返回String类型
     * 
     * @param format
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getLastDayOfMonth(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar endCal = Calendar.getInstance();
        endCal.add(endCal.MONTH, 1);
        endCal.set(endCal.DATE, 1);
        endCal.add(endCal.DATE, -1);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        String day_end = df.format(endCal.getTime());
        return day_end;
    }
    
    /**
     * 获得当前月最后一天 (yyyy-MM-dd),返回String类型
     */
    @SuppressWarnings("static-access")
    public static String getLastDayOfMonth() {
        SimpleDateFormat df = new SimpleDateFormat(YEAR_MONTH_DATE);
        Calendar endCal = Calendar.getInstance();
        endCal.add(endCal.MONTH, 1);
        endCal.set(endCal.DATE, 1);
        endCal.add(endCal.DATE, -1);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        String day_end = df.format(endCal.getTime());
        return day_end;
    }

    /**
     * 获得当前月上个月最后一天
     */
    public static Long getLastDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月份
//    cal.set(Calendar.MONTH, month - 1);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数
        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        return convertDateStringToDateLong("yyyy-MM-dd", (year + "-" + month + "-" + day));
    }

    /**
     * 获得当前月上个月第一天
     */
    public static Long getFirstDayOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月份
        int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH); // 开始天数
        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        return convertDateStringToDateLong("yyyy-MM-dd", (year + "-" + month + "-" + day));
    }

    /**
     * 获取当前月最后一天23时59分59秒,返回Long类型 
     *
     * @return
     * @throws
     */
    @SuppressWarnings("static-access")
    public static Long getLastDayDateTimeOfMonth() {
        Calendar endCal = Calendar.getInstance();
        endCal.add(endCal.MONTH, 1);
        endCal.set(endCal.DATE, 1);
        endCal.add(endCal.DATE, -1);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 59);
        Date date = (Date) endCal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(date));

    }


    /**
     * 获取当前月最后一天23时59分59秒,返回String类型 
     *
     * @return
     * @throws
     */
    @SuppressWarnings("static-access")
    public static String getLastDayTimeOfMonth() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        cal.set(cal.DATE, 1);
        cal.add(cal.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        String last_day = df.format(cal.getTime());
        return last_day;
    }

    /**
     * 获取指定月最后一天23时59分59秒
     * 
     * @param cal
     * @return
     */
    @SuppressWarnings("static-access")
    public static Long getLastDayOfMonth(Calendar cal) {
        cal.add(cal.MONTH, 1);
        cal.set(cal.DATE, 1);
        cal.add(cal.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTimeInMillis();

    }

    /**
     * 获取一年的最后一天 23时59分59秒
     * 
     * @return
     */
    public static String getEndOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Date date = (Date) calendar.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return format.format(date);
    }

    /**
     * 获取当年的开始 00时00分00秒
     * 
     * @return
     */
    public static String getStartOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = (Date) calendar.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return format.format(date);
    }

    /**
     * 获取当年的开始 00时00分00秒
     * 
     * @return
     */
    public static Long getStartOfYearOfLong() {
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, getStartOfYear());
    }

    public static Long convertDateDateShortToDateLong(String format, Date date) throws Exception {
        DateFormat df = new SimpleDateFormat(format);
        String dateStr = df.format(date);
        return convertDateStringToDateLong(format, dateStr);
    }

    public static Long getToday(String format, Date date) {
        DateFormat df = new SimpleDateFormat(format);
        return convertDateStringToDateLong(format, df.format(date));
    }

    public static String getNowTimeStr(String format) {
        return dateToWantStr(new Date(), format);
    }

    public static Date getDateFromString(String format, String dateString) {
        if (SysUtil.isEmpty(dateString)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            date = null;
            System.err.println(e.getMessage());
        }
        return date;
    }

    /**
     * 获取特定日期开始时间：00：00：00
     *
     * @return 特定日0时0分0秒
     * @throws
     */
    public static Long getStartTimeOfDate(String dateStr) {
        if (SysUtil.isEmpty(dateStr)) {
            return null;
        }
        Long dateLong = convertDateStringToDateLong(DateUtil.DATE_STRING_FORMAT_DAY, dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(dateLong));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startDate = (Date) cal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(startDate));
    }



    /**
     * 获取特定日期开始时间：00：00：00:000
     *
     * @return 特定日0时0分0秒0毫秒
     * @throws
     */
    public static Long getStartTimeOfDateStr(String dateStr, String formatStr) {
        if (SysUtil.isEmpty(dateStr)) {
            return null;
        }
        Long dateLong = convertDateStringToDateLong(formatStr, dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(dateLong));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDate = (Date) cal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(startDate));
    }

    //获取特定时间的开始毫秒 hh：mm：ss:0
    public static Long getStartTimeOfSecondStr(String dateStr, String formatStr) {
        if (SysUtil.isEmpty(dateStr)) {
            return null;
        }
        Long dateLong = convertDateStringToDateLong(formatStr, dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(dateLong));
        cal.set(Calendar.MILLISECOND, 0);
        Date startDate = (Date) cal.getTime().clone();
        return startDate.getTime();
    }

    /**
     * 获取特定日期结束时间：23：59：59:999
     *
     * @return 特定日23时59分59秒:999毫秒
     * @throws
     */
    public static Long getEndTimeOfDateStr(String dateStr, String formatStr) {
        if (SysUtil.isEmpty(dateStr)) {
            return null;
        }
        Long dateLong = convertDateStringToDateLong(formatStr, dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(dateLong));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date startDate = (Date) cal.getTime().clone();
        DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
        return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(startDate));
    }

    /**
     * 获取特定日期结束时间：hh：mm：ss:999
     *
     */
    public static Long getEndTimeOfSecondStr(String dateStr, String formatStr) {
        if (SysUtil.isEmpty(dateStr)) {
            return null;
        }
        Long dateLong = convertDateStringToDateLong(formatStr, dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(dateLong));
        cal.set(Calendar.MILLISECOND, 999);
        Date startDate = (Date) cal.getTime().clone();
        return startDate.getTime();
    }

    /**
     * 获取特定日期开始时间：00：00：00
     *
     * @return 特定日0时0分0秒
     * @throws
     */
    public static Date getStartTimeOfDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取特定日期23：59：59
     *
     * @return 特定日期23：59：59
     * @throws
     */
    public static Date getEndTimeOfDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Long getNextMonthTime(long currentTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(currentTime));
        cal.add(Calendar.MONTH, 1);
        return cal.getTime().getTime();
    }


    /**
     * 获取当前天开始时间：00：00：00
     *
     * @return 当前天0时0分0秒
     * @throws
     */
    public static Long getStartTimeOfToday() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        Date date = (Date) currentDate.getTime().clone();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return convertDateStringToDateLong("yyyy-MM-dd HH:mm:ss", format.format(date));
    }

    /**
     * 获取当前天23：59：59
     * 1.1 modify by qxy 当前天23：59：59.999  modifyTime 2013-11-13 15:59
     *
     * @return 当前天23：59：59.999
     * @throws
     */
    public static Long getEndTimeOfToday() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.MILLISECOND, 999);
        return currentDate.getTimeInMillis();
//    Date date = (Date) currentDate.getTime().clone();
//    DateFormat format = new SimpleDateFormat(DATE_STRING_FORMAT_ALL);
//    return convertDateStringToDateLong(DATE_STRING_FORMAT_ALL, format.format(date));
    }

    /**
     * 判断目标时间是否在今天
     *
     * @param targetDate
     * @return
     */
    public static Boolean isInToday(Long targetDate) {
        if (targetDate == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long startOfToday = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Long endOfToday = calendar.getTimeInMillis();
        if (targetDate >= startOfToday && targetDate <= endOfToday) {
            return true;
        }
        return false;
    }

    public static Boolean isInYesterday(Long targetDate) {
        if (targetDate == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Long startOfYesterday = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Long endOfYesterday = calendar.getTimeInMillis();
        if (targetDate >= startOfYesterday && targetDate <= endOfYesterday) {
            return true;
        }
        return false;
    }

    /**
     * 获取昨天：00：00：00
     *
     * @return 昨天：00：00：00
     * @throws
     */
    public static Long getStartTimeOfYesterday() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DAY_OF_YEAR, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        Date date = (Date) currentDate.getTime().clone();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return convertDateStringToDateLong("yyyy-MM-dd HH:mm:ss", format.format(date));
    }

    /**
     * 两个日期间的年月日差
     *
     * @param startDate
     * @param endDate
     * @return XX年XX月XX日
     */
    public static String dateDifference(Long startDate, Long endDate, boolean hideDayIfMonth) {
        if (endDate.compareTo(startDate) < 0) {   //保证endDate>startDate
            Long tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        Calendar calS = Calendar.getInstance();
        calS.setTimeInMillis(startDate);
        int startY = calS.get(Calendar.YEAR);
        int startM = calS.get(Calendar.MONTH);
        int startD = calS.get(Calendar.DATE);
        int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

        calS.setTimeInMillis(endDate);
        int endY = calS.get(Calendar.YEAR);
        int endM = calS.get(Calendar.MONTH);
        //处理2011-01-10到2011-01-10，认为服务为一天
        int endD = calS.get(Calendar.DATE) + 1;
        int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);

        StringBuilder sBuilder = new StringBuilder();

        int lday = endD - startD;
        if (lday < 0) {
            endM = endM - 1;
            lday = startDayOfMonth + lday;
        }
        //处理天数问题，如：2011-01-01 到 2013-12-31  2年11个月31天     实际上就是3年
        if (lday == endDayOfMonth) {
            endM = endM + 1;
            lday = 0;
        }
        int mos = (endY - startY) * 12 + (endM - startM);
        int lyear = mos / 12;
        int lmonth = mos % 12;
        if (lyear > 0) {
            sBuilder.append(lyear + "年");
        }
        if (lmonth > 0) {
            sBuilder.append(lmonth + "月");
        }
        if (!hideDayIfMonth) {
            if (lday > 0) {
                sBuilder.append(lday + "天");
            }
        } else {
            if (lyear == 0 && lmonth == 0) {
                sBuilder.append(lday + "天");
            }
        }
        return sBuilder.toString();
    }

    public static int fieldDifference(long time1, long time2, int field) {
        if (time1 == time2) {
            return 0;
        } else if (time1 > time2) {
            // 确保time1比time2小
            return fieldDifference(time2, time1, field);
        }
        // 下面清除不要参与比较的内容
        Calendar cal1 = Calendar.getInstance();
        cal1.setLenient(false);
        cal1.setTimeInMillis(time1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setLenient(false);
        cal2.setTimeInMillis(time2);
        for (int x = 0; x < Calendar.FIELD_COUNT; x++) {
            if (x > field) {
                cal1.clear(x);
                cal2.clear(x);
            }
        }
        // 重新设定初始值
        time1 = cal1.getTimeInMillis();
        time2 = cal2.getTimeInMillis();
        long ms = 0;
        int min = 0;// 下限,从0开始
        int max = 1;// 每次所加的值,第一次加1
        while (true) {
            // 因为max的值都是相对time1而言,故每次都是从time1开始而不是ms
            cal1.setTimeInMillis(time1);
            cal1.add(field, max);// 将field对应的字段加上max的值
            ms = cal1.getTimeInMillis();
            if (ms == time2) {
                // 两个时间之间相差的值为max
                min = max;
                break;
            } else if (ms > time2) {
                // 超过后,则差值肯定小于max
                break;
            } else {
                // 仍然小于time2,继续增大max,直到ms>=time2为止
                max <<= 1;
            }
        }
        // 上面的操作中没有找到准确的值,接下来使用二分查找以准确找出差值
        while (max > min) {
            cal1.setTimeInMillis(time1);
            int t = (min + max) >>> 1;
            cal1.add(field, t);
            ms = cal1.getTimeInMillis();
            if (ms == time2) {
                min = t;
                break;
            } else if (ms > time2) {
                max = t;
            } else {
                min = t;
            }
        }
        return min;
    }

    /**
     * 获取过期时间(转换成字符串显示：MM-YY-DD)
     * date为空的时候是根据当前时间算的
     * term为-1L的时候返回-1L(业务中便是无限期)
     *
     * @param term 传入的月份
     * @return
     */
    public static Long getDeadline(Long data, int term) throws Exception {
        if (-1 != term) {

            if (null == data) {
                data = System.currentTimeMillis();
            }
            if (data.longValue() < System.currentTimeMillis()) {
                data = System.currentTimeMillis();
            }
            String dateStr = DateUtil.dateLongToWantStr(data, DateUtil.YEAR_MONTH_DATE);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
            cal.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(5, 7)) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(8, 10)));
            cal.add(Calendar.MONTH, term);
            data = DateUtil.convertDateStringToDateLong(DateUtil.YEAR_MONTH_DATE,
                    DateUtil.dateLongToWantStr(cal.getTimeInMillis(),DateUtil.YEAR_MONTH_DATE));
            return data.longValue() + 86399000L;
        }

        return -1L;
    }

    public static String getTodayStr() {
        return getTodayStr(DateUtil.YEAR_MONTH_DATE);
    }

    public static String getTodayStr(String format) {
        Long date = System.currentTimeMillis();
        return DateUtil.dateLongToWantStr(date, format);
    }

    public static String getYesterdayStr() {
        return getYesterdayStr(DateUtil.YEAR_MONTH_DATE);
    }

    public static String getYesterdayStr(String format) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Long date = c.getTimeInMillis();
        return DateUtil.dateLongToWantStr(date, format);
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getYear(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    //判断是否是当前日期
    public static boolean isCurrentTime(Long date) throws Exception {
        if (date == null) {
            return false;
        }
        date = DateUtil.convertDateDateShortToDateLong(DATE_STRING_FORMAT_DAY, new Date(date));
        Long currentDate = DateUtil.convertDateDateShortToDateLong(DATE_STRING_FORMAT_DAY, new Date(System.currentTimeMillis()));
        return (currentDate - date == 0);
    }


    public static boolean isSameDay(Long currentDate, Long compareDate) {
        try {
            if (currentDate == null || compareDate == null) {
                return false;
            }

            currentDate = DateUtil.convertDateDateShortToDateLong(DateUtil.DATE_STRING_FORMAT_DAY, new Date(currentDate));
            compareDate = DateUtil.convertDateDateShortToDateLong(DateUtil.DATE_STRING_FORMAT_DAY, new Date(compareDate));
            return (currentDate - compareDate == 0);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }


    public static Long get6clock(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 标准格式,例如： 2012-04-10
     *
     * @param sDate
     * @return
     */
    public static boolean isStandardDateFormat(String sDate) {
        if (SysUtil.isEmpty(sDate)) {
            return false;
        }
        String importedDatePattern = "^[1-9]\\d{3}\\-(0?[1-9]|1[0-2])\\-(0?[1-9]|1\\d|2\\d|3[0-1])$";
        Pattern pattern = Pattern.compile(importedDatePattern);
        Matcher match = pattern.matcher(sDate);
        return match.matches();
    }


    public static Long getLastWeekTime(Calendar calendar) {
        calendar.add(Calendar.DATE, -7);
        return calendar.getTimeInMillis();
    }

    public static Long getLastMonthTime(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTimeInMillis();
    }

    public static Long getLastThreeMonthTime(Calendar calendar) {
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTimeInMillis();
    }

    public static Long getLastHalfYearTime(Calendar calendar) {
        calendar.add(Calendar.MONTH, -6);
        return calendar.getTimeInMillis();
    }

    public static Long getLastYearTime(Calendar calendar) {
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取指定日期开始时间：00：00：00
     *
     * @return 当前天0时0分0秒
     * @throws
     */
    public static Long getStartOfDate(Long vDate, String formatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(vDate));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = (Date) calendar.getTime().clone();
        DateFormat format = new SimpleDateFormat(formatStr);
        return convertDateStringToDateLong(formatStr, format.format(date));
    }

    /**
     * 获取指定日期结束时间23：59：59
     *
     * @return 当前天23：59：59
     * @throws
     */
    public static Long getEndOfDate(Long vDate, String formatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(vDate));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date date = (Date) calendar.getTime().clone();
        DateFormat format = new SimpleDateFormat(formatStr);
        return convertDateStringToDateLong(formatStr, format.format(date));
    }

    public static Long getEndOfDate(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime().getTime();
    }

    public static int getWeekOfYear(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前月的总天数
     *
     * @return
     */
    public static int getDayOfMonth() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }

    public static List<String> getDays(String startDate, String endDate) {
        return getDates(startDate, endDate, null, null);
    }

    public static List<String> getDates(String startDate, String endDate, String inFormat, String outFormat) {
        List days = new ArrayList();// 日期集合
        if (SysUtil.isEmpty(inFormat)) {
            inFormat = YEAR_MONTH_DATE;
        }
        if (SysUtil.isEmpty(outFormat)) {
            outFormat = YEAR_MONTH_DATE;
        }
        long end = convertDateStringToDateLong(inFormat, endDate);
        long begin = convertDateStringToDateLong(inFormat, startDate);
        while (begin <= end) {
            Date day = new Date();
            day.setTime(begin);
            begin += 3600 * 24 * 1000;
            days.add(dateToWantStr(day, outFormat));
        }
        return days;
    }

    public static String getHourTime(int dayOffset, int hour) {
        Calendar cal = Calendar.getInstance();
        if (dayOffset != 0)
            cal.add(Calendar.DATE, dayOffset);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获得当前月最后N天 (date)
     */
    @SuppressWarnings("static-access")
    public static Date getLastDaysOfMonth(int n) {
        Calendar endCal = Calendar.getInstance();
        endCal.add(endCal.MONTH, 1);
        endCal.set(endCal.DATE, 1);
        endCal.add(endCal.DATE, -n);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        return endCal.getTime();
    }

    /**
     * 时间毫秒数转多少天多少时多少分多少秒
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天" + hours + "小时" + minutes + "分钟"
                + seconds + "秒";
    }

    //获取特定日期
    public static Long pointFutureDay(Date date ,String formatStr,String cat,Integer num) {
//        System.out.println((new SimpleDateFormat(formatStr)).format(date));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if("month".equals(cat)) {
            cal.add(Calendar.MONTH, 1);
        }
        if("year".equals(cat)) {
            cal.add(Calendar.YEAR, 1);
        }
//      return (new SimpleDateFormat(formatStr)).format(cal.getTime());
        return cal.getTime().getTime();
    }

/*    public static void main(String [] args) {

        System.out.println(pointFutureDay(new Date(), DateUtil.DATE_STRING_FORMAT_DEFAULT, "year", 1));
        System.out.println(dateLongToStr(pointFutureDay(new Date(), DateUtil.DATE_STRING_FORMAT_DEFAULT, "year", 1), DateUtil.DATE_STRING_FORMAT_DEFAULT));


//        System.out.println(dateToStr(new Date()));
//        System.out.println(getEndTimeOfSecondStr(dateToStr(new Date()), DateUtil.DATE_STRING_FORMAT_DEFAULT));
//        System.out.println(dateLongToStr(getEndTimeOfSecondStr(dateToStr(new Date()), DateUtil.DATE_STRING_FORMAT_DEFAULT),DateUtil.DATE_STRING_FORMAT_DEFAULT));


    }*/
}
