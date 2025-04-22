		switch (sshDriver) {
		case APACHE: {
			SshdSessionFactory factory = new SshdSessionFactory(
					new JGitKeyCache());
			Runtime.getRuntime()
					.addShutdownHook(new Thread(() -> factory.close()));
			SshSessionFactory.setInstance(factory);
			break;
		}
		case JSCH:
		default:
			SshSessionFactory.setInstance(null);
			break;
		}
