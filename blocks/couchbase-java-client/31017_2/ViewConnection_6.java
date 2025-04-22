    HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
    List<InetSocketAddress> newServers = AddrUtil.getAddressesFromURL(
      bucket.getConfig().getCouchServers());
    for (InetSocketAddress server : newServers) {
      newServerAddresses.add(server);
    }

    ArrayList<InetSocketAddress> stayServers = new ArrayList<InetSocketAddress>();
