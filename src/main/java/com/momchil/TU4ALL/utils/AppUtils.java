package com.momchil.TU4ALL.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AppUtils {

    static Logger logger = LoggerFactory.getLogger(AppUtils.class);
    // HMAC encryption algorithm.
    private static final String HMAC_SHA_256 = "HmacSHA256";

    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat timeFormatter2 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat timeFormatterHour = new SimpleDateFormat("HH");
    private static SimpleDateFormat dateFormat_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatter_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormatterDay = new SimpleDateFormat("dd");
    private static SimpleDateFormat dateFormatterYear = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat dateFormatterBg = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("en"));
    private static SimpleDateFormat dateFormatterBgWithHourNoSpace = new SimpleDateFormat("EEEE, dd MMMM yyyy г. HH:mm", new Locale("bg"));
    private static SimpleDateFormat dateFormatterShortBg = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat dateFormatterTimestampDB2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH);
    private static SimpleDateFormat dateFormatterTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.ENGLISH);
    private static SimpleDateFormat dateFormatterEPAY = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
    private static SimpleDateFormat dateFormatterBORICA = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("bg"));
    private static SimpleDateFormat dateFormatterDSKReporting = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private static DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
    private static DecimalFormat twoPointsPrecision = new DecimalFormat("###,###.00");
    private static Hashtable transcriptionTable = null;

    static {
        transcriptionTable = new Hashtable();
        transcriptionTable.put("А", "A");
        transcriptionTable.put("Б", "B");
        transcriptionTable.put("В", "V");
        transcriptionTable.put("Г", "G");
        transcriptionTable.put("Д", "D");
        transcriptionTable.put("Е", "E");
        transcriptionTable.put("Ж", "J");
        transcriptionTable.put("З", "Z");
        transcriptionTable.put("И", "I");
        transcriptionTable.put("Й", "I");
        transcriptionTable.put("К", "K");
        transcriptionTable.put("Л", "L");
        transcriptionTable.put("М", "M");
        transcriptionTable.put("Н", "N");
        transcriptionTable.put("О", "O");
        transcriptionTable.put("П", "P");
        transcriptionTable.put("Р", "R");
        transcriptionTable.put("С", "S");
        transcriptionTable.put("Т", "T");
        transcriptionTable.put("У", "U");
        transcriptionTable.put("Ф", "F");
        transcriptionTable.put("Х", "H");
        transcriptionTable.put("Ц", "C");
        transcriptionTable.put("Ч", "CH");
        transcriptionTable.put("Ш", "SH");
        transcriptionTable.put("Щ", "SHT");
        transcriptionTable.put("Ъ", "A");
        transcriptionTable.put("Ь", "I");
        transcriptionTable.put("Ю", "IU");
        transcriptionTable.put("Я", "IA");
        transcriptionTable.put("а", "a");
        transcriptionTable.put("б", "b");
        transcriptionTable.put("в", "v");
        transcriptionTable.put("г", "g");
        transcriptionTable.put("д", "d");
        transcriptionTable.put("е", "e");
        transcriptionTable.put("ж", "j");
        transcriptionTable.put("з", "z");
        transcriptionTable.put("и", "i");
        transcriptionTable.put("й", "i");
        transcriptionTable.put("к", "k");
        transcriptionTable.put("л", "l");
        transcriptionTable.put("м", "m");
        transcriptionTable.put("н", "n");
        transcriptionTable.put("о", "o");
        transcriptionTable.put("п", "p");
        transcriptionTable.put("р", "r");
        transcriptionTable.put("с", "s");
        transcriptionTable.put("т", "t");
        transcriptionTable.put("у", "u");
        transcriptionTable.put("ф", "f");
        transcriptionTable.put("х", "h");
        transcriptionTable.put("ц", "c");
        transcriptionTable.put("ч", "ch");
        transcriptionTable.put("ш", "sh");
        transcriptionTable.put("щ", "sht");
        transcriptionTable.put("ъ", "a");
        transcriptionTable.put("ь", "i");
        transcriptionTable.put("ю", "iu");
        transcriptionTable.put("я", "ia");
    }

    public AppUtils() {
    }

    public static String timeMillisToTimestampDB2(final String millis) {
        try {
            final long l = Long.parseLong(millis);
            String tmpDate = dateFormatterTimestampDB2.format(new Date(l));
            return tmpDate;
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToTimestampDB2 : " + e.getMessage());
        }
        return null;
    }

    public static String timeMillisToTimestampDB2(final long millis) {
        try {
            final long l = millis;
            String tmpDate = dateFormatterTimestampDB2.format(new Date(l));
            return tmpDate;
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToTimestampDB2 : " + e.getMessage());
        }
        return "no date";
    }

    public static String timeMillisTo_yyyyMMdd(final long millis) {
        try {
            final long l = millis;
            String tmpDate = dateFormatter_yyyyMMdd.format(new Date(l));
            return tmpDate;
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisTo_yyyyMMdd : " + e.getMessage());
        }
        return "no date";
    }

    public synchronized static Date getDateFromTimestampDB2(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormatterTimestampDB2.parse(tmpDateTimeFormat);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e)); // To change body of catch statement use File | Settings | File
                // Templates.
            }
        }
        return tmpDate;
    }

    public static Date getDateFromTimestamp(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormatterTimestamp.parse(tmpDateTimeFormat);
            } catch (ParseException e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return tmpDate;
    }

    public static Date getDateFromTimestampDSKReporting(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormatterDSKReporting.parse(tmpDateTimeFormat);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e)); // To change body of catch statement use File | Settings | File
                // Templates.
            }
        }
        return tmpDate;
    }

    public static String prettyPrintJson(String uglyJSONString) {
        String prettyJsonString = uglyJSONString;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(uglyJSONString);
            prettyJsonString = gson.toJson(je);
        } catch (Exception e) {
            //logger.error( e.getMessage());
            if (uglyJSONString != null && uglyJSONString.trim().length() > 0) {
                prettyJsonString = uglyJSONString;
            }
        }
        return prettyJsonString;
    }

    public static String getTwoPointsPrecision(float tmpTwoPointsPrecision) {
        String tmp = "0.00";
        unusualSymbols.setDecimalSeparator('.');
        twoPointsPrecision.setDecimalFormatSymbols(unusualSymbols);
        twoPointsPrecision.setGroupingSize(3);
        if (tmpTwoPointsPrecision != 0) {
            try {
                tmp = twoPointsPrecision.format(tmpTwoPointsPrecision);
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
            }
        }
        return tmp;
    }

    public static Date getDateFromDateTimeFormat(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormat_yyyy_MM_dd_HH_mm_ss.parse(tmpDateTimeFormat);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e)); // To change body of catch statement use File | Settings | File
                // Templates.
            }
        }
        return tmpDate;
    }

    public static String getHourFromDateTimeFormat(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormat_yyyy_MM_dd_HH_mm_ss.parse(tmpDateTimeFormat);
                return timeFormatter2.format(tmpDate);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e)); // To change body of catch statement use File | Settings | File
                // Templates.
            }
        }
        return "no date";
    }

    public static String getDateTimeFormat(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormat_yyyy_MM_dd_HH_mm_ss.parse(tmpDateTimeFormat);
                return dateFormat_yyyy_MM_dd_HH_mm_ss.format(tmpDate);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e)); // To change body of catch statement use File | Settings | File
                // Templates.
            }
        }
        return "no date";
    }

    public static String getHourMinutesFromDateTimeFormat(String tmpDateTimeFormat) {
        Date tmpDate = null;
        if ((tmpDateTimeFormat != null) && (tmpDateTimeFormat.length() >= 19)) {
            try {
                tmpDate = dateFormat_yyyy_MM_dd_HH_mm_ss.parse(tmpDateTimeFormat);
                return timeFormatter2.format(tmpDate);
            } catch (ParseException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
            }
        }
        return null;
    }

    public static String dateShortBgToTimeMillis(String timeStamp, String delimiter) {
        try {
            String day = null;
            String month = null;
            String year = null;
            if (timeStamp != null) {
                StringTokenizer st = new StringTokenizer(timeStamp, delimiter);
                if (st.countTokens() == 3) {
                    day = st.nextToken();
                    month = st.nextToken();
                    year = st.nextToken();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                    long currentDate = calendar.getTime().getTime();
                    String timeMillis = "" + currentDate;
                    return timeMillis;
                }
            }
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return "0";
    }

    /**
     * @param timeMillis
     * @return <b>dd.MM.yyyy</b> - sample 26.03.2004
     */
    public static String timeMillisToShortBgDate(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return dateFormatterShortBg.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToShortBgDate : " + e.getMessage());
            }

        }
        return "no date";
    }

    /**
     * @param timeMillis
     * @return <b>dd</b> - sample 26
     */
    public static String timeMillisToDay(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return dateFormatterDay.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToDay : " + e.getMessage());
            }

        }
        return "no date";
    }

    public static String timeMillisToYear(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return dateFormatterYear.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToYear : " + e.getMessage());
            }

        }
        return "no date";
    }

    /**
     * @return <b>yyyy-MM-dd HH:mm:ss</b> - sample 2004-03-26 15:23:45
     */
    public static String timeMillisTo_yyyy_MM_dd_HH_mm_ss(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return dateFormat_yyyy_MM_dd_HH_mm_ss.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToDateTimeFormat : " + e.getMessage());
            }

        }
        return "no date";
    }

    public static String timeMillisTo_yyyy_MM_dd_HH_mm_ss(long timeMillis) {
        try {
            Date date = new Date(timeMillis);
            return dateFormat_yyyy_MM_dd_HH_mm_ss.format(date);
        } catch (NumberFormatException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToDateTimeFormat : " + e.getMessage());
        }

        return "no date";
    }

    /**
     * @return <b>HH:mm</b> - sample 190 -> 03:10
     */
    public static String hourMinutesTimeMillisToHourMinutesTimeFormat(String timeMillis) {
        if (timeMillis != null) {
            try {
                long totalMinutes = Long.parseLong(timeMillis) / 60 / 1000;
                long hours = totalMinutes / 60;
                long minutes = totalMinutes % 60;
                return ((hours < 10) ? "0" + hours : hours + "") + ":" + ((minutes < 10) ? "0" + minutes : minutes + "");
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : hourMinutesTimeMillisToHourMinutesTimeFormat : " + e.getMessage());
            }

        }
        return "no date";
    }

    /**
     * @return <b>HH:mm</b> - sample 15:23
     */
    public static String timeMillisToHourMinutesTimeFormat(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return timeFormatter2.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToHourMinutesTimeFormat : " + e.getMessage());
            }

        }
        return "no date";
    }

    public static String timeMillisToHourMinutesSecondsTimeFormat(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return timeFormatter.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToHourMinutesSecondsTimeFormat : " + e.getMessage());
            }

        }
        return "no date";
    }

    public static String timeMillisToHour(String timeMillis) {
        if (timeMillis != null) {
            try {
                Date date = new Date(Long.parseLong(timeMillis));
                return timeFormatterHour.format(date);
            } catch (NumberFormatException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : timeMillisToHourMinutesTimeFormat : " + e.getMessage());
            }

        }
        return "no date";
    }

    /**
     * Returns date in format "DAY-OF-WEEK , DAY-OF-MONTH MONTH YEAR г." example : <b>Петък , 13 Февруари 2004 г.</b>
     *
     * @return
     */
    public static String getFormattedDate() {
        String tmpDate = dateFormatterBg.format(new Date());
        if (tmpDate.startsWith("Четвъвтък")) {
            tmpDate = "Четвъртък" + tmpDate.substring(9);
            // Logger.log(tmpDate);
        }
        return tmpDate;
    }

    /**
     * @param millis Returns date in format "DAY-OF-WEEK , DAY-OF-MONTH MONTH YEAR г." example : <b>Петък , 13 Февруари 2004 г.</b>
     * @return
     */
    public static String timeMillisToFormattedDate(String millis) {
        try {
            long l = Long.parseLong(millis);
            String tmpDate = dateFormatterBg.format(new Date(l));
            if (tmpDate.startsWith("Четвъвтък")) {
                tmpDate = "Четвъртък" + tmpDate.substring(9);
            }
            return tmpDate;
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToFormattedDate : " + e.getMessage());
        }
        return "no date";
    }

    /**
     * @return example : <b>Петък , 13 Февруари 2004 г. 12:45</b>
     */
    public static String timeMillisToFormattedDateTime(String millis) {
        try {
            long l = Long.parseLong(millis);
            String tmpDate = dateFormatterBgWithHourNoSpace.format(new Date(l));
            if (tmpDate.startsWith("Четвъвтък")) {
                tmpDate = "Четвъртък" + tmpDate.substring(9);
            }
            return tmpDate;
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToFormattedDateTime : " + e.getMessage());
        }
        return "no date";
    }

    public static String getFormattedDateWithTime(String millis) {
        try {
            long l = Long.parseLong(millis);
            return dateFormatterBgWithHourNoSpace.format(new Date(l));
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : getFormattedDateWithTime : " + e.getMessage());
        }
        return "no date";
    }

    // public static String formatDateTime(long time) {
    // return dateTimeFormatter.format(new java.util.Date(time));
    // }

    /**
     * Method getDate.
     *
     * @return String in format 'dd.MM.yyyy'
     */
    public static String getDate() {
        return dateFormatter.format(new Date());
    }

    /**
     * Method getTime.
     *
     * @return String in format 'HH:mm:ss'
     */
    public static String getTime() {
        return timeFormatter.format(new Date());
    }

    public static boolean isValidEmail(String email, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(email, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        StringTokenizer st = new StringTokenizer(email, "@");
        if (st.countTokens() != 2) {
            isValid = false;
            return isValid;
        }

        String name = st.nextToken();

        String host = st.nextToken();

        isValid = isValidHostName(host, false);

        if (!isValid) {
            return isValid;
        }

        if ((name.length() < 1) || (name.indexOf("..") != -1) || !Character.isLetterOrDigit(name.charAt(0)) || !Character.isLetterOrDigit(name.charAt(name.length() - 1))) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i)) && name.charAt(i) != '_' && name.charAt(i) != '-' && name.charAt(i) != '.') {
                isValid = false;
                return isValid;
            }
        }

        return isValid;
    } // IsValidEmail

    private static boolean isValidHostName(String url, boolean portEnabled) {
        boolean isValid = true;

        StringTokenizer st = new StringTokenizer(url, ":");
        int cntr = st.countTokens();
        String hostname = st.nextToken();

        if (hostname.length() == 0) {
            isValid = false;
            return isValid;
        }

        boolean isIpNumber = Character.isDigit(hostname.charAt(0));
        if (isIpNumber) {
            StringTokenizer st_ip = new StringTokenizer(hostname, ".");
            if (st_ip.countTokens() != 4) {
                isValid = false;
                return isValid;
            }
            for (int i = 0; i < 4; i++) {
                String ipsection = st_ip.nextToken();
                if (ipsection.length() < 1 || ipsection.length() > 3) {
                    isValid = false;
                    return isValid;
                }
                for (int j = 0; j < ipsection.length(); j++) {
                    if (!Character.isDigit(ipsection.charAt(j))) {
                        isValid = false;
                        return isValid;
                    }
                }
            }
        } else {
            StringTokenizer st_hs = new StringTokenizer(hostname, ".");
            if (st_hs.countTokens() < 2) {
                isValid = false;
                return isValid;
            }
            for (int i = 0; i < st_hs.countTokens(); i++) {
                String domainlabel = st_hs.nextToken();
                if (domainlabel.length() < 1) {
                    isValid = false;
                    return isValid;
                }
                if (!Character.isLetterOrDigit(domainlabel.charAt(0)) || !Character.isLetterOrDigit(domainlabel.charAt(domainlabel.length() - 1))) {
                    isValid = false;
                    return isValid;
                }
                for (int j = 0; j < domainlabel.length(); j++) {
                    if (!Character.isLetterOrDigit(domainlabel.charAt(j)) || domainlabel.charAt(j) == '-') {
                        isValid = true;
                        return isValid;
                    }
                }
            }
        }

        if (cntr == 2) {
            if (!portEnabled) {
                isValid = false;
                return isValid;
            }
            try {
                int port = Integer.parseInt(st.nextToken());
                if (port < 1 && port > 65535) {
                    isValid = false;
                    return isValid;
                }
            } catch (NumberFormatException e) {
                isValid = false;
                return isValid;
            }
        } else if (cntr == 1) {
            return isValid;
        } else {
            isValid = false;
            return isValid;
        }
        return isValid;
    } // IsValidHostName

    public static boolean isValidLetter(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isLetter(t_string.charAt(i)) && t_string.charAt(i) != ' ') {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidString

    public static boolean isValidLetterOrSpaceOrDash(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isLetterOrDigit(t_string.charAt(i)) && t_string.charAt(i) != ' ' && t_string.charAt(i) != '-') {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidString

    public static boolean isValidLiteralAndSpecialSymbol(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isLetterOrDigit(t_string.charAt(i))
                    && t_string.charAt(i) != ' '
                    && t_string.charAt(i) != '"'
                    && t_string.charAt(i) != '&'
                    && t_string.charAt(i) != '.'
                    && t_string.charAt(i) != '?') {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidLiteralAndSpecialSymbol

    public static boolean isValidLiteral(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isLetterOrDigit(t_string.charAt(i))) {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidLiteral

    public static boolean isValidPhone(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isDigit(t_string.charAt(i)) && t_string.charAt(i) != ' ' && t_string.charAt(i) != '+') {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidPhone

    public static boolean isNumber(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (!isValidLengthOrNotNull(t_string, t_min, t_max)) {
            isValid = false;
            return isValid;
        }

        for (int i = 0; i < t_string.length(); i++) {
            if (!Character.isDigit(t_string.charAt(i))) {
                isValid = false;
                return isValid;
            }
        }
        return isValid;
    } // isValidNumber

    public static boolean isValidLengthOrNotNull(String t_string, int t_min, int t_max) {
        boolean isValid = true;

        if (t_string == null || t_string.length() < t_min || t_string.length() > t_max) {
            isValid = false;
            return isValid;
        }
        return isValid;
    }

    public static boolean hasOtherThanNumbers(String target) {
        String trg;
        boolean found = false;
        int counter = 0;
        if (target != null) {
            while ((!found) && (counter < target.length())) {
                trg = target.substring(counter, counter + 1);
                try {
                    Integer.parseInt(trg);
                } catch (NumberFormatException e) {
                    found = true;
                }
                counter++;
            }
        } else
            return true;
        return found;
    } // hasOtherThanNumbers

    /**
     * Method transcriptToLatin.
     *
     * @param source
     * @return String
     */
    public static String transcriptToLatin(String source) {
        StringBuffer destination = new StringBuffer();
        if ((source != null) && (source.length() > 0)) {
            for (int i = 0; i < source.length(); i++) {
                String tmp = (String) transcriptionTable.get(source.charAt(i) + "");
                if (tmp != null) {
                    destination.append(tmp);
                } else {
                    destination.append(source.charAt(i) + "");
                }
            }
        }

        return destination.toString();
    }

    public static String truncate(String s, int maxLength) {
        if (s != null) {
            if (s.length() > maxLength) {
                return s.substring(0, maxLength - 1);
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

    public static String truncateInner(String s, int before, int after) {
        StringBuffer buffer = new StringBuffer();

        if (s != null) {

            StringTokenizer tokenizer = new StringTokenizer(s, "-");
            int tokens = tokenizer.countTokens();

            if (tokens < before + after)
                return s;

            String[] points = new String[tokens];

            for (int i = 0; i < tokens; i++)
                points[i] = tokenizer.nextToken().trim();

            for (int i = 0; i < before; i++) {
                buffer.append(points[i]);
                buffer.append("-");
            }

            buffer.append(" ... ");

            for (int i = tokens - after; i < tokens; i++) {
                buffer.append("-");
                buffer.append(points[i]);
            }

        } // if

        return buffer.toString();

    }

    public static String switchOptions(int i, String option1, String option2) {
        if ((i % 2) == 0) {
            return option1;
        }
        return option2;
    }

    public static String minutesToHours(String min) {
        try {
            int m = Integer.parseInt(min);
            int h = m / 60;
            m = m % 60;
            if (m < 10)
                return (h + ":0" + m);
            else
                return (h + ":" + m);
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        return "-";
    }

    public static String millisNextDay(String time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(Long.parseLong(time)));
            cal.add(Calendar.DATE, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime().getTime() + "";
        }
        return null;
    }

    public static long millisNextDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public static long millisCurrentDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * @param date     in format dd.MM.yyyy
     * @param beginDay if true time is 00:00:00 else 23:59:59.999
     * @return millis for that day
     */
    public static long dateToTimeMillis(String date, boolean beginDay) {
        try {
            StringTokenizer st = new StringTokenizer(date, ".");
            if (st.countTokens() != 3)
                return 0;
            int day = Integer.parseInt(st.nextToken());
            int month = Integer.parseInt(st.nextToken());
            int year = Integer.parseInt(st.nextToken());
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.YEAR, year);

            if (beginDay) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
            } else {
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
            }
            return cal.getTime().getTime();
        } catch (NumberFormatException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : dateToTimeMillis : " + e.getMessage());
        }
        return 0;
    }

    public static String millisOfWeekStart() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
        cal.add(Calendar.DATE, -dayOfWeek);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime().getTime() + "";
    }

    public static String millisOfWeekEnd() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
        cal.add(Calendar.DATE, 8 - dayOfWeek);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() + "";
    }

    public static String millisOfMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime().getTime() + "";
    }

    public static String millisOfMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() + "";
    }

    public static String timeStampToHoursAndMinutes(String s) {
        try {
            String tmp = s.substring(11, 16);
            return tmp;
        } catch (Exception e) {
        }
        return "-";
    }

    public static long minutesToMillis(long seconds) {
        return seconds * 60 * 1000;
    }

    public static long shortBgDateToTimeMillis(final String s) {
        if (s != null) {
            try {
                return dateFormatterShortBg.parse(s).getTime();
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : shortBgDateToTimeMillis(" + s + ") : " + e.getMessage());
            }
        }
        return 0;
    }

    public static String hourMinutesTimeFormatToTimeMillis(final String s) {
        if (s != null && s.trim().length() == 5) {
            try {
                return String.valueOf((Long.parseLong(s.substring(0, 2)) * 60 * 60 * 1000) + (Long.parseLong(s.substring(3, 5)) * 60 * 1000));
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : hourMinutesTimeFormatToTimeMillis(" + s + ") : " + e.getMessage());
            }
        }
        return null;
    }

    public static String millisOfCustomMonthStart(String month, String year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime().getTime() + "";
    }

    public static String millisOfCustomMonthEnd(String month, String year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, Integer.parseInt(month));
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() + "";
    }

    public static String[] sortStringNumbers(String[] arr) {
        if (arr != null && arr.length > 0) {
            int[] reorder = new int[arr.length];
            for (int i = 0; i < reorder.length; i++) {
                try {
                    if (arr[i] != null && arr[i].trim().length() > 0)
                        reorder[i] = Integer.parseInt(arr[i]);
                    else
                        return arr;
                } catch (Exception e) {
                    logger.debug("ERROR IN PARSING SEAT NUMBER TO INTEGER!!!");
                }
            }
            try {
                Arrays.sort(reorder);
                String[] ordered = new String[reorder.length];
                for (int i = 0; i < reorder.length; i++) {
                    ordered[i] = reorder[i] + "";
                }
                return ordered;
            } catch (Exception e) {
                logger.debug("ERROR IN SORTING SEAT NUMBERS!!!");
            }
        }
        return null;
    }

    public static String slipStringArrayWithCommas(String[] arr) {
        if (arr != null && arr.length > 0) {
            String tmp = "";
            for (int i = 0; i < arr.length; i++) {
                if (i != 0)
                    tmp += ",";
                tmp += arr[i];
            }
            return (tmp.trim().length() > 0) ? tmp : null;
        }
        return null;
    }

    public static String escaper(String str) {
        if (str != null && str.trim().length() > 0) {
            StringBuffer strBuffer = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                strBuffer.append(str.charAt(i));
                if (str.charAt(i) == '\\')
                    strBuffer.append("\\");
            }
            str = strBuffer.toString();
        }
        return str;
    }

    public static String checkForCyrillic(String str) {
        StringBuffer strb = new StringBuffer();
        if (str != null && str.trim().length() > 0) {
            for (int i = 0; i < str.length(); i++) {
                if ((int) str.charAt(i) >= 1040 && (int) str.charAt(i) <= 1103 && (int) str.charAt(i) != 1067 && (int) str.charAt(i) != 1069 && (int) str.charAt(i) != 1099 && (int) str.charAt(i) != 1101) {
                    strb.append(convertCyrillicToBrowserRequestRepresentation(str.charAt(i)));
                } else {
                    strb.append(str.charAt(i) + "");
                }
            }

        }
        return strb.toString();
    }

    public static String convertCyrillicToBrowserRequestRepresentation(char str) {
        HashMap transcriptionBrowserTable = new HashMap();
        transcriptionBrowserTable.put("А", "%C0");
        transcriptionBrowserTable.put("Б", "%C1");
        transcriptionBrowserTable.put("В", "%C2");
        transcriptionBrowserTable.put("Г", "%C3");
        transcriptionBrowserTable.put("Д", "%C4");
        transcriptionBrowserTable.put("Е", "%C5");
        transcriptionBrowserTable.put("Ж", "%C6");
        transcriptionBrowserTable.put("З", "%C7");
        transcriptionBrowserTable.put("И", "%C8");
        transcriptionBrowserTable.put("Й", "%C9");
        transcriptionBrowserTable.put("К", "%CA");
        transcriptionBrowserTable.put("Л", "%CB");
        transcriptionBrowserTable.put("М", "%CC");
        transcriptionBrowserTable.put("Н", "%CD");
        transcriptionBrowserTable.put("О", "%CE");
        transcriptionBrowserTable.put("П", "%CF");
        transcriptionBrowserTable.put("Р", "%D0");
        transcriptionBrowserTable.put("С", "%D1");
        transcriptionBrowserTable.put("Т", "%D2");
        transcriptionBrowserTable.put("У", "%D3");
        transcriptionBrowserTable.put("Ф", "%D4");
        transcriptionBrowserTable.put("Х", "%D5");
        transcriptionBrowserTable.put("Ц", "%D6");
        transcriptionBrowserTable.put("Ч", "%D7");
        transcriptionBrowserTable.put("Ш", "%D8");
        transcriptionBrowserTable.put("Щ", "%D9");
        transcriptionBrowserTable.put("Ъ", "%DA");
        transcriptionBrowserTable.put("Ь", "%DC");
        transcriptionBrowserTable.put("Ю", "%DE");
        transcriptionBrowserTable.put("Я", "%DF");
        transcriptionBrowserTable.put("а", "%E0");
        transcriptionBrowserTable.put("б", "%E1");
        transcriptionBrowserTable.put("в", "%E2");
        transcriptionBrowserTable.put("г", "%E3");
        transcriptionBrowserTable.put("д", "%E4");
        transcriptionBrowserTable.put("е", "%E5");
        transcriptionBrowserTable.put("ж", "%E6");
        transcriptionBrowserTable.put("з", "%E7");
        transcriptionBrowserTable.put("и", "%E8");
        transcriptionBrowserTable.put("й", "%E9");
        transcriptionBrowserTable.put("к", "%EA");
        transcriptionBrowserTable.put("л", "%EB");
        transcriptionBrowserTable.put("м", "%EC");
        transcriptionBrowserTable.put("н", "%ED");
        transcriptionBrowserTable.put("о", "%EE");
        transcriptionBrowserTable.put("п", "%EF");
        transcriptionBrowserTable.put("р", "%F0");
        transcriptionBrowserTable.put("с", "%F1");
        transcriptionBrowserTable.put("т", "%F2");
        transcriptionBrowserTable.put("у", "%F3");
        transcriptionBrowserTable.put("ф", "%F4");
        transcriptionBrowserTable.put("х", "%F5");
        transcriptionBrowserTable.put("ц", "%F6");
        transcriptionBrowserTable.put("ч", "%F7");
        transcriptionBrowserTable.put("ш", "%F8");
        transcriptionBrowserTable.put("щ", "%F9");
        transcriptionBrowserTable.put("ъ", "%FA");
        transcriptionBrowserTable.put("ь", "%FC");
        transcriptionBrowserTable.put("ю", "%FE");
        transcriptionBrowserTable.put("я", "%FF");

        if (transcriptionBrowserTable.get(str + "") != null)
            return (String) transcriptionBrowserTable.get(str + "");
        return str + "";
    }

    public static String convertBrowserRequestRepresentationToCyrillic(char str) {
        HashMap transcriptionBrowserTable = new HashMap();
        transcriptionBrowserTable.put("%C0", "А");
        transcriptionBrowserTable.put("%C1", "Б");
        transcriptionBrowserTable.put("%C2", "В");
        transcriptionBrowserTable.put("%C3", "Г");
        transcriptionBrowserTable.put("%C4", "Д");
        transcriptionBrowserTable.put("%C5", "Е");
        transcriptionBrowserTable.put("%C6", "Ж");
        transcriptionBrowserTable.put("%C7", "З");
        transcriptionBrowserTable.put("%C8", "И");
        transcriptionBrowserTable.put("%C9", "Й");
        transcriptionBrowserTable.put("%CA", "К");
        transcriptionBrowserTable.put("%CB", "Л");
        transcriptionBrowserTable.put("%CC", "М");
        transcriptionBrowserTable.put("%CD", "Н");
        transcriptionBrowserTable.put("%CE", "О");
        transcriptionBrowserTable.put("%CF", "П");
        transcriptionBrowserTable.put("%D0", "Р");
        transcriptionBrowserTable.put("%D1", "С");
        transcriptionBrowserTable.put("%D2", "Т");
        transcriptionBrowserTable.put("%D3", "У");
        transcriptionBrowserTable.put("%D4", "Ф");
        transcriptionBrowserTable.put("%D5", "Х");
        transcriptionBrowserTable.put("%D6", "Ц");
        transcriptionBrowserTable.put("%D7", "Ч");
        transcriptionBrowserTable.put("%D8", "Ш");
        transcriptionBrowserTable.put("%D9", "Щ");
        transcriptionBrowserTable.put("%DA", "Ъ");
        transcriptionBrowserTable.put("%DC", "Ь");
        transcriptionBrowserTable.put("%DE", "Ю");
        transcriptionBrowserTable.put("%DF", "Я");
        transcriptionBrowserTable.put("%E0", "а");
        transcriptionBrowserTable.put("%E1", "б");
        transcriptionBrowserTable.put("%E2", "в");
        transcriptionBrowserTable.put("%E3", "г");
        transcriptionBrowserTable.put("%E4", "д");
        transcriptionBrowserTable.put("%E5", "е");
        transcriptionBrowserTable.put("%E6", "ж");
        transcriptionBrowserTable.put("%E7", "з");
        transcriptionBrowserTable.put("%E8", "и");
        transcriptionBrowserTable.put("%E9", "й");
        transcriptionBrowserTable.put("%EA", "к");
        transcriptionBrowserTable.put("%EB", "л");
        transcriptionBrowserTable.put("%EC", "м");
        transcriptionBrowserTable.put("%ED", "н");
        transcriptionBrowserTable.put("%EE", "о");
        transcriptionBrowserTable.put("%EF", "п");
        transcriptionBrowserTable.put("%F0", "р");
        transcriptionBrowserTable.put("%F1", "с");
        transcriptionBrowserTable.put("%F2", "т");
        transcriptionBrowserTable.put("%F3", "у");
        transcriptionBrowserTable.put("%F4", "ф");
        transcriptionBrowserTable.put("%F5", "х");
        transcriptionBrowserTable.put("%F6", "ц");
        transcriptionBrowserTable.put("%F7", "ч");
        transcriptionBrowserTable.put("%F8", "ш");
        transcriptionBrowserTable.put("%F9", "щ");
        transcriptionBrowserTable.put("%FA", "ъ");
        transcriptionBrowserTable.put("%FC", "ь");
        transcriptionBrowserTable.put("%FE", "ю");
        transcriptionBrowserTable.put("%FF", "я");

        if (transcriptionBrowserTable.get(str + "") != null)
            return (String) transcriptionBrowserTable.get(str + "");
        return str + "";
    }

    public static String convertXMLBrowserRequestRepresentationToCyrillic(char str) {
        HashMap transcriptionBrowserTable = new HashMap();
        transcriptionBrowserTable.put("%D0%B0", "а");
        transcriptionBrowserTable.put("%D0%B1", "б");
        transcriptionBrowserTable.put("%D0%B2", "в");
        transcriptionBrowserTable.put("%D0%B3", "г");
        transcriptionBrowserTable.put("%D0%B4", "д");
        transcriptionBrowserTable.put("%D0%B5", "е");
        transcriptionBrowserTable.put("%D0%B6", "ж");
        transcriptionBrowserTable.put("%D0%B7", "з");
        transcriptionBrowserTable.put("%D0%B8", "и");
        transcriptionBrowserTable.put("%D0%B9", "й");
        transcriptionBrowserTable.put("%D0%BA", "к");
        transcriptionBrowserTable.put("%D0%BB", "л");
        transcriptionBrowserTable.put("%D0%BC", "м");
        transcriptionBrowserTable.put("%D0%BD", "н");
        transcriptionBrowserTable.put("%D0%BE", "о");
        transcriptionBrowserTable.put("%D0%BF", "п");
        transcriptionBrowserTable.put("%D1%80", "р");
        transcriptionBrowserTable.put("%D1%81", "с");
        transcriptionBrowserTable.put("%D1%82", "т");
        transcriptionBrowserTable.put("%D1%83", "у");
        transcriptionBrowserTable.put("%D1%84", "ф");
        transcriptionBrowserTable.put("%D1%85", "х");
        transcriptionBrowserTable.put("%D1%86", "ц");
        transcriptionBrowserTable.put("%D1%87", "ч");
        transcriptionBrowserTable.put("%D1%88", "ш");
        transcriptionBrowserTable.put("%D1%89", "щ");
        transcriptionBrowserTable.put("%D1%8C", "ь");
        transcriptionBrowserTable.put("%D1%8A", "ъ");
        transcriptionBrowserTable.put("%D1%8E", "ю");
        transcriptionBrowserTable.put("%D1%8F", "я");
        transcriptionBrowserTable.put("%D0%90", "А");
        transcriptionBrowserTable.put("%D0%91", "Б");
        transcriptionBrowserTable.put("%D0%92", "В");
        transcriptionBrowserTable.put("%D0%93", "Г");
        transcriptionBrowserTable.put("%D0%94", "Д");
        transcriptionBrowserTable.put("%D0%95", "Е");
        transcriptionBrowserTable.put("%D0%96", "Ж");
        transcriptionBrowserTable.put("%D0%97", "З");
        transcriptionBrowserTable.put("%D0%98", "И");
        transcriptionBrowserTable.put("%D0%99", "Й");
        transcriptionBrowserTable.put("%D0%9A", "К");
        transcriptionBrowserTable.put("%D0%9B", "Л");
        transcriptionBrowserTable.put("%D0%9C", "М");
        transcriptionBrowserTable.put("%D0%9D", "Н");
        transcriptionBrowserTable.put("%D0%9E", "О");
        transcriptionBrowserTable.put("%D0%9F", "П");
        transcriptionBrowserTable.put("%D0%A0", "Р");
        transcriptionBrowserTable.put("%D0%A1", "С");
        transcriptionBrowserTable.put("%D0%A2", "Т");
        transcriptionBrowserTable.put("%D0%A3", "У");
        transcriptionBrowserTable.put("%D0%A4", "Ф");
        transcriptionBrowserTable.put("%D0%A5", "Х");
        transcriptionBrowserTable.put("%D0%A6", "Ц");
        transcriptionBrowserTable.put("%D0%A7", "Ч");
        transcriptionBrowserTable.put("%D0%A8", "Ш");
        transcriptionBrowserTable.put("%D0%A9", "Щ");
        transcriptionBrowserTable.put("%D0%AC", "Ь");
        transcriptionBrowserTable.put("%D0%AA", "Ъ");
        transcriptionBrowserTable.put("%D0%AE", "Ю");
        transcriptionBrowserTable.put("%D0%AF", "Я");

        if (transcriptionBrowserTable.get(str + "") != null)
            return (String) transcriptionBrowserTable.get(str + "");
        return str + "";
    }

    public static String formatNumber(double number, int length) {
        String pattern = "";
        for (int i = 0; i < length; i++) {
            pattern += "0";
        }
        pattern += ".00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, symbols);
        return df.format(number);
    }

    public static String formatNumber(double number) {
        String pattern = "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, symbols);
        return df.format(number);
    }

    public static String formatNumber(float number) {
        String pattern = "0.00";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, symbols);
        return df.format(number);
    }

    public static String encryptPassword(String password) {
        if (password != null && password.trim().length() > 0) {
            MD5Digest md5 = null;
            try {
                md5 = new MD5Digest();
            } catch (NoSuchAlgorithmException e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
            }
            return password = md5.md5crypt(password);
        }
        return password;
    }

    public static int getSumToOneDigit(String digit) {
        int tmpDigit = 0;
        for (int i = 0; i < digit.length(); i++) {
            tmpDigit += Integer.parseInt(digit.charAt(i) + "");
            if (tmpDigit > 9) {
                tmpDigit = Integer.parseInt(String.valueOf(tmpDigit).substring(0, 1)) + Integer.parseInt(String.valueOf(tmpDigit).substring(1, 2));
            }
        }
        return (tmpDigit > 0) ? tmpDigit : (tmpDigit + 1);
    }

    public static String timeMillisToEPAYDateTime(final long timeMillis) {
        try {
            final Date date = new Date(timeMillis);
            return dateFormatterEPAY.format(date);
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToEPAYDateTime(" + timeMillis + ") : " + e.getMessage());
        }
        return dateFormatterEPAY.format(new Date(System.currentTimeMillis()));
    }

    public static long ePAYDateTimeToTimeMillis(final String s) {
        if (s != null) {
            try {
                String receiveDate = s.substring(6, 8) + "." + s.substring(4, 6) + "." + s.substring(0, 4) + " ";
                receiveDate += s.substring(8, 10) + ":" + s.substring(10, 12) + ":" + s.substring(12, s.length());
                return dateFormatterEPAY.parse(receiveDate).getTime();
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : ePAYDateTimeToTimeMillis(" + s + ") : " + e.getMessage());
            }
        }
        return 0;
    }

    public static synchronized String timeMillisToBORICADateTime(final long timeMillis) {
        try {
            final Date date = new Date(timeMillis);
            return dateFormatterBORICA.format(date);
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : timeMillisToBORICADateTime(" + timeMillis + ") : " + e.getMessage());
        }
        return dateFormatterEPAY.format(new Date(System.currentTimeMillis()));
    }

    public static synchronized String timeMillisToBORICADateTimeUTC(long timeMillis) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(timeMillis));
