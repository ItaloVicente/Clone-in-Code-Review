  public void setvBucketCheckpoints(Map<Short, Long> vbchkpnts) {
    int oldSize = (vBucketCheckpoints.size()) * 10;
    int newSize = (vbchkpnts.size()) * 10;
    totalbody += newSize - oldSize;
    vBucketCheckpoints = vbchkpnts;
  }

