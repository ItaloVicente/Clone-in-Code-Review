  public DeleteOperation delete(String key, long cas,
    DeleteOperation.Callback cb) {
    throw new UnsupportedOperationException("Delete with CAS is not supported "
        + "for ASCII protocol");
  }

