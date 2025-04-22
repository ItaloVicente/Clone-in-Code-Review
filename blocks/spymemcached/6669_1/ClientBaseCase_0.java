	protected boolean isMembase() {
	    if (membase != null) {
		    return membase.booleanValue();
	    }

		Map<SocketAddress, Map<String, String>> stats = client.getStats();
		for (Map<String, String> node : stats.values()) {
			if (node.get("ep_version") != null) {
				membase = true;
				   System.err.println("Found membase!");
				break;
			} else {
				membase = false;
				   System.err.println("Found memcached!");
			}

	    }
	    return membase.booleanValue();
	}

