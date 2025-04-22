		return new SshdSessionFactoryBuilder()
				.setProxyDataFactory(null)
				.setConnectorFactory(null)
				.setHomeDirectory(FS.DETECTED.userHome())
				.setSshDirectory(sshDir)
				.build(new JGitKeyCache());
