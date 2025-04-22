		reconfiguring = true;
		try {
			List<String> servers = bucket.getConfig().getServers();
			HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
			ArrayList<InetSocketAddress> newServers = new ArrayList<InetSocketAddress>();
			for (String server : servers) {
				int finalColon = server.lastIndexOf(':');
				if (finalColon < 1) {
					throw new IllegalArgumentException("Invalid server ``"
							+ server + "'' in vbucket's server list");
				}
				String hostPart = server.substring(0, finalColon);

				InetSocketAddress address = new InetSocketAddress(hostPart,
						Integer.parseInt("5984"));
				newServerAddresses.add(address);
				newServers.add(address);

			}

			ArrayList<CouchbaseNode> oddNodes = new ArrayList<CouchbaseNode>();
			ArrayList<CouchbaseNode> stayNodes = new ArrayList<CouchbaseNode>();
			ArrayList<InetSocketAddress> stayServers = new ArrayList<InetSocketAddress>();
			for (CouchbaseNode current : nodes) {
				if (newServerAddresses.contains(current.getSocketAddress())) {
					stayNodes.add(current);
					stayServers.add((InetSocketAddress) current
							.getSocketAddress());
				} else {
					oddNodes.add(current);
				}
			}

			newServers.removeAll(stayServers);
