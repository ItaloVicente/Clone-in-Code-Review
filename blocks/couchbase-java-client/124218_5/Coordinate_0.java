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
