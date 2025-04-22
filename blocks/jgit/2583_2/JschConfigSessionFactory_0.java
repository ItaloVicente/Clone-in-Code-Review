	public synchronized RemoteSession getSession(URIish uri
			CredentialsProvider credentialsProvider
			throws TransportException {

		String user = uri.getUser();
		final String pass = uri.getPass();
		String host = uri.getHost();
		int port = uri.getPort();

		try {
			if (config == null)
				config = OpenSshConfig.get(fs);

			final OpenSshConfig.Host hc = config.lookup(host);
			host = hc.getHostName();
			if (port <= 0)
				port = hc.getPort();
			if (user == null)
				user = hc.getUser();

			final Session session = createSession(hc
			if (pass != null)
				session.setPassword(pass);
			final String strictHostKeyCheckingPolicy = hc
					.getStrictHostKeyChecking();
			if (strictHostKeyCheckingPolicy != null)
				session.setConfig("StrictHostKeyChecking"
						strictHostKeyCheckingPolicy);
			final String pauth = hc.getPreferredAuthentications();
			if (pauth != null)
				session.setConfig("PreferredAuthentications"
			if (credentialsProvider != null
					&& (!hc.isBatchMode() || !credentialsProvider
							.isInteractive())) {
				session.setUserInfo(new CredentialsProviderUserInfo(session
						credentialsProvider));
			}
			configure(hc

			if (!session.isConnected())
				session.connect(tms);

			return new JschSession(session

		} catch (final JSchException je) {
			final Throwable c = je.getCause();
			if (c instanceof UnknownHostException)
				throw new TransportException(uri
			if (c instanceof ConnectException)
				throw new TransportException(uri
			throw new TransportException(uri
