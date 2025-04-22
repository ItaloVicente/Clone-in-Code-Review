
package com.couchbase.client.protocol.views;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpatialViewDesignTest {

  public SpatialViewDesignTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testSpatialViewDesign() {
    String name = "points";
    String map = "function(){}";
    SpatialViewDesign view = new SpatialViewDesign(name, map);
    assertEquals(name, view.getName());
    assertEquals(map, view.getMap());
  }


}
