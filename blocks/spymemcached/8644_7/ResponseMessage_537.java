  public long getEnginePrivate() {
    if (ENGINE_PRIVATE_OFFSET + ENGINE_PRIVATE_FIELD_LENGTH
        > getExtralength()) {
      return 0;
    }
    int offset = HEADER_LENGTH + ENGINE_PRIVATE_OFFSET;
    return Util.fieldToValue(mbytes, offset, ENGINE_PRIVATE_FIELD_LENGTH);
  }
