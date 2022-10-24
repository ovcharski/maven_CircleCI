package helperClasses;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static helperClasses.UtilityMethods.logError;
import static helperClasses.UtilityMethods.logStringIntoConsole;


public class DateUtil {
    public static int compareTwoDates(String first, String second) {
        long compare = -1;
        logStringIntoConsole("First: " + first);
        logStringIntoConsole("Second: " + second);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");

        try {
            Date dateStart = simpleDateFormat.parse(first);
            logStringIntoConsole("Start Date: " + dateStart.toString());
            Date dateEnd = simpleDateFormat.parse(second);
            logStringIntoConsole("End Date: " + dateEnd.toString());

            compare = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
            logStringIntoConsole("Compare: " + compare);
        } catch (Exception e) {
            logError(e.toString());
        }
    	/*DateTimeFormatter format = DateTimeFormat.forPattern("M/d/yyyy");

    	DateTime dtOne = format.parseDateTime(first);
    	logStringIntoConsole("dtOne: " + dtOne.toString());
    	DateTime dtTwo = format.parseDateTime(second);
		logStringIntoConsole("dtTwo: " + dtTwo.toString());

    	LocalDate firstDate = dtOne.toLocalDate();
    	LocalDate secondDate = dtTwo.toLocalDate();

    	compare = secondDate.compareTo(firstDate);*/

        int result = Integer.parseInt(Long.toString(compare));
        logStringIntoConsole("Days Comparison: " + result);

        return result;
    }

    public static String getYYYY() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    private static int randomInt;

    public static Date getCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        return calendar.getTime();
    }

    /**
     * Return the system date in GregorianCalendar Format
     * @return Calendar
     */
    private static Calendar getCurrentCalendar() {
        return new GregorianCalendar();
    }

    /**
     * Returns the system time in milliseconds
     * @return timeInMilliseconds
     */
    public static long getTimeInMillisOFCurrentCalender(){
        long timeInMillis = getCurrentCalendar().getTimeInMillis();
        return timeInMillis;
    }

    /**
     * Format current date to the "MM/dd/yyyy" format
     * @return date in "MM/dd/yyyy" format
     */
    public static String getMMDDYYYY() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getCurrentDateFourDigitYear() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("M/d/yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getCurrentFourDigitYear() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getMMYYYY() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getCurrentDateMonthDayShorthand() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("M/d");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    /**
     * Format current date to shorthand - 'M/d/yy' - and return it.
     *
     * @return Date in 'M/d/yy' format.
     */
    public static String getMDYY() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("M/d/yy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getCurrentDayOfMonth(){
        String currentDate = getMMDDYYYY();
        System.out.println(currentDate);
        String dayOfMonth = currentDate.substring(8);
        System.out.println(dayOfMonth);
        return dayOfMonth;
    }

    public static String getFormattedCurrentDateFull(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getFormattedCurrentDateTime (){
        SimpleDateFormat timeFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a ");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        return time;
    }

    public static String getDateTimeAndTimeZone() {
        //20191002T140630.000 GMT

        SimpleDateFormat cstCdtFormat=new SimpleDateFormat("yyyyMMddTHHmmss");
        cstCdtFormat.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
        return cstCdtFormat.format(new Date());
    }
    public static String getCurrentDateFormatted(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMMM dd, yyyy");
        timeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
        String time = timeFormat.format(new Date());
        System.out.println(time);
        return time;
    }

    /**
     * Description - Method which returns a string date value of 'MM/DD/YYYY' based on an
     * input parameter of how many days from current date user desires.
     *
     * Author - Oleg Andreyev
     *
     * @param numberOfDays
     * @return
     */
    public static String someDaysInFuture(int numberOfDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, numberOfDays); // Adding some number of days specified in parameter
        String output = sdf.format(c.getTime());
        return output;
    }

    public static String someDaysInPast(int numberOfDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, -numberOfDays); //Subtracting some number of days specified in parameter
        String output = sdf.format(c.getTime());
        return output;
    }

    public static String someDaysInFutureIncludeTime(int numberOfDays) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, numberOfDays); // Adding some number of days specified in parameter
        String output = sdf.format(c.getTime());
        return output;
    }
}
