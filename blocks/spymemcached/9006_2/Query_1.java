  public boolean willReduce() {
    return (args.containsKey(REDUCE))
      ? ((Boolean)args.get(REDUCE)).booleanValue() : false;
  }

  public boolean willIncludeDocs() {
    return includedocs;
  }

