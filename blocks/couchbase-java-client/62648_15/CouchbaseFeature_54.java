package com.couchbase.client.java.fts.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.couchbase.client.core.CouchbaseException;

public class SearchUtils {

    private static final String FTS_SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZ";

    private static final Pattern PATTERN = Pattern.compile("([\\+-])(\\d{1,2})(?::(\\d{1,2}))?$");

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(FTS_SIMPLE_DATE_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            sdf.setLenient(false);
            return sdf;
        }
    };

    public static String toFtsUtcString(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat rfc3339 = df.get();
        String zDate = rfc3339.format(date);
        String xDate = zDate.replaceFirst("\\+0000$", "Z");
        return xDate;
    }

    public static Date fromFtsString(String date) {
        if (date == null) {
            return null;
        }
        String zDate = iso822TimezoneToRfc3339Timezone(date);
        try {
            return df.get().parse(zDate);
        } catch (ParseException e) {
            throw new CouchbaseException("Cannot parse FTS date '" + date + "' despite convertion to RFC 822 timezone '"
                    + zDate + "'", e);
        }
    }

    private static String iso822TimezoneToRfc3339Timezone(String xDate) {
        String zDate;
        if (xDate.endsWith("Z")) {
            zDate = xDate.replaceFirst("Z$", "+0000");
        } else {
            final Matcher matcher = PATTERN.matcher(xDate);
            if (matcher.find()) {
                String sign = matcher.group(1);
                String hours = matcher.group(2);
                String minutes = "00";
                if (matcher.groupCount() == 3 && matcher.group(3) != null) {
                    minutes = matcher.group(3);
                }

                if (hours.length() == 1) {
                    hours = "0" + hours;
                }
                if (minutes.length() == 1) {
                    minutes = "0" + minutes;
                }

                zDate = matcher.replaceFirst(sign + hours + minutes);
            } else {
                throw new CouchbaseException("Cannot convert timezone to RFC 822 in '" + xDate + "'");
            }
        }
        return zDate;
    }
}
