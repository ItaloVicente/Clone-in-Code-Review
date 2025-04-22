        AddrUtil.getAddresses(cf.getVBucketConfig().getServers());

    List<InetSocketAddress> conv = new LinkedList<InetSocketAddress>();
    while (!addrs.isEmpty()) {
      conv.add(addrs.remove(0));
    }
    while (!conv.isEmpty()) {
      addrs.add(new InetSocketAddress(conv.remove(0).getHostName(), 5984));
    }
