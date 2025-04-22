
  @Override
  public String getBbox() {
     throw new UnsupportedOperationException("Map/Reduce views don't contain "
       + "Bounding Box information");
  }

  @Override
  public String getGeometry() {
      throw new UnsupportedOperationException("Map/Reduce views don't contain "
       + "Geometry information");
  }
