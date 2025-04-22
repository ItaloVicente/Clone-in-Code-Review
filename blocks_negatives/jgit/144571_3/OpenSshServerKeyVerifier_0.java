	private boolean find(ClientSession clientSession,
			SocketAddress remoteAddress, PublicKey serverKey,
			List<HostEntryPair> entries, HostEntryPair[] modified,
			HostKeyHelper helper) throws RevokedKeyException {
		Collection<SshdSocketAddress> candidates = helper
				.resolveHostNetworkIdentities(clientSession, remoteAddress);
