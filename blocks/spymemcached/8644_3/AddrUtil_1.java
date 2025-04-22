  public static List<InetSocketAddress> getAddresses(List<String> servers) {
    ArrayList<InetSocketAddress> addrs =
        new ArrayList<InetSocketAddress>(servers.size());
    for (String server : servers) {
      int finalColon = server.lastIndexOf(':');
      if (finalColon < 1) {
        throw new IllegalArgumentException("Invalid server ``" + server
            + "'' in list:  " + server);
      }
      String hostPart = server.substring(0, finalColon);
      String portNum = server.substring(finalColon + 1);
