  private ViewType viewType;

  public View(String bn, String ddn, String vn, boolean m, boolean r) {
    this(bn, ddn, vn, m, r, ViewType.MAPREDUCE);
  }

  public View(String bn, String ddn, String vn, boolean m, boolean r,
    ViewType t) {
    bucketName = bn;
