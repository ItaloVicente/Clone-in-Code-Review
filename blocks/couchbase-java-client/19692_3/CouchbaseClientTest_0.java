
    StringBuilder availableServers = new StringBuilder();
    for(SocketAddress sa : client.getAvailableServers()) {
      if (availableServers.length() > 0) {
        availableServers.append(";");
      }
      availableServers.append(sa.toString());
    }

