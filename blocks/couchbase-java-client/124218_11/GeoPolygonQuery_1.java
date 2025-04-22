package com.couchbase.client.java.search.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class Coordinate {

  private final double lon;
  private final double lat;

  public static Coordinate ofLonLat(double lon, double lat) {
    return new Coordinate(lon, lat);
  }

  private Coordinate(final double lon, final double lat) {
      this.lon = lon;
      this.lat = lat;
  }

  public double lon() {
    return lon;
  }

  public double lat() {
    return lat;
  }

}
