package com.couchbase.client.java.search.queries;

import java.util.concurrent.TimeUnit;
import java.util.List;

import com.couchbase.client.core.metrics.DefaultMetricsCollectorConfig;
import com.couchbase.client.core.metrics.DefaultMetricsCollectorConfig.Builder;


public class Coordinate
{
  private double lon;
  private double lat;
  

  public static CoordinateAsLonLat(double longitude,double latitude)
  {
	  Builder builder = builder();
      builder.lon(longitude);
      builder.lat(latitude)
      return builder.build();
  }
  

  private static Builder builder() {
      return new Builder();
  }

  protected Coordinate(Builder builder) {
      lon = builder.longitude;
      lat = builder.latitude;
  }

 
  public static class Builder {

      private double latitude;
      private double longitude;

      protected Builder() {
      }

      public Builder lon(double lon) {
          this.longitude = lon;
          return this;
      }
      
      public Builder lat(double lat) {
          this.latitude = lat;
          return this;
      }


      public Coordinate build() {
          return new Coordinate(this);
      }

  }
  
  public static double[] toListOfDoubles(List<Coordinate> listOfPoints)
  {
	  List<double[2]> result = new List<double>();
	  for(int ind = 0; ind < listOfPoints.size(); i++)
	  {
		  double coord = new double[2];
		  coord[0] = listOfPoints[ind].lon;
		  coord[1] = listOfPoints[ind].lat;
		  result.add(coord);
		  
	  }
	  return result;
  }
}
