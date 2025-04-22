
package com.couchbase.client.protocol.views;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DesignDocument {

  private String name;

  private String language = "javascript";

  private List<ViewDesign> views;

  private List<SpatialViewDesign> spatialViews;

  public DesignDocument(String name, List<ViewDesign> views) {
    this(name, views, new ArrayList<SpatialViewDesign>());
  }

  public DesignDocument(String name, List<ViewDesign> views,
    List<SpatialViewDesign> spatialViews) {
    this.name = name;
    this.views = views;
    this.spatialViews = spatialViews;
  }

  public String getLanguage() {
    return language;
  }

  public String getName() {
    return name;
  }

  public List<ViewDesign> getViews() {
    return views;
  }

  public DesignDocument setViews(List<ViewDesign> views) {
    this.views = views;
    return this;
  }

  public DesignDocument setView(ViewDesign view) {
    this.views.add(view);
    return this;
  }

  public List<SpatialViewDesign> getSpatialViews() {
    return spatialViews;
  }

  public DesignDocument setSpatialViews(List<SpatialViewDesign> spatialViews) {
    this.spatialViews = spatialViews;
    return this;
  }

  public DesignDocument setSpatialView(SpatialViewDesign spatialView) {
    this.spatialViews.add(spatialView);
    return this;
  }

  public DesignDocument setName(String name) {
    this.name = name;
    return this;
  }

  public DesignDocument setLanguage(String language) {
    this.language = language;
    return this;
  }

  public String toJson() {
    if(views.isEmpty()) {
      throw new RuntimeException("A design document needs at least one view.");
    }

    if(name.isEmpty()) {
      throw new RuntimeException("A design document needs a name.");
    }

    JSONObject jsonDesign = new JSONObject();
    try {
      jsonDesign.accumulate("language", language);

      JSONObject jsonViews = new JSONObject();
      for(ViewDesign view : views) {
        JSONObject jsonView = new JSONObject();
        jsonView.accumulate("map", view.getMap());
        if(!view.getReduce().isEmpty()) {
          jsonView.accumulate("reduce", view.getReduce());
        }
        jsonViews.accumulate(view.getName(), jsonView);
      }
      jsonDesign.accumulate("views", jsonViews);

      if(!spatialViews.isEmpty()) {
        JSONObject jsonSpatialViews = new JSONObject();
        for(SpatialViewDesign spatialView : spatialViews) {
          jsonSpatialViews.accumulate(spatialView.getName(),
            spatialView.getMap());
        }
        jsonDesign.accumulate("spatial", jsonSpatialViews);
      }
    } catch (JSONException ex) {
      throw new RuntimeException("Failed to compose design document: " + ex);
    }

    return jsonDesign.toString();
  }
}
