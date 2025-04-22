package com.couchbase.client.java.search.queries;

public class Coordinate
{
  private Double[] coordinates = new Double[2];

  public Coordinate(double lon,double lat)
  {
    this.coordinates[0] = lon;
    this.coordinates[1] = lat;
  }

  public Double getLon()
  {
    return coordinates[0];
  }

  public void setLon(Double longitude)
  {
    this.coordinates[0] = longitude;
  }

  public Double getLat()
  {
    return coordinates[1];
  }

  public void getLat(Double latitude)
  {
    this.coordinates[1] = latitude;
  }

}
