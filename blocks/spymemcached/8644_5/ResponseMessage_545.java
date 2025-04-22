  public String getKey() {
    if (getExtralength() >= getTotalbody()) {
      return new String();
    }
    int offset = (int) (HEADER_LENGTH + getExtralength());
    return new String(mbytes, offset, getKeylength());
  }
