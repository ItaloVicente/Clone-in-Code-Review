package com.couchbase.client;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Encoder;
public final class TestAdmin {
  private static String serverName;
  private static String adminUserName;
  private static String adminUserPassword;
  private static String bucketName;
  private static String bucketPassword;

  protected TestAdmin(String serverName, String adminUserName,
          String adminUserPassword, String bucketName,
          String bucketPassword) {
    TestAdmin.serverName = serverName;
    TestAdmin.adminUserName = adminUserName;
    TestAdmin.adminUserPassword = adminUserPassword;
    TestAdmin.bucketName = bucketName;
    TestAdmin.bucketPassword = bucketPassword;
  }

  public static String executePost(URL targetURL,
         String urlParameters, String username,
         String password) throws IOException {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection)targetURL.openConnection();
      BASE64Encoder encoder = new BASE64Encoder();
      String encodedCredential =
              encoder.encode((username + ":" + password).getBytes());
      connection.setRequestProperty("Authorization",
              "Basic " + encodedCredential);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type",
           "application/x-www-form-urlencoded");

      connection.setRequestProperty("Content-Length", ""
              + Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");

      connection.setUseCaches(false);
      connection.setDoInput(true);
      connection.setDoOutput(true);

      DataOutputStream wr = new DataOutputStream(
                  connection.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.flush();
      wr.close();

      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;
      StringBuilder response = new StringBuilder();
      while((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      return response.toString();

    } finally {
      if(connection != null) {
        connection.disconnect();
      }
    }
  }

  public static boolean doDeleteDefault() throws IOException {

    URL url = new URL("http://"
            + serverName
            + ":8091/pools/"
            + bucketName + "/buckets/" + bucketName);
    try {
      byte[] buffer = new byte[8192];
      int read;
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("DELETE");
      if (adminUserName != null) {
        String encodedCredential =
                Base64.encodeBase64String((adminUserName + ":"
                + adminUserPassword).getBytes());
        connection.setRequestProperty("Authorization",
                "Basic " + encodedCredential);
      }
      connection.connect();
      long time = System.currentTimeMillis();
      connection.connect();

      InputStream responseBodyStream = connection.getInputStream();
      StringBuilder responseBody = new StringBuilder();
      while ((read = responseBodyStream.read(buffer)) != -1) {
        responseBody.append(new String(buffer, 0, read));
      }
      connection.disconnect();

    } catch (FileNotFoundException ex) {
      System.err.println("Warning: bucket doesn't exist, could"
              + "not be deleted.");
    }

    return true;

  }

  public static String doCreateDefault256() throws IOException {

    URL url = new URL("http://" + serverName
            + ":8091/pools/" + bucketName + "/buckets/");
    String args = "parallelDBAndViewCompaction=false&"
            + "autoCompactionDefined=false&"
            + "replicaIndex=0&"
            + "replicaNumber=1&"
            + "saslPassword=&"
            + "authType=sasl&"
            + "ramQuotaMB=256&"
            + "bucketType=membase&"
            + "name=" + bucketName;
    return (executePost(url, args, adminUserName, adminUserPassword));

  }
  public static void reCreateDefaultBucket() throws Exception {
    try {
      doDeleteDefault();
      Thread.sleep(2000);
      doCreateDefault256();
      Thread.sleep(8000);
    } catch (Exception e) {
      throw(e);
    }
  }
}
