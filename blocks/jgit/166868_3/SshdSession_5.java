			long timeLimit = timeout.toMillis();
			long remainingTime[] = { timeLimit };
			session = connect(uri
					future -> notifyCloseListeners()
					timeLimit > 0);
		} catch (IOException e) {
			disconnect(e);
			throw e;
		}
	}

	@SuppressWarnings("resource")
	private ClientSession connect(URIish target
			SshFutureListener<CloseFuture> listener
			boolean useTimeout) throws IOException {
		ClientSession resultSession = null;
		ClientSession proxySession = null;
		PortForwardingTracker portForward = null;
		try {
			List<URIish> hops = jumps;
			String username = target.getUser();
			String host = target.getHost();
			int port = target.getPort();
			HostConfigEntry hostConfig = getHostConfig(username
			username = hostConfig.getUsername();
			host = hostConfig.getHostName();
			port = hostConfig.getPort();
			if (hops.isEmpty()) {
				String jumpHosts = hostConfig
						.getProperty(SshConstants.PROXY_JUMP);
				if (!StringUtils.isEmptyOrNull(jumpHosts)) {
					try {
						hops = parseProxyJump(jumpHosts);
					} catch (URISyntaxException e) {
						throw new IOException(MessageFormat.format(
								SshdText.get().configInvalidProxyJump
								target.getHost()
					}
				}
