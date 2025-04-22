		Map<SocketAddress, Map<String, String>> stats = client.getStats("proxy");
		for (Map<String, String> node : stats.values()) {
			if (node.get("basic:version") != null) {
				moxi = true;
				   System.err.println("Using proxy");
				break;
			} else {
				moxi = false;
				   System.err.println("Not using proxy");
			}
