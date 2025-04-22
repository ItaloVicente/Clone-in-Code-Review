  public static List<InetSocketAddress> getAddresses(String s) {
    if (s == null) {
      throw new NullPointerException("Null host list");
    }
    if (s.trim().equals("")) {
      throw new IllegalArgumentException("No hosts in list:  ``" + s + "''");
    }
    ArrayList<InetSocketAddress> addrs = new ArrayList<InetSocketAddress>();
