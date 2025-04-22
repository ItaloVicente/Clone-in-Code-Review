
  @Override
  public long getTotalRows() {
    throw new IllegalStateException("Total Number of Rows is not available on "
      + "reduced views.");
  }

