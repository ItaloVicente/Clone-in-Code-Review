  public static Collection<byte[]> getKeyBytes(Collection<String> keys) {
    Collection<byte[]> rv = new ArrayList<byte[]>(keys.size());
    for (String s : keys) {
      rv.add(getKeyBytes(s));
    }
    return rv;
  }
