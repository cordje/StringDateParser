import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zane on 10/31/2015.
 */
public class DateParser {
    private String dateString;
    private String dateFormat;

    /**
     * Get the milliseconds since the epoch
     * @return Milliseconds since epoch (Long)
     * @throws ParseException
     */
    public long getEpochTime() throws ParseException {
        long millis = parseStringToTimestamp(dateString).getTime();
        return millis;
    }

    public DateParser(String dateString) {
        this.dateString = dateString;
    }

    /**
     *  This allows you to add custom formats to the map
     * @param regex a regex String
     * @param dateFormatString the correspond date format String
     */
    public void addRegexFormat(String regex, String dateFormatString){
        this.DATE_FORMAT_REGEXPS.put(regex,dateFormatString);
    }

    private Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{1,2}:\\d{1,2}\\s(am|pm)\\s[a-z]{3},\\s(mon|tue|wed|thu|fri|sat|sun)\\s(january|february|march|april|may|june|july|august|september|october|november|december)\\s\\d{1,2},\\s\\d{4}$", "hh:mm a z, E MMMM dd, yyyy");
        put("^\\d{8}$", "yyyyMMdd");
        put("^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).\\s\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}\\s(am|pm)\\s[a-z]{3}$","MMM. dd, yyyy hh:mm a z");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^(january|february|march|april|may|june|july|august|september|october|november|december)\\s\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}\\s(am|pm)$","MMMM dd, yyyy hh:mm a");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\[a-z]{2,},\\d{1,2}\\s\\d{4}$", "MMM dd, yyyy");
        put("^(january|february|march|april|may|june|july|august|september|october|november|december)\\s\\d{1,2},\\s\\d{4}$", "MMMM dd, yyyy");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Parse a String to a Timestamp. Return null if failed to parse.
     * @param s String to attempt to parse
     * @return java.sql.timestamp if successfull else null
     * @throws ParseException
     */
    public Timestamp parseStringToTimestamp(String s) throws ParseException {
        Timestamp t = null;
        Date parsedDate = null;
        DateFormat dateFormat = null;
        if(!s.isEmpty()) {
            dateFormat = new SimpleDateFormat(determineDateFormat(s));
        }
        if(dateFormat != null){
            parsedDate = dateFormat.parse(s);
            t = new Timestamp(parsedDate.getTime());
        }
        return t;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown.
     * @param s The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public String determineDateFormat(String s) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (s.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null; // Unknown format.
    }
}
