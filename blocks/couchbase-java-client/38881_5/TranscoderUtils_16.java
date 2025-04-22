
package com.couchbase.client.java.convert.util;

import java.util.regex.Pattern;

public class StringUtils {

  private static final Pattern decimalPattern = Pattern.compile("^-?\\d+$");

  public static boolean isJsonObject(final String document) {
    return (document != null && !document.isEmpty()) && (document.startsWith("{") || document.startsWith("[")
        || "true".equals(document) || "false".equals(document)
        || "null".equals(document) || decimalPattern.matcher(document).matches()
    );
  }

}
