		return new SshPushConnection(newConnection());
	}

	private Connection newConnection() {
		if (useExtConnection())
			return new ExtConnection();
		return new JschConnection();
