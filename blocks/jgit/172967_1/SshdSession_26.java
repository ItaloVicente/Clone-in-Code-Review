	private ClientSession connect(URIish target
			SshFutureListener<CloseFuture> listener
			int depth) throws IOException {
		if (--depth < 0) {
			throw new IOException(
					format(SshdText.get().proxyJumpAbort
		}
		HostConfigEntry hostConfig = getHostConfig(target.getUser()
				target.getHost()
		String host = hostConfig.getHostName();
		int port = hostConfig.getPort();
		List<URIish> hops = determineHops(jumps
		ClientSession resultSession = null;
		ClientSession proxySession = null;
		PortForwardingTracker portForward = null;
		try {
			if (!hops.isEmpty()) {
				URIish hop = hops.remove(0);
				if (LOG.isDebugEnabled()) {
					LOG.debug("Connecting to jump host {}"
