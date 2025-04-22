		return connections;
	}

	public void reconfigure(Bucket bucket) {
		try {
			if (!(locator instanceof VBucketNodeLocator)) {
				return;
			}

			List<String> servers = bucket.getVbuckets().getServers();
			HashSet<SocketAddress> newServerAddresses = new HashSet<SocketAddress>();
			ArrayList<InetSocketAddress> newServers = new ArrayList<InetSocketAddress>();
			for (String server : servers) {
				int finalColon = server.lastIndexOf(':');
				if (finalColon < 1) {
					throw new IllegalArgumentException("Invalid server ``"
					+ server + "'' in vbucket's server list");
				}
				String hostPart = server.substring(0, finalColon);
				String portNum = server.substring(finalColon + 1);

				InetSocketAddress address = new InetSocketAddress(hostPart,
				Integer.parseInt(portNum));
				newServerAddresses.add(address);
				newServers.add(address);

			}

			ArrayList<MemcachedNode> oddNodes = new ArrayList<MemcachedNode>();
			ArrayList<MemcachedNode> stayNodes = new ArrayList<MemcachedNode>();
			ArrayList<InetSocketAddress> stayServers = new ArrayList<InetSocketAddress>();
			for (MemcachedNode current : locator.getAll()) {
				if (newServerAddresses.contains(current.getSocketAddress())) {
					stayNodes.add(current);
					stayServers.add((InetSocketAddress) current.getSocketAddress());
				} else {
					oddNodes.add(current);
				}
			}

			newServers.removeAll(stayServers);

			List<MemcachedNode> newNodes = createConnections(newServers);

			List<MemcachedNode> mergedNodes = new ArrayList<MemcachedNode>();
			mergedNodes.addAll(stayNodes);
			mergedNodes.addAll(newNodes);

			((VBucketNodeLocator) locator).updateLocator(mergedNodes, bucket.getVbuckets());

			nodesToShutdown.addAll(oddNodes);
		} catch (IOException e) {
		    getLogger().error("Connection reconfiguration failed", e);
		}
