  public Object decode(CachedData d) {
    byte[] data = d.getData();
    Object rv = null;
    if ((d.getFlags() & COMPRESSED) != 0) {
      data = decompress(d.getData());
    }
    int flags = d.getFlags() & SPECIAL_MASK;
    if ((d.getFlags() & SERIALIZED) != 0 && data != null) {
      rv = deserialize(data);
    } else if (flags != 0 && data != null) {
      switch (flags) {
      case SPECIAL_BOOLEAN:
        rv = Boolean.valueOf(tu.decodeBoolean(data));
        break;
      case SPECIAL_INT:
        rv = Integer.valueOf(tu.decodeInt(data));
        break;
      case SPECIAL_LONG:
        rv = Long.valueOf(tu.decodeLong(data));
        break;
      case SPECIAL_DATE:
        rv = new Date(tu.decodeLong(data));
        break;
      case SPECIAL_BYTE:
        rv = Byte.valueOf(tu.decodeByte(data));
        break;
      case SPECIAL_FLOAT:
        rv = new Float(Float.intBitsToFloat(tu.decodeInt(data)));
        break;
      case SPECIAL_DOUBLE:
        rv = new Double(Double.longBitsToDouble(tu.decodeLong(data)));
        break;
      case SPECIAL_BYTEARRAY:
        rv = data;
        break;
      default:
        getLogger().warn("Undecodeable with flags %x", flags);
      }
    } else {
      rv = decodeString(data);
    }
    return rv;
  }
