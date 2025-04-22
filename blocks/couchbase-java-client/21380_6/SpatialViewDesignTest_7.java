
package com.couchbase.client.protocol.views;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DesignDocumentTest {

  public DesignDocumentTest() {
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
  public void testDesignDocumentWithoutSpatial() {
    String viewname = "myview";
    String viewmap = "function()...";
    ViewDesign view = new ViewDesign(viewname, viewmap);
    List<ViewDesign> views = new ArrayList<ViewDesign>();
    views.add(view);

    DesignDocument doc = new DesignDocument("mydesigndoc", views);
    String expected = "{\"language\":\"javascript\",\"views\":{\""
      + viewname + "\":{\"map\":\"" + viewmap + "\"}}}";
    assertEquals(expected, doc.toJson());
  }

  @Test
  public void testDesignDocumentWithSpatial() {
    String viewname = "myview";
    String viewmap = "function()...";
    ViewDesign view = new ViewDesign(viewname, viewmap);
    List<ViewDesign> views = new ArrayList<ViewDesign>();
    views.add(view);

    String spatialname = "points";
    String spatialmap = "function() {emit(1.0, null);}";
    SpatialViewDesign spatialView = new SpatialViewDesign(spatialname,
      spatialmap);
    List<SpatialViewDesign> spatialViews = new ArrayList<SpatialViewDesign>();
    spatialViews.add(spatialView);

    DesignDocument doc = new DesignDocument("mydesigndoc", views, spatialViews);
    String expected = "{\"language\":\"javascript\",\"views\":{\""
      + viewname + "\":{\"map\":\"" + viewmap + "\"}},\"spatial\":{\""
      + spatialname +"\":\"" + spatialmap + "\"}}";
    assertEquals(expected, doc.toJson());
  }

}
