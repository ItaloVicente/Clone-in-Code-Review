  public Query setBbox(double lowerLeftLong, double lowerLeftLat,
    double upperRightLong, double upperRightLat) {
    String combined = lowerLeftLong + "," + lowerLeftLat + ','
      + upperRightLong + ',' + upperRightLat;
    params[PARAM_BBOX_OFFSET] = "bbox";
    params[PARAM_BBOX_OFFSET+1] = encode(combined);
