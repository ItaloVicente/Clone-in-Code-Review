	private void updateKnownHostsFile(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey, Path path,
			HostKeyHelper updater)
			throws IOException {
		KnownHostEntry entry = updater.prepareKnownHostEntry(clientSession,
				remoteAddress, serverKey);
		if (entry == null) {
