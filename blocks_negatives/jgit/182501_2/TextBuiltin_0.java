		switch (sshDriver) {
		case APACHE: {
			SshdSessionFactory factory = new SshdSessionFactory(
					new JGitKeyCache(), new DefaultProxyDataFactory());
			Runtime.getRuntime()
					.addShutdownHook(new Thread(factory::close));
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
