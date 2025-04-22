    try {
    } catch (InterruptedException e) {
      fail("Interrupted while client was warming up");
    }

    StringBuilder availableServers = new StringBuilder();
    for(SocketAddress sa : client.getAvailableServers()) {
      if (availableServers.length() > 0) {
        availableServers.append(";");
      }
      availableServers.append(sa.toString());
    }

    assert (client.getAvailableServers().size() % 2) ==  0 : "Num servers "
      + client.getAvailableServers().size() + ". They are: "
      + availableServers;
