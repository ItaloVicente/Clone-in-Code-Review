  public int getLimit() {
    if (args.containsKey(LIMIT)) {
      return(((Integer)args.get(LIMIT)).intValue());
    } else {
      return -1;
    }
  }

