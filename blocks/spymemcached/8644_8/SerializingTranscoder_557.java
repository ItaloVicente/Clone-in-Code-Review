  public CachedData encode(Object o) {
    byte[] b = null;
    int flags = 0;
    if (o instanceof String) {
      b = encodeString((String) o);
    } else if (o instanceof Long) {
      b = tu.encodeLong((Long) o);
      flags |= SPECIAL_LONG;
    } else if (o instanceof Integer) {
      b = tu.encodeInt((Integer) o);
      flags |= SPECIAL_INT;
    } else if (o instanceof Boolean) {
      b = tu.encodeBoolean((Boolean) o);
      flags |= SPECIAL_BOOLEAN;
    } else if (o instanceof Date) {
      b = tu.encodeLong(((Date) o).getTime());
      flags |= SPECIAL_DATE;
    } else if (o instanceof Byte) {
      b = tu.encodeByte((Byte) o);
      flags |= SPECIAL_BYTE;
    } else if (o instanceof Float) {
      b = tu.encodeInt(Float.floatToRawIntBits((Float) o));
      flags |= SPECIAL_FLOAT;
    } else if (o instanceof Double) {
      b = tu.encodeLong(Double.doubleToRawLongBits((Double) o));
      flags |= SPECIAL_DOUBLE;
    } else if (o instanceof byte[]) {
      b = (byte[]) o;
      flags |= SPECIAL_BYTEARRAY;
    } else {
      b = serialize(o);
      flags |= SERIALIZED;
    }
    assert b != null;
    if (b.length > compressionThreshold) {
      byte[] compressed = compress(b);
      if (compressed.length < b.length) {
        getLogger().debug("Compressed %s from %d to %d",
            o.getClass().getName(), b.length, compressed.length);
        b = compressed;
        flags |= COMPRESSED;
      } else {
        getLogger().info("Compression increased the size of %s from %d to %d",
            o.getClass().getName(), b.length, compressed.length);
      }
    }
    return new CachedData(flags, b, getMaxSize());
  }
