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
