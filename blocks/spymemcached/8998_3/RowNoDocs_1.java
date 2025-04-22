
  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public Object getDocument() {
    throw new UnsupportedOperationException("This view result doesn't contain "
        + "documents");
  }
