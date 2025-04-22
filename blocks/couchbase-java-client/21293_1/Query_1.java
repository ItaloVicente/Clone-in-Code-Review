  public Query setRange(ComplexKey startkey, ComplexKey endkey) {
    args.put(ENDKEY, endkey.toJson());
    args.put(STARTKEY, startkey.toJson());
    return this;
  }

