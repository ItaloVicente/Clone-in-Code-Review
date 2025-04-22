package com.couchbase.client.http;

import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HttpUtilTest {

  public HttpUtilTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testBuildAuthHeader() throws UnsupportedEncodingException {
    HttpUtil.buildAuthHeader("foo", "bar");
  }

  @Test
  public void testBuildAuthHeaderUTF8() throws UnsupportedEncodingException {
    String result = HttpUtil.buildAuthHeader("blahblah",
            "bla@@h");
    assertEquals("Basic YmxhaGJsYWg6YmxhQEBo", result);
  }
}
