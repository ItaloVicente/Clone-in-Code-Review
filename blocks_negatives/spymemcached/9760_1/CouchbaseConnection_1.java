      ArrayList<InetSocketAddress> newServers =
          new ArrayList<InetSocketAddress>();
      for (String server : servers) {
        int finalColon = server.lastIndexOf(':');
        if (finalColon < 1) {
          throw new IllegalArgumentException("Invalid server ``" + server
              + "'' in vbucket's server list");
        }
        String hostPart = server.substring(0, finalColon);

        InetSocketAddress address =
            new InetSocketAddress(hostPart, Integer.parseInt("5984"));
