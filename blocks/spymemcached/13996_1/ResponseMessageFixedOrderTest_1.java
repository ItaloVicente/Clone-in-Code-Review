
package net.spy.memcached.tapmessage;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseMessageBaseCase {

  protected byte[] responsebytes;
  protected ResponseMessage instance = null;
  protected List<TapResponseFlag> expectedFlags;

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
    expectedFlags = new LinkedList<TapResponseFlag>();
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testGetEnginePrivate() {
    long expResult = 0L;
    long result = instance.getEnginePrivate();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetFlags() {
    List<TapResponseFlag> result = instance.getFlags();

    short expResultVal = 0;
    for (TapResponseFlag flag : expectedFlags) {
      expResultVal = (short)(expResultVal + flag.getFlags());
    }

    short resultVal = 0;
    for (TapResponseFlag flag : result) {
      resultVal = (short)(resultVal + (int)flag.getFlags());
    }

    assertEquals(expResultVal, resultVal);
  }

  @Test
  public void testGetTTL() {
    int expResult = 0;
    int result = instance.getTTL();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetReserved1() {
    int expResult = 0;
    int result = instance.getReserved1();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetReserved2() {
    int expResult = 0;
    int result = instance.getReserved2();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetReserved3() {
    int expResult = 0;
    int result = instance.getReserved3();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetVBucketState() {
    int expResult = 0;
    int result = instance.getVBucketState();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetItemFlags() {
    int expResult = (int)0x0200;
    int result = instance.getItemFlags();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetItemExpiry() {
    long expResult = 0L;
    long result = instance.getItemExpiry();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetKey() {
    String expResult = "a";
    String result = instance.getKey();
    assertEquals(expResult, result);
  }

  @Test
  public void testGetValue() {
    ByteBuffer bb = ByteBuffer.allocate(8);
    bb.put(7, (byte)42);
    byte[] expResult = bb.array();
    byte[] result = instance.getValue();
    assertArrayEquals(expResult, result);
  }

  @Test
  public void testGetBytes() {
    byte[] result = instance.getBytes().array();
    assertEquals((byte)42, result[result.length-1]);
  }

}
