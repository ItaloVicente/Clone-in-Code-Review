  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean firstParam = true;
    for (int i = 0; i < params.length; i++) {
      if (params[i] == null) {
        i++;
        continue;
      }

      boolean even = i % 2 == 0;
      if (even) {
        sb.append(firstParam ? "?" : "&");
      }
      sb.append(params[i]);
      firstParam = false;
      if (even) {
        sb.append('=');
      }
    }
    return sb.toString();
