  public static ObserveResponse valueOf(byte b) {
    switch (b) {
    case (byte) 0x00:
      return ObserveResponse.FOUND_NOT_PERSISTED;
    case (byte) 0x01:
      return ObserveResponse.FOUND_PERSISTED;
    case (byte) 0x80:
      return ObserveResponse.NOT_FOUND_PERSISTED;
    case (byte) 0x81:
      return ObserveResponse.NOT_FOUND_NOT_PERSISTED;
    case (byte) 0xfe:
      return ObserveResponse.MODIFIED;
    default:
      return ObserveResponse.UNINITIALIZED;
    }
  }

