  @Override
  public boolean asyncDecode(CachedData d) {
    if ((d.getFlags() & COMPRESSED) != 0 || (d.getFlags() & SERIALIZED) != 0) {
      return true;
    }
    return super.asyncDecode(d);
  }
