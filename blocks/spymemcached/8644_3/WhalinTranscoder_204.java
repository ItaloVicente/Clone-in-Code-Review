  public Object decode(CachedData d) {
    byte[] data = d.getData();
    Object rv = null;
    if ((d.getFlags() & COMPRESSED) != 0) {
      data = decompress(d.getData());
    }
    if ((d.getFlags() & SERIALIZED) != 0) {
      rv = deserialize(data);
    } else {
      int f = d.getFlags() & ~COMPRESSED;
      switch (f) {
      case SPECIAL_BOOLEAN:
        rv = Boolean.valueOf(this.decodeBoolean(data));
        break;
      case SPECIAL_INT:
        rv = new Integer(tu.decodeInt(data));
        break;
      case SPECIAL_SHORT:
        rv = new Short((short) tu.decodeInt(data));
        break;
      case SPECIAL_LONG:
        rv = new Long(tu.decodeLong(data));
        break;
      case SPECIAL_DATE:
        rv = new Date(tu.decodeLong(data));
        break;
      case SPECIAL_BYTE:
        rv = new Byte(tu.decodeByte(data));
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
      case SPECIAL_STRING:
        rv = decodeString(data);
        break;
      case SPECIAL_STRINGBUFFER:
        rv = new StringBuffer(decodeString(data));
        break;
      case SPECIAL_STRINGBUILDER:
        rv = new StringBuilder(decodeString(data));
        break;
      case SPECIAL_CHARACTER:
        rv = decodeCharacter(data);
        break;
      default:
        getLogger().warn("Cannot handle data with flags %x", f);
      }
    }
    return rv;
  }
