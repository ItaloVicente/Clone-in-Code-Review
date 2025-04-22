  public Query setBbox(double lowerLeftLong, double lowerLeftLat,
    double upperRightLong, double upperRightLat) {
    String combined = lowerLeftLong + "," + lowerLeftLat + ","
      + upperRightLong + "," + upperRightLat;
    args.put(BBOX, combined);
    return this;
  }

