
  public static List<InetSocketAddress>
      getAddressesFromURL(List<URL> servers) {
    ArrayList<InetSocketAddress> addrs =
      new ArrayList<InetSocketAddress>(servers.size());
    for (URL server : servers) {
      addrs.add(new InetSocketAddress(server.getHost(), server.getPort()));
    }
    return addrs;
  }
