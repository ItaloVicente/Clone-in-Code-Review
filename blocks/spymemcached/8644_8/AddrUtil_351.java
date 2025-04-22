      addrs.add(new InetSocketAddress(hostPart, Integer.parseInt(portNum)));
    }
    if (addrs.isEmpty()) {
      throw new IllegalArgumentException("servers cannot be empty");
    }
    return addrs;
  }
