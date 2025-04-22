  public Query setReduce(Boolean reduce) {
    if(reduce) {
      args.put(REDUCE, Boolean.TRUE);
    } else {
      args.put(REDUCE, Boolean.FALSE);
    }
