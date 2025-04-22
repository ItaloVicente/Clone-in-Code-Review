  public Map<String, Object> getArgs() {
    Map<String, Object> args = new HashMap<String, Object>();
    for (int i = 0; i < params.length; i++) {
      boolean even = i % 2 == 0;
      if (even && params[i] != null) {
        args.put(params[i], params[i+1]);
      }
    }
    return args;
