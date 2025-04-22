  public Query setKeys(ComplexKey keys) {
    args.put(KEYS, keys.toJson());
    return this;
  }

  public Query setKeys(String keys) {
    args.put(KEYS, keys);
    return this;
  }
