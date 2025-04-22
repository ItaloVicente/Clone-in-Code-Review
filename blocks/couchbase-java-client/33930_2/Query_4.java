  public boolean willReduce() {
    String reduce = params[PARAM_REDUCE_OFFSET+1];
    if (reduce == null) {
      return false;
    }
    return Boolean.valueOf(reduce);
