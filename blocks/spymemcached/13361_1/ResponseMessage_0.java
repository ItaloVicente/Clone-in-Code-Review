
  @Override
  public String toString() {
    return String.format("Key: %s, Flags: %d, TTL: %d, Size: %d\nValue: %s",
      getKey(), getItemFlags(), getTTL(), getValue().length, deserialize());
  }

  private Object deserialize() {
    SerializingTranscoder tc = new SerializingTranscoder();
    CachedData d = new CachedData(this.getItemFlags(), this.getValue(), CachedData.MAX_SIZE);
    Object rv = null;
    rv = tc.decode(d);
    return rv;
  }

