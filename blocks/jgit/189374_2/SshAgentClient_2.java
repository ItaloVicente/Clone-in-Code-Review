		boolean connected;
		try {
			connected = connector != null && connector.connect();
			if (!connected && debugging) {
			}
		} catch (IOException e) {