//            System.out.println("calendar.getTimeZone().getDisplayName() = " + calendar.getTimeZone().getDisplayName());
//            System.out.println("AppUtils.getGMTOffsetAsString(new Date()) = " + AppUtils.getGMTOffsetAsString(calendar));
//            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//            System.out.println("calendar.getTimeZone().getDisplayName() = " + calendar.getTimeZone().getDisplayName());
//            System.out.println("AppUtils.getGMTOffsetAsString(new Date()) = " + AppUtils.getGMTOffsetAsString(calendar));
            dateFormatterBORICA.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormatterBORICA.format(calendar.getTime().getTime());
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
//            logger.error( "AppUtils : timeMillisToBORICADateTime(" + timeMillis + ") : " + e.getMessage());
        }
        return dateFormatterEPAY.format(new Date(System.currentTimeMillis()));
    }

    public static synchronized long BORICADateTimeToTimeMillis(final String s) {
        if (s != null) {
            try {
                return dateFormatterBORICA.parse(s).getTime();
            } catch (Exception e) {
                logger.debug(ExceptionUtils.getStackTrace(e));
                logger.error("AppUtils : BORICADateTimeToTimeMillis(" + s + ") : " + e.getMessage());
            }
        }
        return 0;
    }

    public static String encryptMD5(final String str) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        if (m != null && str != null) {
            m.update(str.getBytes(), 0, str.length());
            return (new BigInteger(1, m.digest()).toString(16)).toString();
        }
        return str;
    }

    public static boolean isNotPassedThirtyDaysFromSale(String dateOfIssue) {
        boolean result = false;
        try {
            long thirtyDaysMillis = 30 * 24 * 60 * 60 * 1000L;//30 days
            long currentTimeMillis = System.currentTimeMillis();
            long dateOfIssueMillis = getDateFromDateTimeFormat(dateOfIssue).getTime();
            if ((currentTimeMillis - thirtyDaysMillis) < dateOfIssueMillis) {
                result = true;
            }
        } catch (Exception e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
            logger.error("AppUtils : isPassedThirtyDaysFromSale(" + dateOfIssue + ") : " + e.getMessage());
        }
        return result;
    }

    public static String formatInteger(int number, int length) {
        String pattern = "";
        for (int i = 0; i < length; i++) {
            pattern += "0";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat(pattern, symbols);
        df.setGroupingUsed(false);
        return df.format(number);
    }

    public static String computeSecurityString(String accountId,
                                               String transactionId,
                                               String transactionDescription,
                                               String transactionValue,
                                               String vendorId,
                                               String timeLimitInMinutes,
                                               String timeExtra,
                                               String lang,
                                               String operationType,
                                               String currencyCode,
                                               String passPhrase) {
        String securityString = accountId
                + transactionId.trim()
                + transactionDescription.trim()
                + transactionValue.trim()
                + vendorId.trim()
                + timeLimitInMinutes.trim()
                + (timeExtra.trim().length() > 0 ? timeExtra.trim() : "")
                + (lang.trim().length() > 0 ? lang.trim() : "")
                + (operationType.trim().length() > 0 ? operationType.trim() : "")
                + (currencyCode.trim().length() > 0 ? currencyCode.trim() : "")
                + passPhrase.trim();
        String securityHash = null;
        logger.debug("computeSecurityString() : securityString = " + securityString);
        try {
            securityHash = DigestUtils.md5Hex(securityString.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        logger.debug("computeSecurityString() : securityHash = " + securityHash);
        return securityHash;
    }

    public static String computeSecurityString(String accountId,
                                               String nonce,
                                               String passPhrase) {
        String securityString = accountId.trim()
                + nonce.trim()
                + passPhrase.trim();
        String securityHash = null;
        logger.debug("computeSecurityReportString() : securityString = " + securityString);
        try {
//            securityHash = DigestUtils.md5Hex(securityString.getBytes("utf8"));
            securityHash = DigestUtils.sha256Hex(securityString.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            logger.debug(ExceptionUtils.getStackTrace(e));
        }
        logger.debug("computeSecurityReportString() : securityHash = " + securityHash);
        return securityHash;
    }

    public static String computeSaltedPassword(String password,
                                               byte[] salt) {
        MessageDigest md;
        StringBuilder sb = new StringBuilder();
        try {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(UTF_8));

            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

//            System.out.println(sb);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static synchronized String convertCyrillicToUnicode(byte[] source) {
        String out = new String(source, UTF_8);
        //System.out.println(AppUtils.timeMillisToDateTimeFormat(System.currentTimeMillis() + "") + " out = " + out);
        return out;
    }

    public static synchronized byte[] convertUnicodeToCyrillic(String s) {
        //String out = null;
        byte[] result = null;
        try {
            result = s.getBytes("cp1251");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        // System.out.println(AppUtils.timeMillisToDateTimeFormat(System.currentTimeMillis() + "") + " out = " + out);
        return result;
    }

    public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        if (data != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                int halfbyte = (data[i] >>> 4) & 0x0F;
                int two_halfs = 0;
                do {
                    if ((0 <= halfbyte) && (halfbyte <= 9))
                        buf.append((char) ('0' + halfbyte));
                    else
                        buf.append((char) ('a' + (halfbyte - 10)));
                    halfbyte = data[i] & 0x0F;
                } while (two_halfs++ < 1);
            }
        }
        return buf.toString();
    }

    public static byte[] convertHEXStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String convertToHex(byte[] data, int count) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
            }//for i

            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }//xorMessage

    public static String MD5(String text, String encoding)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.reset();
        byte[] hash;
        md.update(text.getBytes(encoding), 0, text.length());
        hash = md.digest();
        return convertToHex(hash);
    }

    public static String SHA256HEX(String text, String encoding)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");
        md.reset();
        byte[] hash;
        md.update(text.getBytes(encoding), 0, text.length());
        hash = md.digest();
        return convertToHex(hash);
    }

    public static byte[] SHA256(String text, String encoding)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");
        md.reset();
        byte[] hash;
        md.update(text.getBytes(encoding), 0, text.length());
        hash = md.digest();
        return hash;
    }

    public static byte[] SHA512(String text, String encoding)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        md.reset();
        byte[] hash;
        md.update(text.getBytes(encoding), 0, text.length());
        hash = md.digest();
        return hash;
    }

    public static byte[] SHA512(byte[] bytes)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        md.reset();
        byte[] hash;
        md.update(bytes, 0, bytes.length);
        hash = md.digest();
        return hash;
    }

    // Encrypt a string using the provided key.
    public static byte[] hmacSha256(final String data, final byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        final Mac mac = Mac.getInstance(HMAC_SHA_256);
        mac.init(new SecretKeySpec(key, HMAC_SHA_256));
        return mac.doFinal(data.getBytes(UTF_8));
    }

    // Encrypt a byte array using the provided key.
    public static byte[] hmacSha256(final byte[] data, final byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        final Mac mac = Mac.getInstance(HMAC_SHA_256);
        mac.init(new SecretKeySpec(key, HMAC_SHA_256));
        return mac.doFinal(data);
    }

    public static String replaceWithCorrectFileSeparator(String path) {
        String correctPath = "";
//        logger.debug( "replaceWithCorrectFileSeparator() : path = " + path);
        if (path != null && path.length() > 0) {
            correctPath = path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(File.separator));
        }
