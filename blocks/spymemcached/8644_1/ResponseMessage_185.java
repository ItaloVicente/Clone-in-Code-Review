  public int getItemFlags() {
    if (ITEM_FLAGS_OFFSET + ITEM_FLAGS_FIELD_LENGTH > getExtralength()) {
      return 0;
    }
    int offset = HEADER_LENGTH + ITEM_FLAGS_OFFSET;
    return (int) Util.fieldToValue(mbytes, offset, ITEM_FLAGS_FIELD_LENGTH);
  }
