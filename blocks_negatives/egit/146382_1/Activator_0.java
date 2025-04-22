		if (SshClientType.APACHE.name().equalsIgnoreCase(sshClient)) {
			if (previous instanceof EGitSshdSessionFactory) {
				return;
			}
			logInfo(CoreText.Activator_SshClientUsingApache);
			SshSessionFactory.setInstance(new EGitSshdSessionFactory());
		} else {
