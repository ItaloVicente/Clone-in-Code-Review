	public List<HostEntryPair> lookup(ClientSession session,
			SocketAddress remote) {
		List<HostKeyFile> filesToUse = getFilesToUse(session);
		HostKeyHelper helper = new HostKeyHelper();
		List<HostEntryPair> result = new ArrayList<>();
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(session, remote);
