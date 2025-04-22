  public Integer decode(CachedData d) {
    if (FLAGS == d.getFlags()) {
      return tu.decodeInt(d.getData());
    } else {
      return null;
    }
  }
