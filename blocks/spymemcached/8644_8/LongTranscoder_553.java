  public Long decode(CachedData d) {
    if (FLAGS == d.getFlags()) {
      return tu.decodeLong(d.getData());
    } else {
      getLogger().error(
          "Unexpected flags for long:  " + d.getFlags() + " wanted " + FLAGS);
      return null;
    }
  }
