  public int getLimit() {
    String limit = params[PARAM_LIMIT_OFFSET+1];
    if (limit == null) {
      return -1;
    }
    return Integer.valueOf(limit);
