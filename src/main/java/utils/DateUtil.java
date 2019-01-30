package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class DateUtil {
    /**
     * 格式化英文时间 Tue Jan 15 18:54:10 CST 2019
     * @param date
     * @return
     */
    public static String formatCSTDate(Date date) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf2.format(sdf1.parse(String.valueOf(date)));

        return format;
    }

    /**
     * 根据有效期开始时间和规则计算结束时间
     * @param beginDate 开始时间
     * @param lastedTimeType 过期时长类型：0.日，1.月，2.年
     * @param lastedTime 时长
     * @return
     */
    public static Date calculateEndDate(Date beginDate,Integer lastedTimeType,Integer lastedTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        switch (lastedTimeType){
            case 0:
                calendar.add(Calendar.DAY_OF_YEAR,lastedTime);
                break;
            case 1:
                calendar.add(Calendar.MONTH,lastedTime);
                break;
            case 2:
                calendar.add(Calendar.YEAR,lastedTime);
                break;
            default:
        }
        return calendar.getTime();
    }

    /**
     * 设置指定时间
     * @param hour
     * @param minute
     * @param second
     * @param addDay
     * @param date
     * @return
     */
    public static Date getNeedTime(int hour, int minute, int second, int addDay, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (addDay != 0) {
            calendar.add(Calendar.DATE, addDay);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
//        if (args.length == 1) {
//            calendar.set(Calendar.MILLISECOND, args[0]);
//        }
        return calendar.getTime();
    }
}
