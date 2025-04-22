    public static Expression millisToZone(Expression expression, String timeZoneName, String format) {
        if (format == null || format.isEmpty()) {
            return x("MILLIS_TO_ZONE(" + expression.toString() + ", \"" + timeZoneName + "\")");
        }
        return x("MILLIS_TO_ZONE(" + expression.toString() + ", \"" + timeZoneName + "\"" +
                ", \"" + format + "\")");
    }

    public static Expression millisToZone(String expression, String timeZoneName, String format) {
        return millisToZone(x(expression), timeZoneName, format);
    }

    public static Expression nowMillis() {
        return x("NOW_MILLIS()");
    }

    public static Expression nowStr(String format) {
        if (format == null || format.isEmpty()) {
            return x("NOW_STR()");
        }
        return x("NOW_STR(\"" + format + "\")");
    }

    public static Expression strToUtc(Expression expression) {
        return x("STR_TO_UTC(" + expression.toString() + ")");
    }

    public static Expression strToUtc(String expression) {
        return strToUtc(x(expression));
    }
    public static Expression strToZoneName(Expression expression, String zoneName) {
        return x("STR_TO_ZONE_NAME(" + expression.toString() + ", \"" + zoneName + "\")");
    }

    public static Expression strToZoneName(String expression, String zoneName) {
        return strToZoneName(x(expression), zoneName);
    }

    public enum DatePart {
        millenium,
        century,
        decade,
        year,
        quarter,
        month,
        week,
        day,
        hour,
        minute,
        second,
        millisecond
    }
    
    public enum DatePartExt {
        millenium,
        century,
        decade,
        year,
        quarter,
        month,
        week,
        day,
        hour,
        minute,
        second,
        millisecond,
        day_of_year,
        day_of_week,
        iso_week,
        iso_year,
        iso_dow,
        timezone,
        timezone_hour,
        timezone_minute
