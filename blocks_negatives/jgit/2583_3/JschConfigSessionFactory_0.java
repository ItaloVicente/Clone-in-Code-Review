	public synchronized Session getSession(String user, String pass,
			String host, int port, CredentialsProvider credentialsProvider,
			FS fs) throws JSchException {
		if (config == null)
			config = OpenSshConfig.get(fs);

		final OpenSshConfig.Host hc = config.lookup(host);
		host = hc.getHostName();
		if (port <= 0)
			port = hc.getPort();
		if (user == null)
			user = hc.getUser();

		final Session session = createSession(hc, user, host, port, fs);
		if (pass != null)
			session.setPassword(pass);
		final String strictHostKeyCheckingPolicy = hc
				.getStrictHostKeyChecking();
		if (strictHostKeyCheckingPolicy != null)
			session.setConfig("StrictHostKeyChecking",
					strictHostKeyCheckingPolicy);
		final String pauth = hc.getPreferredAuthentications();
		if (pauth != null)
			session.setConfig("PreferredAuthentications", pauth);
		if (credentialsProvider != null
