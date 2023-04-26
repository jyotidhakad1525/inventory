package com.dms.inventory.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.rmi.dgc.VMID;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final String EMPTY_STRING = "";
    public static final String UNDERSCORE = "_";
    /**
     * The Constant DECIMALLIMIT.
     */
    public static final int DECIMALLIMIT = 2;
    private static final ResourceBundle errorMsgBundle = null;
    private static final String FINANCIALYEAR = "FinancialYear";
    /*
     *
     * This is specific property file for eApp only.
     */
    private static final String HOURLY_FORMAT = "H:mm";
    private static final String TOTAL_HOURS_FORMAT = "K:mm";
    private static final String PM = "PM";
    private static final String AM = "AM";
    static Logger log = LoggerFactory.getLogger(Utils.class);
    /**
     * The sdf.
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    /**
     * The sdf1.
     */
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    private Utils() {
    }

    /**
     * Utility method to check if a string is empty.
     *
     * @param str Input string which has to be checked.
     * @return true, if checks if is empty
     */
    public static boolean isEmpty(String str) {
        return str == null || (str.trim().length() == 0);
    }

    /**
     * Utility method to check if a string is not empty.
     *
     * @param str Input string which has to be checked.
     * @return true, if checks if is not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * This method checks if the given map is NULL or Empty
     *
     * @param map instance of <code>java.util.Map</code>
     * @return true if this map is either NULL or contains no key-value
     * mappings.
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * This method checks if the given map is NOT NULL and NOT Empty
     *
     * @param map instance of <code>java.util.Map</code>
     * @return true if this map is NOT NULL and contains key-value mappings.
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * using hashCode to reduce the length of the string.
     *
     * @param inPrefix the in prefix
     * @return String
     */
    public static String generateTransactionId() {
        VMID vmid = new VMID();
        return vmid.toString();
    }

    /**
     * Format date.
     *
     * @param date       the date
     * @param fromFormat the from format
     * @param toFormat   the to format
     * @return the string
     */
    public static String formatDate(String date, String fromFormat, String toFormat) {
        Date parsedDt = formatStringToDate(date, fromFormat);
        return formatDateToString(toFormat, parsedDt);
    }

    public static String formatDateToString(String toFormat, Date parsedDt) {
        SimpleDateFormat sdf = new SimpleDateFormat(toFormat);
        return sdf.format(parsedDt);
    }

    public static Date formatStringToDate(String date, String fromFormat) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(fromFormat);
        sdf1.setLenient(false);
        Date parsedDt;
        try {
            parsedDt = sdf1.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return parsedDt;
    }

    /**
     * Checks if the given objArr is null or size 0
     *
     * @param byteArr array of Byte.
     * @return true if the array is null or size 0.
     */
    public static boolean isEmpty(byte[] byteArr) {
        return (byteArr == null) || (byteArr.length < 1);
    }

    /**
     * Checks if the given objArr is not null and size not 0
     *
     * @param byteArr array of Byte.
     * @return true if the array is not null and size 0.
     */
    public static boolean isNotEmpty(byte[] byteArr) {
        return !isEmpty(byteArr);
    }

    /**
     * Checks if the given objArr is null or size 0
     *
     * @param objArr array of Object.
     * @return true if the array is null or size 0.
     */
    public static boolean isEmpty(Object[] objArr) {
        return (objArr == null) || (objArr.length < 1);
    }

    /**
     * Checks if the given objArr is not null and size not 0
     *
     * @param objArr array of Object.
     * @return true if the array is not null and size 0.
     */
    public static boolean isNotEmpty(Object[] objArr) {
        return !isEmpty(objArr);
    }

    /**
     * This method checks if the given list is null or is empty.
     *
     * @param listObj instance of java.util.List
     * @return true if list is null or size == 0
     */
    public static boolean isEmpty(List<?> listObj) {
        return listObj == null || listObj.isEmpty();
    }

    /**
     * This returns true is the given list is not null or not empty.
     *
     * @param listObj instance of java.util.List
     * @return boolean value.
     */
    public static boolean isNotEmpty(List<?> listObj) {
        return !isEmpty(listObj);
    }

    /**
     * Formats the given string if it is null.
     *
     * @param string input.
     * @return either given string or empty string if null.
     */
    public static String getValidString(String string) {
        if (isNotEmpty(string)) {
            return string.trim();
        } else {
            return EMPTY_STRING;
        }
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * This method checks if the given collection is NOT NULL and NOT Empty
     *
     * @param collection instance of <code>java.util.Collection</code>
     * @return true if this set is NOT NULL.
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Utility method to parse the given string.
     *
     * @param value input string, a number in string format.
     * @return an int value for the given string value.
     */
    public static int parseInt(String value) {
        int nRet = 0;
        String temp = null;

        if (isEmpty(value)) {
            return nRet;
        }
        temp = value.trim();
        try {
            nRet = Integer.parseInt(temp);
        } catch (NumberFormatException nEx) {
            log.error("Could not convert String to Integer : " + value, nEx);
        }
        return nRet;
    }

    /**
     * Gets the property value from the resource bundle. Logs a message if error
     * in reading resource.
     *
     * @param res resource bundle.
     * @param key search key.
     * @return searched value based on key provided.
     */
    public static String getStringFromBundle(ResourceBundle res, String key) {
        if (res == null || Utils.isEmpty(key)) {
            return EMPTY_STRING;
        }
        String value = res.getString(key);

        if (Utils.isEmpty(value)) {
            return EMPTY_STRING;
        }
        return value.trim();
    }

    /**
     * Calls getStringFromBundle and assigns default value if the returned value
     * is empty.
     *
     * @param res          resource bundle.
     * @param key          Search Key.
     * @param defaultValue sets the defaultValue if it doesn't find the property for the
     *                     given key.
     * @return the property value for the given key provided.
     */
    public static String getStringFromBundle(ResourceBundle res, String key, String defaultValue) {
        String value = getStringFromBundle(res, key);
        if (isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetweenDates(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        // calendar1.set( date1.getYear(), date1.getMonth(), date1.getDate(), 0,
        // 0, 0 );
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        // calendar2.set( date2.getYear(), date2.getMonth(), date2.getDate(), 0,
        // 0, 0 );
        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        return diffDays;
    }

    //We can use above method also but this one is short and better
    public static int diffBetweenDateInDays(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        //long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        //long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        //long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        return (int) diffInDays;
    }

    public static long diffBetweenDateInSeconds(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        //long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        //long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        //  long diffInDays =TimeUnit.MILLISECONDS.toDays(duration);
        return diffInSeconds;
    }

    //get date for given past or previous number of days
    public static Date getDate(int count) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, count);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(formatted);

        return date;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static int getDiffYears(Date firstDate, Date lastDate) {
        Calendar a = getCalendar(firstDate);
        Calendar b = getCalendar(lastDate);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        /*
         * if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
         * (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) &&
         * a.get(Calendar.DATE) > b.get(Calendar.DATE))) { diff--; }
         */
        return diff;
    }

    public static String getAmPmTime(String time) {
        String timeString = EMPTY_STRING;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(HOURLY_FORMAT);
            final Date dateObj = sdf.parse(time);

            timeString = new SimpleDateFormat(TOTAL_HOURS_FORMAT).format(dateObj);
            String susbStringOfTime = time.substring(0, 2);
            int exactTime = new Integer(susbStringOfTime);
            if (exactTime >= 12) {
                timeString = timeString + " " + PM;
            } else if (exactTime < 12) {
                timeString = timeString + " " + AM;
            }
        } catch (final ParseException e) {
            log.error(e.getMessage(), e);
        }
        return timeString;
    }

    /**
     * Returns a trimmed String if object is not null.
     *
     * @param object the object
     * @return either given string or empty string if null.
     */
    public static String trim(Object object) {
        if (object != null) {
            return object.toString().trim();
        } else {
            return "";
        }
    }

    public static String getFormattedCurrentDate(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new java.util.Date());
    }

    public static String UUID(String... inputParams) {

        if (Utils.isEmpty(inputParams)) {
            return UUID();
        }
        String join = String.join("", inputParams);

        return String.join("", join, UUID());
    }

    public static String UUID() {
        return getFormattedCurrentDate("ddMMyyyyhhmmss");
    }


    public static Locale getLocale(String localeVal) {
        Locale locale = null;
        if (Utils.isNotEmpty(localeVal)) {
            String[] localePropeties = localeVal.split(UNDERSCORE);

            if (localePropeties.length == 1) {
                locale = new Locale(localePropeties[0]);
            } else if (localePropeties.length == 2) {
                locale = new Locale(localePropeties[0], localePropeties[1]);
            } else if (localePropeties.length == 3) {
                locale = new Locale(localePropeties[0], localePropeties[1], localePropeties[2]);
            } else {
                locale = new Locale("en");
            }
        }
        return locale;
    }

    /**
     * Sql date2 string with time.
     *
     * @param date the date
     * @return the string
     */
    public static String sqlDate2StringWithTime(Date date) {
        synchronized (sdf1) {
            return (date != null) ? sdf1.format(date) : "";
        }
    }

    /**
     * Sql date time2 string.
     *
     * @param date the date
     * @return the string
     */
    public static String sqlDateTime2String(Date date) {
        synchronized (sdf) {
            return (date != null) ? sdf.format(date) : "";
        }
    }

    /**
     * String2 sql date with time.
     *
     * @param dateString the date string
     * @return the date
     */
    public static Date string2SqlDateWithTime(String dateString) {
        try {
            synchronized (sdf1) {
                return Utils.isNotEmpty(dateString) ? new Date(sdf1.parse(dateString).getTime()) : null;
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Sql date2 string.
     *
     * @param date the date
     * @return the string
     */
    public static String sqlDate2String(Date date) {
        synchronized (sdf) {
            return (date != null) ? sdf.format(date) : "";
        }
    }

    /**
     * Parses the double.
     *
     * @param value the value
     * @return the double
     */
    public static double parseDouble(String value) {
        double ret = 0.0;
        String temp = null;

        if (Utils.isEmpty(value))
            return ret;
        temp = value.trim();
        try {
            ret = new Double(temp).doubleValue();
        } catch (NumberFormatException nEx) {
            return -999;
        }
        return ret;
    }

    /**
     * @param numStr
     * @return
     */
    public static BigDecimal getPin(String numStr) {
        if (numStr.isEmpty()) {
            return null;
        } else {
            try {
                return new BigDecimal(numStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    public static boolean isNumeric(String numStr) {
        if (isEmpty(numStr)) {
            return true;
        } else {
            try {
                Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }

    public static boolean isDouble(String numStr) {
        if (isEmpty(numStr)) {
            return false;
        } else {
            try {
                Double.parseDouble(numStr);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static <T> String ObjectToJson(T t) {
        return ObjectToJson(t, false);
    }


    public static <T> String ObjectToJson(T t, boolean isPrety) {

        if (t == null) {
            return null;
        }
        String jsonData = null;
        ObjectMapper objMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
//		objMapper.setSerializationInclusion(Include.NON_NULL);
        try {
            jsonData = isPrety ? objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t) :
                    objMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e);
        }
        log.info("{} jsonData : {} ", t.getClass().getSimpleName(), jsonData);
        return jsonData;
    }

    @SuppressWarnings({"unchecked", "hiding"})
    public static <T> T jsonToObject(String json, T t) {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            t = (T) objMapper.readValue(json, t.getClass());
        } catch (JsonProcessingException e) {
            log.error("" + e);
            return null;
        } catch (IOException e) {
            log.error("IOException", e);
            return null;
        }
        log.debug(t.getClass().getSimpleName());
        return t;
    }


    @SuppressWarnings({"hiding"})
    public static List<?> jsonToList(String json, List<?> t) {
        ObjectMapper objMapper = new ObjectMapper();
        try {

            t = objMapper.readValue(json, t.getClass());

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            log.error("" + e);
            return null;
        } catch (IOException e) {
            log.error("IOException", e);
            return null;
        }
        log.debug(t.getClass().getSimpleName());
        return t;
    }

    public static String StringToIndianCurrency(BigDecimal amount) {
        if (null != amount && !"0.00".equals(amount.toString()) && !"0.0".equals(amount.toString())) {
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            // we never reach double digit grouping so return
            if (amount.doubleValue() < 100000) {
                return "Rs. " + formatter.format(amount.doubleValue());
            }
            StringBuilder returnValue = new StringBuilder();
            // Spliting integer part and decimal part
            String value = amount.toString();
            if (!value.contains(".")) {
                value = amount.toString() + ".00";
            }
            String intpart = value.substring(0, value.indexOf("."));
            String decimalpart = value.substring(value.indexOf("."));
            // switch to double digit grouping
            formatter.applyPattern("#,##");
            returnValue.append(formatter.format(new BigDecimal(intpart).doubleValue() / 1000)).append(",");
            // appending last 3 digits and decimal part
            returnValue.append(intpart.substring(intpart.length() - 3)).append(decimalpart);
            // returning complete string
            return returnValue.toString();
        } else {
            return "0.00";
        }
    }

    /**
     * @return the customMessage
     */
    public static String getCustomMessage(String customErrorCode) {

        return errorMsgBundle.getString(customErrorCode);
    }

    public static Date getStartDate(String yearType, int noOfYears) {
        LocalDate localeDate = null;
        Date date = null;
        LocalDate currentTime = LocalDate.now();
        if (FINANCIALYEAR.equals(yearType)) {
            if (currentTime.getMonth().getValue() <= 3) {
                localeDate = currentTime.withYear(currentTime.getYear() - noOfYears).withMonth(4).withDayOfMonth(1);
            } else {
                localeDate = currentTime.withYear(currentTime.getYear() - noOfYears + 1).withMonth(4).withDayOfMonth(1);
            }
        } else {
            localeDate = currentTime.withYear(currentTime.getYear() - noOfYears + 1).withMonth(1).withDayOfMonth(1);
        }

        date = Date.from(localeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Date getEndDate(String yearType) {
        LocalDate localeDate = null;
        Date date = null;
        LocalDate currentTime = LocalDate.now();
        if (FINANCIALYEAR.equals(yearType)) {
            if (currentTime.getMonth().getValue() <= 3) {
                localeDate = currentTime.withYear(currentTime.getYear()).withMonth(3).withDayOfMonth(31);
            } else {
                localeDate = currentTime.withYear(currentTime.getYear() + 1).withMonth(3).withDayOfMonth(31);
            }
        } else {
            localeDate = currentTime.withYear(currentTime.getYear()).withMonth(12).withDayOfMonth(31);
        }

        date = Date.from(localeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Date getStartDate(String yearType, String year) {
        LocalDate localeDate = null;
        Date date = null;
        LocalDate currentTime = LocalDate.now();
        if (FINANCIALYEAR.equals(yearType)) {
            localeDate = currentTime.withYear(new Integer(year)).withMonth(4).withDayOfMonth(1);
        } else {
            localeDate = currentTime.withYear(new Integer(year)).withMonth(1).withDayOfMonth(1);
        }

        date = Date.from(localeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static Date getEndDate(String yearType, String year) {
        LocalDate localeDate = null;
        Date date = null;
        LocalDate currentTime = LocalDate.now();
        if (FINANCIALYEAR.equals(yearType)) {
            if (currentTime.getMonth().getValue() <= 3) {
                localeDate = currentTime.withYear(new Integer(year)).withMonth(3).withDayOfMonth(31);
            } else {
                localeDate = currentTime.withYear(new Integer(year) + 1).withMonth(3).withDayOfMonth(31);
            }
        } else {
            localeDate = currentTime.withYear(new Integer(year)).withMonth(12).withDayOfMonth(31);
        }

        date = Date.from(localeDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }


    /**
     * @param policies
     * @return
     */
    public static String getStringWithDelimeter(List<String> list, String delimeter) {
        return String.join(delimeter, list);
    }


    public static double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String decimalFormat(double value, int places) {
        return String.format("%." + places + "f", value);
    }

    public static String decimalFormat(BigDecimal value, int places) {
        return String.format("%." + places + "f", value);
    }

    public static String generateCaptchaTextMethod1() {
        int rl = new SecureRandom().nextInt(); // Random numbers are generated.
        return Integer.toHexString(rl);
    }

    public static String generateCaptchaTextMethod2(int captchaLength) {

        String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer captchaStrBuffer = new StringBuffer();
        // build a random captchaLength chars salt
        while (captchaStrBuffer.length() < captchaLength) {
            int index = (int) (new SecureRandom().nextFloat() * saltChars.length());
            captchaStrBuffer.append(saltChars, index, index + 1);
        }

        return captchaStrBuffer.toString();

    }


    public static BigDecimal rounUpOneRs(BigDecimal val) {
        val = val.setScale(2, BigDecimal.ROUND_HALF_UP);
        // Rounding >49 take as +1, <=49 taken as +0
        val = val.setScale(0, BigDecimal.ROUND_HALF_UP);

        return val;
    }


    public static BigDecimal rounding(BigDecimal val) {
        return rounding(val, 2);
    }

    public static BigDecimal rounding(BigDecimal val, int position) {

        return val.setScale(position, BigDecimal.ROUND_HALF_UP);
    }

    public static <T> boolean isNotEmpty(T t) {
        // TODO Auto-generated method stub
        //return false;
        return !isEmpty(t);
    }

    public static <T> boolean isEmpty(T t) {
        // TODO Auto-generated method stub
        //return false;
        return t == null;
    }

    public static String leftPadding(String str, int charcount, String replace) {
        if (Utils.isEmpty(str)) {
            str = "";
        }

        if (str.contains(".")) {
            str = str.replace(".", "");
        }
        return StringUtils.leftPad(str, charcount, replace);
    }

    public static String rightPadding(String str, int charcount, String replace) {
        if (str == null) {
            str = "";
        }

        //added for removing \" for double quote
        if (str.contains("\"")) {
            str = str.replace("\"", "");
        }

        int length = str.length();
        if (charcount < length) {
            str = str.substring(0, charcount);
        }

        return StringUtils.rightPad(str, charcount, replace);
    }

    public static String rightPad(String str, int charcount, String replace) {
        if (str == null) {
            str = "";
        }
        return StringUtils.rightPad(str, charcount, replace);
    }


    public static String getExpByRemovingDelimiter(String expression, String delimeter) {
        if (expression == null || delimeter == null) {
            return null;
        }
        return expression.replace(delimeter, "");
    }


    /**
     * @param t
     * @return
     */
    public static <T> String convertListToString(List<T> t) {
        if (Utils.isEmpty(t)) {
            return "";
        }
        String data = t.toString();
        data = data.replaceAll("\\[", "");
        data = data.replaceAll("\\]", "");
        data = data.replaceAll("\\, ", "");
        return data;
    }

    public static String format(String pattern, Object... args) {
        return MessageFormat.format(pattern, args);
    }

    public static String formatData(String key, String bundle, Object... args) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle);
        return MessageFormat.format(resourceBundle.getString(key), args);
    }

    /**
     * @param str
     * @return
     */
    public static BigDecimal StringToBigdecimal(String str) {
        return StringToBigdecimal(str, 2);
    }

    /**
     * @param str
     * @param precision
     * @return
     */
    public static BigDecimal StringToBigdecimal(String str, int precision) {

        int index = str.length() - precision;
        String decimal = str.substring(0, index);

        String floting = str.substring(index);
        String val = decimal + "." + floting;
        return new BigDecimal(val);
    }

    /**
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String formatStr(String str, int beginIndex, int endIndex) {

        int length = str.length();
        if (length >= beginIndex && length >= endIndex) {

            return str.substring(beginIndex, endIndex);
        }

        return null;

    }

    /**
     * @param itemJSON
     * @return
     *//*
	public static synchronized JSONObject stringToJson(String itemJSON) {
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(itemJSON);
		} catch (org.json.simple.parser.ParseException e) {
		}
		return json;
	}*/

    /**
     * @return
     */
    public static String replaceLeadingZeros(String str, String replacingValue) {

        if (Utils.isEmpty(str)) {
            return EMPTY_STRING;
        }
        return str.replaceFirst("^0+(?!$)", replacingValue);

    }

    @SuppressWarnings("unchecked")
    public static <T> T constructSuccessResponse(T t) {
        BaseResponse baseResponse = SuccessResponse();
        if (Utils.isEmpty(t)) {
            return (T) baseResponse;
        }
        BeanUtils.copyProperties(baseResponse, t);
        return t;
    }

    /**
     * @return
     */
    public static BaseResponse SuccessResponse() {
        BaseResponse baseResponse = new BaseResponse();
        ErrorMessages success = ErrorMessages.SUCCESS;
        baseResponse.setStatus(success.message());
        baseResponse.setStatusCode(success.code());
        baseResponse.setStatusDescription(success.message());
        return baseResponse;
    }

    public static BaseResponse SuccessResponse(String message) {
        BaseResponse baseResponse = new BaseResponse();
        ErrorMessages success = ErrorMessages.SUCCESS;
        baseResponse.setStatus(success.name());
        baseResponse.setStatusCode(success.code());
        baseResponse.setStatusDescription(message);
        return baseResponse;
    }
    
    public static BaseResponse failureResponse(String message) {
        BaseResponse baseResponse = new BaseResponse();
        ErrorMessages failure = ErrorMessages.FAILURE;
        baseResponse.setStatus(failure.name());
        baseResponse.setStatusCode(failure.code());
        baseResponse.setStatusDescription(message);
        return baseResponse;
    }

    public static <T> boolean checkMandetoryFieldsInclude(T t, List<String> fields, boolean isThrow) {
        return checkMandetoryFields(t, fields, "INCLUDE", isThrow);
    }

    public static <T> boolean checkMandetoryFieldsExclude(T t, List<String> fields, boolean isThrow) {
        return checkMandetoryFields(t, fields, "EXCLUDE", isThrow);
    }

    private static <T> boolean checkMandetoryFields(T t, List<String> fields, String type, boolean isThrow) {

        Class<? extends Object> clz = t.getClass();
        Field[] declaredFields = clz.getDeclaredFields();
        List<String> missingFields = new ArrayList<>();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                String prop = field.getName();
                Object object = field.get(t);
                if (isEmpty(object) && fields.contains(prop) && !type.equalsIgnoreCase("EXCLUDE")) {
                    missingFields.add(prop);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isEmpty(missingFields)) {
            return true;
        } else if (isThrow) {
            String fildsData = String.join("|", missingFields);
            throw new BaseException(ErrorMessages.SOURCE_INPUT_REQUIRE, null, fildsData);
        } else {
            return false;
        }
    }

    public static Timestamp timeStamp() {
        return new Timestamp(new Date().getTime());
    }


    public static <S, T> List<T> copyListtoList(List<S> sourcelist, Class<T> clz) {

        List<T> targetList = new ArrayList<T>();
        try {
            for (S source : sourcelist) {
                T t = clz.newInstance();
                BeanUtils.copyProperties(source, t);
                targetList.add(t);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetList;
    }

    public static String generateVehicleId(String... inputParams) {
        String vehicleId = null;

        int maxLength = 15;
        if (inputParams.length >= 2) {
            String orgId = inputParams[0];
            String showroomId = inputParams[1];
            String count = Integer.valueOf(inputParams[2]) + 1 + "";
            StringBuilder sb = new StringBuilder(orgId);
            sb.append(showroomId);
            int length = maxLength - sb.length();
            sb.append(Utils.leftPadding(count, length, "0"));
            vehicleId = sb.toString();
            log.info("Vehicle Id : {}", vehicleId);
        }

        return vehicleId;

    }


}
