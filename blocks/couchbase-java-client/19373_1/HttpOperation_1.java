package com.couchbase.client.http;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class HttpUtil {

  public static String buildAuthHeader(String username, String password)
          throws UnsupportedEncodingException {
    StringBuilder clearText = new StringBuilder(username);
    clearText.append(':');
    if (password != null) {
      clearText.append(password);
    }
    String headerResult;
    headerResult = "Basic "
            + Base64.encodeBase64String(clearText.toString().getBytes("UTF-8"));

    if (headerResult.endsWith("\r\n")) {
      headerResult = headerResult.substring(0, headerResult.length() - 2);
    }
    return headerResult;
  }
}
