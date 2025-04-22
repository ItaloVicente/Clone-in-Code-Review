	void setSshDriver(SshDriver sshDriver) {
		switch (sshDriver) {
		case APACHE: {
			SshdSessionFactory factory = new SshdSessionFactory(
					new JGitKeyCache()
			Runtime.getRuntime().addShutdownHook(new Thread(factory::close));
			SshSessionFactory.setInstance(factory);
			break;
		}
		case JSCH:
			JschConfigSessionFactory factory = new JschConfigSessionFactory();
			SshSessionFactory.setInstance(factory);
			break;
		default:
			SshSessionFactory.setInstance(null);
			break;
		}
	}

