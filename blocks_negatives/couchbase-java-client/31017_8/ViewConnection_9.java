  public void reconfigure(Bucket bucket) {
    reconfiguring = true;

    try {
      HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
      List<InetSocketAddress> newServers =
        AddrUtil.getAddressesFromURL(bucket.getConfig().getCouchServers());
      for (InetSocketAddress server : newServers) {
        newServerAddresses.add(server);
      }

      ArrayList<ViewNode> shutdownNodes = new ArrayList<ViewNode>();
      ArrayList<ViewNode> stayNodes = new ArrayList<ViewNode>();
      ArrayList<InetSocketAddress> stayServers =
          new ArrayList<InetSocketAddress>();

      wlock.lock();
      try {
        for (ViewNode current : couchNodes) {
          if (newServerAddresses.contains(current.getSocketAddress())) {
            stayNodes.add(current);
            stayServers.add(current.getSocketAddress());
          } else {
            shutdownNodes.add(current);
          }
        }
