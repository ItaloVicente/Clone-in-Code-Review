		String identityAgent = null;
		if (session instanceof JGitClientSession) {
			HostConfigEntry hostConfig = ((JGitClientSession) session)
					.getHostConfigEntry();
			identityAgent = hostConfig.getProperty(SshConstants.IDENTITY_AGENT
					null);
		}
		if (SshConstants.NONE.equals(identityAgent)) {
			return null;
		}
		return new SshAgentClient(factory.create(identityAgent