//        logger.debug( "replaceWithCorrectFileSeparator() : correctPath = " + correctPath);
        return correctPath;
    }

    public static void copyFileCommonIO(String from, String to)
            throws IOException {

        File fromDir = new File(from);
        File toDir = new File(to);

        FileUtils.copyDirectory(fromDir, toDir);

    }

    public static String getCustomerDescription(String okUrl) {
        String result = "";
        if (okUrl != null
                && !"null".equalsIgnoreCase(okUrl)
                && okUrl.trim().length() > 0) {
            if (okUrl.indexOf("&desc=") > -1) {
                int descIndex = okUrl.indexOf("&desc=");
                result = "(" + okUrl.substring((descIndex + 6), okUrl.length()) + ")";
                if (result.indexOf("&") > -1) {
                    result = "(" + okUrl.substring(0, result.indexOf("&")) + ")";
                }
            }
        }
        return result;
    }

    public static synchronized String paddingString(String targetString,
                                                    int size,
                                                    char paddingCharacter,
                                                    boolean paddingLeft) {
        StringBuffer str = new StringBuffer(targetString);
        int strLength = str.length();
        if (size > 0 && size > strLength) {
            for (int i = 0; i <= size; i++) {
                if (paddingLeft) {
                    if (i < size - strLength) str.insert(0, paddingCharacter);
                } else {
                    if (i > strLength) str.append(paddingCharacter);
                }
            }
        }
        return str.toString();
    }

    public static String writeFile(String path, String fileName, byte[] image) {
        int position = fileName.lastIndexOf('.');
        if (position > 0) {
            logger.debug("PATH: " + path);
            logger.debug("FILENAME: " + fileName);
            if (path != null && fileName != null) {
                try {
                    FileOutputStream fos = new FileOutputStream(new File(path, fileName));

                    fos.write(image);
                    fos.flush();
                    fos.close();
                    return fileName;
                } catch (FileNotFoundException e) {
                    logger.debug(e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    logger.debug(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
