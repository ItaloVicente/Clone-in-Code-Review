  public Query setRangeStart(ComplexKey startkey) {
    args.put(STARTKEY, startkey.toJson());
    return this;
  }

  /**
   * Use the reduction function.
   *
   * @param reduce True if the reduce phase should also be executed.
   * @return The Query instance.
   */
  public Query setReduce(Boolean reduce) {
    args.put(REDUCE, reduce);
