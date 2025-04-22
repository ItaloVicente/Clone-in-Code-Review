	private Session createSession(CredentialsProvider credentialsProvider
			FS fs
			final OpenSshConfig.Host hc) throws JSchException {
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
				&& (!hc.isBatchMode() || !credentialsProvider.isInteractive())) {
			session.setUserInfo(new CredentialsProviderUserInfo(session
					credentialsProvider));
		}
		configure(hc
		return session;
	}

