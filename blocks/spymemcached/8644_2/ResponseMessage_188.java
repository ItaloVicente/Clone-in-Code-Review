  public byte[] getValue() {
    if (getExtralength() + getKeylength() >= getTotalbody()) {
      return new byte[0];
    }
    int offset = (int) (HEADER_LENGTH + getExtralength() + getKeylength());
    int length = (int) (getTotalbody() - getKeylength() - getExtralength());
    byte[] value = new byte[length];
    System.arraycopy(mbytes, offset, value, 0, length);
    return value;
  }
