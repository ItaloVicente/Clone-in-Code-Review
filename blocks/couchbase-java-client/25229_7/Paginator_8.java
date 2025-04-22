
  public final void remove() {
    throw new UnsupportedOperationException("Remove is unsupported");
  }

  enum State {
    INITIALIZED,
    PAGING,
    FINISHED
  }

